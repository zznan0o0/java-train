package common.utils;


@FunctionalInterface
public interface MapDictDictFn <T1, T2, T3> {
      T3 apply(T1 t1, T2 t2, String key);
}
