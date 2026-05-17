package my.steam.authgate;
import my.steam.authgate.UserSMA;
import my.steam.authgate.AuthgateRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import java.util.UUID;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.MailAuthenticationException;

@Service
public class AuthgateService {
    public String username; 
    public String password; 
    public UUID token;
    public int result = 0; // результат выполнения метода 
    public AuthgateRepository userRepository; 
    public UserSMA user;
    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public MailSender mailSender; //= new JavaMailSenderImpl();



    
    public AuthgateService (AuthgateRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    } 


    // Отправить письмо для подтверждения email
    public void sendVerificationEmail(String recipientEmail, UUID token)throws MailAuthenticationException {
        
        String confirmationUrl = "http://localhost:8081/auth/verify?token=" + token.toString();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение регистрации");
        message.setText("Перейдите по ссылке для подтверждения аккаунта: " + confirmationUrl);
        
        mailSender.send(message);
    }

    // пользователь перешёл по ссылке - подтверждает почту при регистрации. 
    // в ссылке токен, который надо сравнить с токеном из БД
    // 0 - email подтвержден, зарегистрированная учетная запись (УЗ) становится активной. 
    // 1 - ошибка, токен не найден.
    public int verifyEmail(UUID token) {
        if (compareToken(token) == 0) {
            user = userRepository.findByToken(token).get();
            user.isActive = true;
            userRepository.save(user);
            result = 0;
        } else result = 1;
        return result;
    }

    // Зарегистрировать пользователя
    // 0 - успешно создан, 
    // 1 - пользователь уже существует
    // 2 - email уже используется
    public int signup(String username, String password, String email) throws Exception {
        
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
            token = UUID.randomUUID();
            user = new UserSMA(username, password, email, token);
            sendVerificationEmail(email, token);
            userRepository.save(user);
            result = 0;

        }
        return result;
    }

    // Токен совпадает с тем, что лежит в auth.users(token) - 0 
    // Токен не совпадает - 1
    public int compareToken(UUID token) {

        if (token.toString().equals(userRepository.findByToken(token).get().token.toString())) {

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
