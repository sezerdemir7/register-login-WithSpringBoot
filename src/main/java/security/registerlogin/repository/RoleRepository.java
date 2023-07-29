package security.registerlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.registerlogin.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
