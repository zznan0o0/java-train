package common.utils;

@FunctionalInterface
public interface MapDictDitc2TFn<T1, T2>{
    T1 apply(T1 t1, T2 t2, String key) throws NoSuchFieldException, IllegalAccessException;
}
