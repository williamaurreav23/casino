package at.steiner.casino.repository;

import at.steiner.casino.domain.User;
import at.steiner.casino.domain.UserExtra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {
    Optional<UserExtra> getByUser(User user);
}
