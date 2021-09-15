package com.blood.donation.app.utils;


import java.util.function.Function;

public final class ObjectUtils {

    private ObjectUtils() { }


    public static <A, B> B safeGet(A object, Function<A, B> bGetter) {
        return object == null ? null : bGetter.apply(object);
    }

    public static <A, B, C> C safeGet(A object, Function<A, B> bGetter, Function<B, C> cGetter) {
        B b = safeGet(object, bGetter);
        return b == null ? null : cGetter.apply(b);
    }

    public static <A, B, C, D> D safeGet(
            A object, Function<A, B> bGetter, Function<B, C> cGetter, Function<C, D> dGetter) {
        C c = safeGet(object, bGetter, cGetter);
        return c == null ? null : dGetter.apply(c);
    }

    public static <A, B, C, D, E> E safeGet(
            A object, Function<A, B> bGetter, Function<B, C> cGetter, Function<C, D> dGetter, Function<D, E> eGetter) {
        D d = safeGet(object, bGetter, cGetter, dGetter);
        return d == null ? null : eGetter.apply(d);
    }
}
