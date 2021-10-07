package blog.repository;

import blog.model.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, String> {
    ActivationToken findByActivationToken(String activationToken);
}