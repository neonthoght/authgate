package my.steam.authgate;
import my.steam.authgate.UserSMA;
import org.springframework.stereotype.Service; 
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.repository.CrudRepository;

@Service
public class AuthgateService {
    public String username; 
    public String password; 
    public int result = 0; // результат выполнения метода
    UserSMA user; 


    CrudRepository<UserSMA, String> userRepository; 

    public int signup(String username, String password){
        
        if (userExists(username)) {
            result = 1;
        } else {
            // сохраняем в БД
            user.username = this.username;
            user.password = this.password;
            userRepository.save(user);

            // добавляем в контекст 
            result = 0;

        }
        return result;
    }

    public boolean userExists(String username) {
        userRepository.findById(username).isPresent();
        return true;
    }


}
