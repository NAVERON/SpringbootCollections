package common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    /**
     * create token
     * @param infos
     * @return
     */
    public String createJWTToken(Map<String, String> infos) {
        Algorithm algorithm = Algorithm.HMAC256(JWTUtils.PRIVATE_SECRET);
        Date date = new Date(System.currentTimeMillis() + JWTUtils.EXPIRE_TIME);

        JWTCreator.Builder builder = JWT.create();
        infos.forEach(builder::withClaim);

        // other sign
        ZonedDateTime current = Instant.now().atZone(ZoneId.systemDefault());
        builder.withIssuedAt(current.toInstant())
                .withExpiresAt(current.plusSeconds(JWTUtils.EXPIRE_TIME).toInstant())
                .withIssuer(JWTUtils.ISSUER)
                .withAudience(JWTUtils.AUDIENCE);

        return builder.sign(algorithm);
    }

    /**
     * verify token
     * if validate --> parse jwt and get claims
     * else throw Exception
     * @param idToken token
     */
    public Map<String, Object> validateOIDCToken(String idToken) {

        Algorithm algorithm = Algorithm.HMAC256(JWTUtils.PRIVATE_SECRET);

        Verification builder = JWT.require(algorithm);
        JWTVerifier verifier = builder
                .withIssuer(JWTUtils.ISSUER)
                .withAudience(JWTUtils.AUDIENCE)
                .acceptLeeway(10)
                .build();

        // 校验 token
        try {
            DecodedJWT jwt = verifier.verify(idToken);
            log.info("jwt verify ok ------>");
            log.info("jwt claims --> \n{}", jwt.getClaims());

            Map<String, Object> result = new HashMap<>(jwt.getClaims());
            return result;
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            throw new JWTVerificationException(e.getMessage());
        }
    }

}





