package oauth2.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * doc : https://www.baeldung.com/spring-security-5-oauth2-login
 * 官方配置说明, 可以按照需要定制任何内容
 * official doc --> <a href = "https://docs.spring.io/spring-security/site/docs/5.2.12.RELEASE/reference/html/oauth2.html" >spring security oauth doc</a>
 */
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Resource
    private OAuth2ClientProperties oAuth2ClientProperties;  // 配置读取 properties 映射文件

    @Bean
    public SecurityFilterChain oauth(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/v1/oauth2/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                .loginPage("/v1/oauth2/home")
                .defaultSuccessUrl("/v1/oauth2/home")
                .failureUrl("/v1/oauth2/home")
            ;

        return http.build();
    }

//    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {  // 覆盖自动读取的配置
        log.info("OAuth2ClientProperties --> \n{}, \n{}", this.oAuth2ClientProperties.getRegistration().toString(), this.oAuth2ClientProperties.getProvider().toString());

        return new InMemoryClientRegistrationRepository(this.giteeRegistration());
    }

    private ClientRegistration giteeRegistration() {

        ClientRegistration giteeRegistration = ClientRegistration
                .withRegistrationId("gitee")
                .authorizationUri("https://gitee.com/oauth/authorize")
                .tokenUri("https://gitee.com/oauth/token")
                .clientId("d996d4579a8c1f7f043dfd0d5c1a63f1c301ca06587a8fedd54f661cb8088c8e")
                .clientSecret("8a5802693fe02d1fc31f01a6191fcba2fb08f829edc22359b60f70c485bc2eb1")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/v1/oauth2/gitee/callback")
                .build();

        return giteeRegistration;
    }

}





