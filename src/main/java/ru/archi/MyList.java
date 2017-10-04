package main.java.ru.archi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Черный on 03.10.2017.
 */
public class MyList extends ArrayList {
    ArrayList<Integer> predicate = new ArrayList<>();

    public void addPredicate(Integer...args) {
        Collections.addAll(predicate, args);
    }

    public ArrayList<Integer> getPredicate(){
        return predicate;
    }

    public void setPredicate(ArrayList predicate){
        this.predicate = predicate;
    }
}
