package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select count(u) = 0 from User u where u.id <> :userId and u.email = :email")
    boolean isEmailUniqueExceptUserWithId(@Param("email") String email, @Param("userId") long userId);
}
