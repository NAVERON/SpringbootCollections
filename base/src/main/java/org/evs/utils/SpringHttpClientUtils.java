package org.evs.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;


/**
 * 配置 spring restTemplate http客户端 请求
 */
public class SpringHttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(SpringHttpClientUtils.class);

    private SpringHttpClientUtils(){} // prevent instance

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
    }

    public static RestTemplate buildRestClient() {
        RestTemplateBuilder builder = new RestTemplateBuilder(new OuterRestTemplateCustomizer());

        return builder.build();
    }

}



