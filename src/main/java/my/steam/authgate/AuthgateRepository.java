package my.steam.authgate;
import my.steam.authgate.UserSMA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthgateRepository extends CrudRepository<UserSMA, String>{

    /*
    // создать пользователя в БД/зарегистрироваться, true - сохранено в БД, false - не сохранил
    public boolean saveUser(String password, String username);

    // получить пользователя из БД
    public User getUser(String username);
    */

    
}
