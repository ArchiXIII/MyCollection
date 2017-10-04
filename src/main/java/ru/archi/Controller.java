package main.java.ru.archi;

/**
 * Created by Черный on 03.10.2017.
 */
public class Controller {
    public static void main(String[] args) {
        MyList myList = new MyList();
        myList.addPredicate(5, 9, 4);
        myList.add(1);
        myList.add(2);
        myList.add(5);
        myList.add(7);
        myList.add(9);


        System.out.println(myList.get(2) + "  " + myList.get(3));
    }
}
