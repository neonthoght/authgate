package my.steam.authgate;
import my.steam.authgate.UserSMA;
import my.steam.authgate.AuthgateRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.session.Session;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
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
    public Session session;
    public String sessionParams;
    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public JavaMailSenderImpl mailSender; //= new JavaMailSenderImpl();



    
    public AuthgateService (AuthgateRepository userRepository, JavaMailSenderImpl mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }
    
    

    
    //получить атрибуты сессии, атрибуты сессии берем по jsessionid из headers
    public String getSessionParams(HttpSession session) {
        System.out.println("sessionid: " + session.getId());
        return  session.getId();
    }

    // Вход в систему
    public int signin(String username, String password, HttpSession session) throws Exception {
        
        //шифруем пароль
        //password = passwordEncoder.encode(password);

        user = new UserSMA(username, password);
        
        user = userRepository.findByUsername(username).get();
        if (user == null ) { // Пользователь не найден!
            System.out.println("Пользователь не найден!");
            result = 1;
        } else {
            if (user.isActive == false) {
                result = 4;
                System.out.println("Пользователь неактивен!");
            } else {

                if (passwordEncoder.matches(password, user.password)) {
                    result = 0;
                    System.out.println("Пароль совпадает!");
                } else { // пароль не совпадает
                    result = 2;
                    System.out.println("Пароль не совпадает!");
                }
            }
        }
        // Добавляем в сессию имя зарегистрированного пользователя и флаг активности
        session.setAttribute("username", username);
        session.setAttribute("is_active", user.isActive);

        
        return result;
    }

    // Отправить письмо для подтверждения email
    public void sendVerificationEmail(String recipientEmail, UUID token)throws MailAuthenticationException {
        
        String confirmationUrl = "http://localhost:8081/auth/verify?token=" + token.toString();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение регистрации");
        message.setText("Перейдите по ссылке для подтверждения аккаунта: " + confirmationUrl);
        
        System.out.println("smtp user " + mailSender.getUsername());
        System.out.println("smtp password " + mailSender.getPassword());
        System.out.println("smtp port " + mailSender.getPort());     
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
    public int signup(String username, String password, String email, HttpSession session) throws Exception {
        
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

            // Добавляем в сессию имя зарегистрированного пользователя
            session.setAttribute("username", username);

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
