package security.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * doc : https://shiro.apache.org/spring-boot.html
 * smaple : https://github.com/apache/shiro/tree/main/samples/spring-boot-web
 */
@Configuration
public class ShiroConfig {

    private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public Realm realm() {
        return new Realm() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean supports(AuthenticationToken token) {
                return false;
            }

            @Override
            public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                return null;
            }
        };
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }


}









