package my.steam.authgate;
import my.steam.authgate.UserSMA;
import my.steam.authgate.AuthgateRepository;
import org.springframework.stereotype.Service; 
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AuthgateService {
    public String username; 
    public String password; 
    public int result = 0; // результат выполнения метода 
    AuthgateRepository userRepository; 

    
    public AuthgateService (AuthgateRepository userRepository) {
        this.userRepository = userRepository;
    } 



    
    public int signup(String username, String password){
        UserSMA user = new UserSMA(username, password);

        if (userExists(username)) {
            result = 1;
        } else {
            // сохраняем в БД
            //UserSMA user = new UserSMA(username, password);
            //user.username = this.username;
            //user.password = this.password;
            userRepository.save(user);

            // добавляем в контекст 
            result = 0;

        }
        return result;
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


}
