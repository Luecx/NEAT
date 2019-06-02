package data_structures;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomHashSet<T> {

    HashSet<T> set;
    ArrayList<T> data;

    public RandomHashSet(){
        set = new HashSet<>();
        data = new ArrayList<>();
    }

    public boolean contains(T object){
        return set.contains(object);
    }

    public T random_element(){
        if(set.size() > 0){
            return data.get((int)(Math.random() * size()));
        }
        return null;
    }

    public int size() {
        return data.size();
    }

    public void add(T object){
        if(!set.contains(object)){
            set.add(object);
            data.add(object);
        }
    }

    public void clear() {
        set.clear();
        data.clear();
    }

    public T get(int index){
        if(index < 0 || index >= size())return null;
        return data.get(index);
    }

    public void remove(int index){
        if(index < 0 || index >= size()) return;
        set.remove(data.get(index));
        data.remove(index);
    }

    public void remove(T object){
        set.remove(object);
        data.remove(object);
    }

    public ArrayList<T> getData() {
        return data;
    }
}
