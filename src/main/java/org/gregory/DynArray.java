package org.gregory;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DynArray<T> {

    public T[] array;
    public int count;
    public int capacity;
    Class clazz;

    public DynArray(Class clz) {
        clazz = clz;
        count = 0;
        setCapacity(16);
        array = (T[]) Array.newInstance(clazz, capacity);
    }

    public void makeArray(int new_capacity) {
        T[] newArray = (T[]) Array.newInstance(this.clazz, new_capacity);
        System.arraycopy(array, 0, newArray, 0, count);
        this.array = newArray;
        setCapacity(new_capacity);
    }

    public T getItem(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= count) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bounds Exception");
        }
        return this.array[index];
    }

    public void append(T itm) {
        if (getCount() == getCapacity()) {
            makeArray(capacity * 2);
        }
        array[count++] = itm;
    }

    public void appendArr(T... arr) {
        for (T t : arr) {
            append(t);
        }
    }

    public void insert(T itm, int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > getCount()) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bounds Exception");
        }

        if (getCount() == getCapacity()) {
            makeArray(getCapacity() * 2);
        }

        System.arraycopy(array, index, array, index + 1, getCount() - index);
        array[index] = itm;
        count++;
    }

    public void remove(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= getCount()) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bounds Exception");
        }

        System.arraycopy(array, index + 1, array, index, getCount() - (index + 1));
        count--;
        array[getCount()] = null;

        int newCapacity = (int) (getCapacity() / 1.5);
        if (getCount() * 2 < getCapacity()
                && newCapacity >= 16) {
            makeArray(newCapacity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        DynArray<?> that = (DynArray<?>) o;

        if (count != that.count
                || capacity != that.capacity
                || !Arrays.equals(array, that.array)
                || getClazz() != getClazz()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Class ")
                .append(clazz)
                .append(" capacity ")
                .append(getCapacity())
                .append(" count ")
                .append(getCount())
                .append(" array ");

        for (T arr : array) {
            stringBuilder.append(arr)
                    .append(" ");
        }

        return stringBuilder.toString();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Class getClazz() {
        return clazz;
    }
}
