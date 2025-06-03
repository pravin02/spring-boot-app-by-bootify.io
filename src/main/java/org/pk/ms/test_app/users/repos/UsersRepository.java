package org.pk.ms.test_app.users.repos;

import org.pk.ms.test_app.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByUsernameIgnoreCase(String username);

}
