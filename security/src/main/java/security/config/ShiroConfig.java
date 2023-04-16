package security.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * doc : https://shiro.apache.org/spring-boot.html
 * smaple : https://github.com/apache/shiro/tree/main/samples/spring-boot-web
 * shiro architecture : https://shiro.apache.org/architecture.html
 */
@Configuration
public class ShiroConfig {

    private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public Realm customeRealm() {
        return new ShiroCustomeRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm customeRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customeRealm);

        // 关闭shiroDAO功能 -- 需要整理下
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();

        // 不需要 shiro session 中的东西存到任何地方
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);

        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // login url 登陆api 可以不认证
        chainDefinition.addPathDefinition("/home/login", "anon");

        // logged in users with the 'document:read' permission
//        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
        // all other paths require a logged in user
//        chainDefinition.addPathDefinition("/**", "authc");

        // 都需要登陆
        chainDefinition.addPathDefinition("/**", "authc");

        return chainDefinition;
    }

}









