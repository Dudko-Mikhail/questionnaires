package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findAllByUserIdOrderByOrder(long userId);
}
