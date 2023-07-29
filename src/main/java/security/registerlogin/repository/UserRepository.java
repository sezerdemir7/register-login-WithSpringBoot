package security.registerlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.registerlogin.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
