package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByUserIdOrderByOrder(long userId, Pageable pageable);
}
