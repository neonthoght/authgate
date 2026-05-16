package my.steam.authgate;
import my.steam.authgate.UserSMA;
import my.steam.authgate.AuthgateRepository;
import org.springframework.stereotype.Service; 
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class AuthgateService {
    public String username; 
    public String password; 
    public int result = 0; // результат выполнения метода 
    AuthgateRepository userRepository; 
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    public AuthgateService (AuthgateRepository userRepository) {
        this.userRepository = userRepository;
    } 



    // Зарегистрироваться
    // 0 - успешно создан, 1 - пользователь уже существует
    public int signup(String username, String password){
        
        if (userExists(username)) {
            result = 1;
        } else {
 
            //шифруем пароль
            password = passwordEncoder.encode(password);
            UserSMA user = new UserSMA(username, password);

            userRepository.save(user);


            result = 0;

        }
        return result;
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


}
