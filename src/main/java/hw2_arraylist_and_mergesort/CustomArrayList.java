package hw2_arraylist_and_mergesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<T> implements CustomArrayListInterface<T> {
    private final int DEFAULT_CAPACITY = 10;
    private Object[] elementData = new Object[DEFAULT_CAPACITY];
    private int size = 0;

    public CustomArrayList() {
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public CustomArrayList(Collection<? extends T> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        }
    }

    @Override
    public void add(T item) {
        resizeForAdd();
        elementData[size++] = item;
    }

    @Override
    public void add(int index, T item) {
        checkIndex(index);
        resizeForAdd();
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = item;
        size++;
    }

    private void resizeForAdd() {
        if (size == elementData.length - 1)
            resize(elementData.length * 2);
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        c.forEach(this::add);
        return true;
    }

    @Override
    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        if (size == 0 || size == 1) return;
        mergeSort(c, elementData, size);
    }

    private void mergeSort(Comparator<? super T> comparator, Object[] array, int length) {
        if (length < 2) {
            return;
        }
        int mid = length / 2;
        Object[] leftAL = new Object[mid];
        Object[] rightAL = new Object[length - mid];

        System.arraycopy(array, 0, leftAL, 0, mid);
        System.arraycopy(array, mid, rightAL, 0, length - mid);

        mergeSort(comparator, leftAL, mid);
        mergeSort(comparator, rightAL, length - mid);

        merge(comparator, array, leftAL, rightAL, mid, length - mid);
    }

    @SuppressWarnings("unchecked")
    private void merge(Comparator<? super T> comparator, Object[] array, Object[] leftAL, Object[] rightAL, int left, int right) {
        int i = 0, j = 0, k = 0;

        while (i < left && j < right) {
            T itemLeftAL = (T) leftAL[i];
            T itemRightAL = (T) rightAL[j];

            if (comparator.compare(itemLeftAL, itemRightAL) <= 0) {
                array[k++] = leftAL[i++];
            } else {
                array[k++] = rightAL[j++];
            }
        }

        while (i < left) {
            array[k++] = leftAL[i++];
        }

        while (j < right) {
            array[k++] = rightAL[j++];
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elementData[index];
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked") T oldValue = (T) elementData[index];
        shiftLeft(index);
        return oldValue;
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size; i++) {
            elementData[i] = elementData[i + 1];
        }
        elementData[size] = null;
        size--;
        trimCapacity();
    }

    private void trimCapacity() {
        if (elementData.length > DEFAULT_CAPACITY && size < elementData.length / 4)
            resize(elementData.length / 2);
    }

    @Override
    public boolean remove(Object o) {
        int index = 0;
        found:
        {
            if (o == null) {
                for (; index < size; index++)
                    if (elementData[index] == null)
                        break found;
            } else {
                for (; index < size; index++)
                    if (o.equals(elementData[index]))
                        break found;
            }
            return false;
        }
        shiftLeft(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(elementData, 0, newArray, 0, size);
        elementData = newArray;
    }
}

