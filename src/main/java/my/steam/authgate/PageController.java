package my.steam.authgate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller // возвращает только html страницы
public class PageController {

    @GetMapping(value="/auth/signin")
    public String singinPage(HttpSession session) {
        return "forward:/signin.html";
    }
}
