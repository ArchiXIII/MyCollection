package main.java.ru.archi;

import java.util.Iterator;

/**
 * Created by Черный on 03.10.2017.
 */
public class Controller {
    public static void main(String[] args) {
        ArrayListWithPredicate<Integer> myList = new ArrayListWithPredicate();
        myList.addPredicate(5, 9, 4);
        myList.add(1);
        myList.add(2);
        myList.add(5);
        myList.add(7);
        myList.add(9);

        Iterator itr = myList.iterator();

        while(itr.hasNext()) {
            System.out.print(itr.next().toString() + " ");
        }
    }
}
