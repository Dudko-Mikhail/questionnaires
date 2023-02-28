package by.dudko.questionnaires.mapper;

import java.util.function.Supplier;

public interface UniversalMapper<S, T> {
    Supplier<S> sourceGenerator();

    Supplier<T> targetGenerator();

    default T map(S source) {
        return map(source, targetGenerator().get());
    }

    T map(S source, T target);

    default S reverseMap(T source) {
        return reverseMap(source, sourceGenerator().get());
    }

    S reverseMap(T source, S target);
}
