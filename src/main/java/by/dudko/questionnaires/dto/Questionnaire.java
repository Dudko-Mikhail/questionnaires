package by.dudko.questionnaires.dto;

public interface Questionnaire {
    long getUserId();

    String getEmail();

    int getFieldsCount();

    int getRequiredFieldsCount();
}
