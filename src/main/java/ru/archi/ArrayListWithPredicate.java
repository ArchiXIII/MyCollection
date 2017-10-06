package main.java.ru.archi;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Черный on 03.10.2017.
 */
public class ArrayListWithPredicate<E> extends ArrayList {
    private ArrayList<Integer> predicate = new ArrayList<>();

    public void addPredicate(Integer...args) {
        Collections.addAll(predicate, args);
    }

    public ArrayList<Integer> getPredicate(){
        return predicate;
    }

    public void setPredicate(ArrayList predicate){
        this.predicate = predicate;
    }

    public Iterator<E> iterate() {
        return new MyItr();
    }

    private class MyItr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != ArrayListWithPredicate.this.size();
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


}
