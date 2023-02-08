package by.dudko.questionnaires.mapper;

public interface MapperWithTargetObject<S, T> {
    T map(S source, T target);
}
