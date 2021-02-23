package nl.conspect.drivedok.repositories;

import nl.conspect.drivedok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
