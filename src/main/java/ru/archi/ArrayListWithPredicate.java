package main.java.ru.archi;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Черный on 03.10.2017.
 */
public class ArrayListWithPredicate<E> extends ArrayList {
    private ArrayList<Integer> predicate = new ArrayList<>();

    transient Object[] elementData;

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayListWithPredicate() {
        super();
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public void addPredicate(Integer...args) {
        Collections.addAll(predicate, args);
    }

    public Iterator<E> iterator() {
        return new ArrayListWithPredicate.Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            if(cursor == ArrayListWithPredicate.this.size()) {
                return false;
            } else if(predicate.contains(elementData[cursor])) {
                if(++cursor != ArrayListWithPredicate.this.size()) {
                    hasNext();
                } else return false;
            }
            return true;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= ArrayListWithPredicate.this.size())
                throw new NoSuchElementException();
            Object[] elementData = ArrayListWithPredicate.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayListWithPredicate.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayListWithPredicate.this.size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayListWithPredicate.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    public ArrayListWithPredicate(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public boolean add(Object o) {
        ensureCapacityInternal(ArrayListWithPredicate.this.size() + 1);
        elementData[ArrayListWithPredicate.this.size()] = o;
        return super.add(o);
    }

    @Override
    public void add(int index, Object element) {
        super.add(index, element);
        elementData[index] = element;
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
}
