package indi.qjw.mx.common.util;

import com.google.common.collect.Iterables;

import java.util.Collection;

public class IndexUtil {
    public static int get(int[] array, int index) {
        return get(array, index, 0);
    }

    public static int get(int[] array, int index, int defaultValue) {
        if (array == null) {
            return defaultValue;
        }
        if (index < 0) {
            index += array.length;
        }
        if (index < 0 || index >= array.length) {
            return defaultValue;
        }
        return array[index];
    }

    public static <T> T get(T[] array, int index) {
        return get(array, index, null);
    }

    public static <T> T get(T[] array, int index, T defaultValue) {
        if (array == null) {
            return defaultValue;
        }
        if (index < 0) {
            index += array.length;
        }
        if (index < 0 || index >= array.length) {
            return defaultValue;
        }
        return array[index];
    }

    public static <T> T get(Collection<T> collection, int index) {
        return get(collection, index, null);
    }

    public static <T> T get(Collection<T> collection, int index, T defaultValue) {
        if (collection == null) {
            return defaultValue;
        }
        if (index < 0) {
            index += collection.size();
        }
        if (index < 0 || index >= collection.size()) {
            return defaultValue;
        }
        T t = Iterables.get(collection, index);
        return t == null ? defaultValue : t;
    }
}
