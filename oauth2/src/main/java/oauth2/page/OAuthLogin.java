package oauth2.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "")
public class OAuthLogin {

    private static final Logger log = LoggerFactory.getLogger(OAuthLogin.class);

    @GetMapping(path = "home")
    public ModelAndView home(ModelAndView model) {

        model.setViewName("index");
        return model;
    }

    @GetMapping(path = "gitee/oauth2/callback")
    public ModelAndView giteeLoginCallback(ModelAndView model, @RequestParam(value = "code") String code) {
        log.info("返回 gitee code --> {}", code);

        model.setViewName("index");
        return model;
    }

    @GetMapping(path = "baidu/oauth2/callback")
    public ModelAndView baiduLoginCallback(ModelAndView model,@RequestParam(value = "code") String code) {
        log.info("返回的 baidu code --> {}", code);

        model.setViewName("index");
        return model;
    }


}
