package hw2_arraylist_and_mergesort;

import java.util.Collection;
import java.util.Comparator;

public interface CustomArrayListInterface<T> {
    void add(T item);

    void add(int index, T item);

    boolean addAll(Collection<? extends T> c);

    void clear();

    T get(int index);

    boolean isEmpty();

    T remove(int index);

    boolean remove(Object o);

    void sort(Comparator<? super T> c);

    int size();
}
