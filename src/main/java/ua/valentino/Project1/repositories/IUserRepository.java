package ua.valentino.Project1.repositories;

import java.util.Optional;
import ua.valentino.Project1.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByResetPasswordToken(String resetPasswordToken);
}