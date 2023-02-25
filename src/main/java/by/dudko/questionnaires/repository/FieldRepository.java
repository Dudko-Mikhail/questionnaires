package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByUserIdOrderByOrder(long userId, Pageable pageable);

    @Query("select coalesce(max(f.order), 0) from Field f where f.user.id = :userId")
    int findMaxOrderByUserId(@Param(value = "userId") long userId);
}
