package my.steam.authgate;
import my.steam.authgate.AuthgateService;
import my.steam.authgate.AuthgateRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import my.steam.authgate.UserSMA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;


@RestController
//@Controller
@RequestMapping(value="/auth")
public class AuthgateController {

    AuthgateService gateService;
    AuthgateRepository gateRepository;
    int result = 0;
    ResponseEntity<String> response;
    UserSMA user;

    public AuthgateController(AuthgateService gateService, AuthgateRepository gateRepository) {
        this.gateService = gateService; 
        this.gateRepository = gateRepository;
    }

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody UserSMA user, HttpSession session) throws Exception {
        System.out.println(user.username + user.password);
        result = gateService.signup(user.username, user.password, user.email, session);
        if (result == 0) {
            response = ResponseEntity.status(200).body("user created!");
        } 
        else if (result == 2) { // email уже используется
            response = ResponseEntity.status(200).body("email already used!");
        } 
        else {
            response = ResponseEntity.status(200).body("user already exists!");
            System.out.println(response);
        }

        return response;
    }
    
    @GetMapping(value="/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam UUID token) {
        gateService.verifyEmail(token);
        return  response;
    }

    @PostMapping(value="/signin")
    public ResponseEntity<String> signin(@RequestBody UserSMA user, HttpSession session) throws Exception {

        result = gateService.signin(user.username, user.password, session);
        return response;
    }

}
