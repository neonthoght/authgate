package my.steam.authgate;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

//@RestController
@Controller
@RequestMapping(value="/auth/")
public class AuthgateController {

    @GetMapping(value="signup")
    public String signup() {
        return "signup";
    }
}
