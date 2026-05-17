package my.steam.authgate;
import my.steam.authgate.UserSMA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface AuthgateRepository extends CrudRepository<UserSMA, String>{

    Optional<UserSMA> findByUsername(String username);
    Optional<UserSMA> findByEmail(String email);
    Optional<UserSMA> findByToken(String token);
}
