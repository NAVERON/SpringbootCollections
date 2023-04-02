package oauth2.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "v1/oauth2")
public class OAuthLogin {

    private static final Logger log = LoggerFactory.getLogger(OAuthLogin.class);

    // http://localhost:8080/login
    @GetMapping(path = "home")
    public ModelAndView home(ModelAndView model) {

        model.setViewName("index");
        return model;
    }

    /**
     * doc : @see <a href = "https://gitee.com/api/v5/oauth_doc#/">https://gitee.com/api/v5/oauth_doc</a>
     * @param model
     * @param code
     * @return
     */
    @GetMapping(path = "gitee/callback")
    public ModelAndView giteeLoginCallback(ModelAndView model, @RequestParam(value = "code") String code, @RequestParam(name = "state") String state) {
        log.info("返回 gitee code --> {}", code);

        model.setViewName("index");
        model.addObject("user_name", "null");
        return model;
    }

    @GetMapping(path = "user")
    public ModelAndView user(ModelAndView model, @AuthenticationPrincipal OAuth2User user) {
        log.info("oauth2 user : {}", user.getName());

        model.setViewName("index");
        model.addObject("user_name", user.getName());
        return model;
    }

}
