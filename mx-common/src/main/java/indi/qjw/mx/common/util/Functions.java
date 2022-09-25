package indi.qjw.mx.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

public enum Functions {
    ;

    public static <T> Consumer<T> empty() {
        return t -> {
        };
    }

    public static <T, V> BiConsumer<T, V> bEmpty() {
        return (t, v) -> {
        };
    }

    public static <T, V, R> Function<T, R> join(@NonNull Function<T, V> before,
            @NonNull Function<V, R> after) {
        return before.andThen(after);
    }

    public static <T, U, V, R> Function<T, R> join(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Function<V, R> func3) {
        return func1.andThen(func2).andThen(func3);
    }

    public static <T, U, V, W, R> Function<T, R> join(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Function<V, W> func3,
            @NonNull Function<W, R> func4) {
        return func1.andThen(func2).andThen(func3).andThen(func4);
    }

    public static <T, V> Predicate<T> joinP(@NonNull Function<T, V> function,
            @NonNull Predicate<V> predicate) {
        return t -> predicate.test(function.apply(t));
    }

    public static <T, U, V> Predicate<T> joinP(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Predicate<V> predicate) {
        return t -> predicate.test(func2.apply(func1.apply(t)));
    }

    public static <T, U, V, W> Predicate<T> joinP(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Function<V, W> func3,
            @NonNull Predicate<W> predicate) {
        return t -> predicate.test(func3.apply(func2.apply(func1.apply(t))));
    }

    public static <T, U, V, W, X> Predicate<T> joinP(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Function<V, W> func3,
            @NonNull Function<W, X> func4,
            @NonNull Predicate<X> predicate) {
        return t -> predicate.test(func4.apply(func3.apply(func2.apply(func1.apply(t)))));
    }

    public static <T, R> Consumer<T> joinC(@NonNull Function<T, R> function,
            @NonNull Consumer<R> consumer) {
        return t -> consumer.accept(function.apply(t));
    }

    public static <T, U, V> Consumer<T> joinC(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Consumer<V> consumer) {
        return t -> consumer.accept(func2.apply(func1.apply(t)));
    }

    public static <T, U, V, W> Consumer<T> joinC(@NonNull Function<T, U> func1,
            @NonNull Function<U, V> func2,
            @NonNull Function<V, W> func3,
            @NonNull Consumer<W> consumer) {
        return t -> consumer.accept(func3.apply(func2.apply(func1.apply(t))));
    }

    @SafeVarargs
    public static <T> Consumer<T> joinCC(@NonNull Consumer<T>... consumers) {
        return Arrays.stream(consumers)
                .filter(Objects::nonNull)
                .reduce(Consumer::andThen)
                .orElseGet(Functions::empty);
    }

    @SafeVarargs
    public static <T, R> Function<T, R> joinCS(@NonNull Supplier<R> supplier, @NonNull Consumer<T>... consumers) {
        return t -> {
            joinCC(consumers).accept(t);
            return supplier.get();
        };
    }

    @SafeVarargs
    public static <T> UnaryOperator<T> c2f(@NonNull Consumer<T>... consumers) {
        return t -> {
            joinCC(consumers).accept(t);
            return t;
        };
    }


    @SafeVarargs
    public static <T, R> Function<T, R> joinCF(@NonNull Function<T, R> func, @NonNull Consumer<T>... consumers) {
        return t -> {
            joinCC(consumers).accept(t);
            return func.apply(t);
        };
    }


    public static <T> Function<T, Boolean> p2F(@NonNull Predicate<T> predicate) {
        return predicate::test;
    }

    public static <T> Predicate<T> f2P(@NonNull Function<T, Boolean> func) {
        return t -> Optional.ofNullable(func.apply(t)).orElse(Boolean.FALSE);
    }

    public static <T> Consumer<T> ifThen(@NonNull Predicate<T> predicate, @NonNull Consumer<T> thenFunc) {
        return t -> {
            if (predicate.test(t)) {
                thenFunc.accept(t);
            }
        };
    }

    public static <T> Consumer<T> ifThenElse(@NonNull Predicate<T> predicate,
            @NonNull Consumer<T> thenFunc, @NonNull Consumer<T> elseFunc) {
        return t -> {
            if (predicate.test(t)) {
                thenFunc.accept(t);
            } else {
                elseFunc.accept(t);
            }
        };
    }

    public static <T> Supplier<T> constant(T value) {
        return () -> value;
    }

    public static IntSupplier constantInt(int value) {
        return () -> value;
    }

    public static LongSupplier constantLong(long value) {
        return () -> value;
    }

    public static DoubleSupplier constantDouble(double value) {
        return () -> value;
    }

    public static <T> Predicate<T> always(boolean value) {
        return t -> value;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return always(true);
    }

    public static <T> Predicate<T> alwaysFalse() {
        return always(false);
    }

    public static <T, R> Function<T, R> always(R value) {
        return t -> value;
    }

    public static <T> UnaryOperator<T> identity() {
        return t -> t;
    }

    public static <T> Predicate<T> equal(T t) {
        return Predicate.isEqual(t);
    }

    public static <T> Predicate<T> notEqual(T t) {
        return Predicate.<T>isEqual(t).negate();
    }

    public static <T, R> Function<T, R> forSupplier(@NonNull Supplier<R> supplier) {
        return t -> supplier.get();
    }

    public static <T> ToIntFunction<T> forISupplier(@NonNull IntSupplier supplier) {
        return t -> supplier.getAsInt();
    }

    public static <T> ToLongFunction<T> forLSupplier(@NonNull LongSupplier supplier) {
        return t -> supplier.getAsLong();
    }

    public static <T> ToDoubleFunction<T> forDSupplier(@NonNull DoubleSupplier supplier) {
        return t -> supplier.getAsDouble();
    }

    public static <T, R> Function<T, R> forMap(@NonNull Map<T, R> map) {
        return forMapDefault(map, null);
    }

    public static <T, R> Function<T, Optional<R>> forMapOpt(@NonNull Map<T, R> map) {
        return key -> Optional.ofNullable(map.get(key));
    }

    public static <T, R> Function<T, R> forMapDefault(@NonNull Map<T, R> map, R defaultValue) {
        return key -> map.getOrDefault(key, defaultValue);
    }

    public static <T, V, R> Function<V, R> bindFirst(@NonNull BiFunction<T, V, R> func, T first) {
        return v -> func.apply(first, v);
    }

    public static <T, V, R> Function<T, R> bindSecond(@NonNull BiFunction<T, V, R> func, V second) {
        return t -> func.apply(t, second);
    }

    public static <T> UnaryOperator<T> bBindFirst(@NonNull BinaryOperator<T> func, T first) {
        return v -> func.apply(first, v);
    }

    public static <T> UnaryOperator<T> bBindSecond(@NonNull BinaryOperator<T> func, T second) {
        return t -> func.apply(t, second);
    }

    public static <T> Supplier<T> uBindFirst(@NonNull UnaryOperator<T> func, T first) {
        return () -> func.apply(first);
    }

    public static <T, V> Consumer<V> cBindFirst(@NonNull BiConsumer<T, V> func, T first) {
        return v -> func.accept(first, v);
    }

    public static <T, V> Consumer<T> cBindSecond(@NonNull BiConsumer<T, V> func, V second) {
        return t -> func.accept(t, second);
    }

    public static <T, V> Predicate<V> pBindFirst(@NonNull BiPredicate<T, V> func, T first) {
        return v -> func.test(first, v);
    }

    public static <T, V> Predicate<T> pBindSecond(@NonNull BiPredicate<T, V> func, V second) {
        return t -> func.test(t, second);
    }

    public static <T> BooleanSupplier bsBindFirst(@NonNull Predicate<T> predicate, T first) {
        return () -> predicate.test(first);
    }

    public static <T, R> Supplier<R> sBindFirst(@NonNull Function<T, R> func, T first) {
        return () -> func.apply(first);
    }

    public static <T> Runnable rBindFirst(@NonNull Consumer<T> consumer, T first) {
        return () -> consumer.accept(first);
    }

    public static <T, R> R foldSequential(Stream<T> stream, R identity, BiFunction<R, T, R> accumulator) {
        if (stream == null) {
            return identity;
        }
        return fold(stream.sequential(), identity, accumulator, bFirstArg());
    }

    public static <T> BinaryOperator<T> bFirstArg() {
        return (t, v) -> t;
    }

    public static <T, R> Optional<R> fold(Stream<T> stream,
                                          BiFunction<R, T, R> accumulator, BinaryOperator<R> selector) {
        return Optional.ofNullable(fold(stream, null, accumulator, selector));
    }

    public static <T, R> R fold(Stream<T> stream, R identity,
                                BiFunction<R, T, R> accumulator, BinaryOperator<R> selector) {
        if (stream == null) {
            return identity;
        }
        Holder<R> holder = new Holder<>(identity);
        BiFunction<Holder<R>, T, Holder<R>> wrapAccumulator = (h, t) ->
                new Holder<>(accumulator.apply(h.getValue(), t));
        BinaryOperator<Holder<R>> combiner = (h1, h2) ->
                h1.setValue(selector.apply(h1.getValue(), h2.getValue()));
        return stream.reduce(holder, wrapAccumulator, combiner).getValue();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static final class Holder<T> implements
            UnaryOperator<T>, Consumer<T>,
            Supplier<T>, Predicate<Object> {
        private T value;

        public Holder<T> setValue(T value) {
            this.value = value;
            return this;
        }

        @Override
        public T apply(T t) {
            T oldValue = value;
            value = t;
            return oldValue;
        }

        @Override
        public void accept(T t) {
            setValue(t);
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean test(Object o) {
            return value != null;
        }
    }

}
