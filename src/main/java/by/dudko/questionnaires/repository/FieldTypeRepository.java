package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.FieldType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldTypeRepository extends JpaRepository<FieldType, Integer> {
    Optional<FieldType> findByValue(String value);
}
