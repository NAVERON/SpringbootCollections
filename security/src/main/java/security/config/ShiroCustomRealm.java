package security.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自定义 认证和授权 过程
 */
public class ShiroCustomRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(ShiroCustomRealm.class);

    // 获取用户角色和权限的逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 授权 根据principal 获取登陆用户的信息 查询拥有的权限设置

        return null;
    }

    // 登录的认证过程 获取用户实际的信息比对登录传递过来的信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 认证用户的合法性 鉴定用户账号 正确
        String principal = (String) token.getPrincipal();
        log.warn("principal --> {}", principal);

        return new SimpleAuthenticationInfo(principal, "eron", getName());
    }

}


