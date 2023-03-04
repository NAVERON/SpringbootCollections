package common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * 配置 spring restTemplate http客户端 请求
 */
@Component
public class SpringHttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(SpringHttpClientUtils.class);

    private final RestTemplate client;

    /**
     * 定制化restTemplate 比如日志拦截
     * @author ERON
     * restTemplate 的定制化
     */
    private static class OuterRestTemplateCustomizer implements RestTemplateCustomizer {

        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.getInterceptors().add((request, body, execution) -> {
                log.info("请求日志");
                log.warn("请求头 -> {}", request.getHeaders().toSingleValueMap());
                log.warn("请求方法 -> {}", request.getMethod().name());
                log.warn("请求url -> {}", request.getURI());

                return execution.execute(request, body);
            });

            // 设置 httpFactory 为jdk 自带http client
            // restTemplate.setRequestFactory();
            // https://gist.github.com/kasimok/04878482b9e7bc49a83ad5acfc74234e

        }

//        public void customize(RestTemplate restTemplate) {
//            restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
//
//                @Override
//                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
//                        throws IOException {
//                    log.info("请求日志");
//                    log.warn("请求头 -> {}", request.getHeaders().toSingleValueMap());
//                    log.warn("请求方法 -> {}", request.getMethod().name());
//                    log.warn("请求url -> {}", request.getURI());
//
//                    return execution.execute(request, body);
//                }
//            });
//        }

    }

    public SpringHttpClientUtils() {
        RestTemplateBuilder builder = new RestTemplateBuilder(new OuterRestTemplateCustomizer());
        client = builder.build();
    }

    /**
     * https://jsonplaceholder.typicode.com/  测试地址
     * @param url
     *    https://jsonplaceholder.typicode.com/posts/1
     * 参考教程 https://www.javaguides.net/2019/06/spring-resttemplate-get-post-put-and-delete-example.html
     * @return
     */
    // 通用get 请求
    public ResponseEntity<String> doGet(String url) {
        ResponseEntity<String> response = this.client.getForEntity(url, String.class);

        return response;
    }

    // 通用post请求
    public ResponseEntity<String> doPost(String url, Object obj, Map<String, String> params) {

        ResponseEntity<String> response = this.client.postForEntity(url, obj, String.class);

        return response;
    }

}



