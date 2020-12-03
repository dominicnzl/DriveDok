package nl.conspect.drivedok.repositories;

import nl.conspect.drivedok.model.DriveDokUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveDokUserRepository extends JpaRepository<DriveDokUser, Long> {
}
