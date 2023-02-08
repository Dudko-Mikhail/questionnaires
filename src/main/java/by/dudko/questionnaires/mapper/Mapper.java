package by.dudko.questionnaires.mapper;

public interface Mapper<S, T> {
    T map(S source);
}
