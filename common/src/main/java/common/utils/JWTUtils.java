package common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt token 工具类
 * 学习地址 : https://zhuanlan.zhihu.com/p/611080386
 */
public class JWTUtils {

    private static final Logger log = LoggerFactory.getLogger(JWTUtils.class);

    private static final String ISSUER = "https://your-issuer.com";
    private static final String AUDIENCE = "you-client-id";
    private static final String JWK_URL = "";  // 获取公钥的地址

    private static final String PRIVATE_SECRET = "";  // 加密密钥
    private static final Long EXPIRE_TIME = 30 * 60 * 1000L;  // ms --> 30 min

    // 创建jwt
    public String createJWTToken(Map<String, String> infos) {
        Algorithm algorithm = Algorithm.HMAC256(JWTUtils.PRIVATE_SECRET);
        Date date = new Date(System.currentTimeMillis() + JWTUtils.EXPIRE_TIME);

        JWTCreator.Builder builder = JWT.create();
        infos.forEach(builder::withClaim);
        builder.withExpiresAt(date);

        return builder.sign(algorithm);

    }

    // 验证
    public void validateOIDCToken(String idToken, Map<String, String> infos) {

        // 判断 issuer audience 是否正确
        Algorithm algorithm = Algorithm.HMAC256(JWTUtils.PRIVATE_SECRET);

        Verification builder = JWT.require(algorithm);
        infos.forEach(builder::withClaim);
        JWTVerifier verifier = builder.build();

        // 校验 token
        DecodedJWT jwt = verifier.verify(idToken);
        return ;
    }

    // 解析 claims
    public void parseToken(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        Map<String, String> infos = new HashMap<>();
        jwt.getClaims().entrySet().forEach(claim -> {
            infos.put(claim.getKey(), claim.getValue().asString());
        });

        log.info("claims ==> {}", infos);
    }


}





