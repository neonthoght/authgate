package my.steam.authgate;
import my.steam.authgate.UserSMA;
import my.steam.authgate.AuthgateRepository;
import org.springframework.stereotype.Service; 
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import java.util.UUID;

@Service
public class AuthgateService {
    public String username; 
    public String password; 
    public String token;
    public int result = 0; // результат выполнения метода 
    AuthgateRepository userRepository; 
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    MailSender mailSender;

    
    public AuthgateService (AuthgateRepository userRepository) {
        this.userRepository = userRepository;
    } 


    // Отправить письмо для подтверждения email
    public void sendVerificationEmail(String recipientEmail, String token) {
        
        String confirmationUrl = "http://localhost:8081/auth/verify?token=" + token;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение регистрации");
        message.setText("Перейдите по ссылке для подтверждения аккаунта: " + confirmationUrl);
        
        mailSender.send(message);
    }

    // Зарегистрировать пользователя
    // 0 - успешно создан, 
    // 1 - пользователь уже существует
    // 2 - email уже используется
    public int signup(String username, String password, String email){
        
        if (userExists(username)) {
            result = 1;
        } 
        else if (emailExists(email)) {
            result = 2;
        }
        else {
 
            //шифруем пароль
            password = passwordEncoder.encode(password);

            

            // сохраняем пользователя в БД и отправляем письмо для подтверждения email
            token = UUID.randomUUID().toString();
            UserSMA user = new UserSMA(username, password, email, token);
            userRepository.save(user);

            sendVerificationEmail(email, token);
            result = 0;

        }
        return result;
    }

    // Токен совпадает с тем, что лежит в auth.users(token) - 0 
    // Токен не совпадает - 1
    public int compareToken(String token) {

        if (token.equals(userRepository.findByToken(token).get().token)) {

            result = 0;
        }
        else result = 1;

        return result;
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


}
