package com._DSF.je.Repository;

import com._DSF.je.Entity.User;
import com._DSF.je.Enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByRole(Role role);
    List<User> findByUsernameContainingIgnoreCaseAndRole(String username, Role role);

}
