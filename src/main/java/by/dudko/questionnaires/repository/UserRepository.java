package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.dto.Questionnaire;
import by.dudko.questionnaires.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true,
            value = "SELECT\n" +
                    "    u.id userId,\n" +
                    "    min(u.email) AS email,\n" +
                    "    COUNT(u.id) FILTER(where is_active = true) fieldsCount,\n" +
                    "    COUNT(u.id) FILTER(where is_active = true AND is_required = true) requiredFieldsCount\n" +
                    "FROM users u\n" +
                    "         LEFT JOIN fields f ON u.id = f.user_id\n" +
                    "GROUP BY u.id")
    Page<Questionnaire> findAllQuestionnaires(Pageable pageable);
}
