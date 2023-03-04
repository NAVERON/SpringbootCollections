package common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * JDK 自带 http 工具实现
 * https://medium.com/@ashokmathankumar/basic-authentication-with-java-11-httpclient-e129bb749fd3
 */
public class JDKHttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(JDKHttpClientUtils.class);

    private static volatile HttpClient client = null;

    // 单例模式
    public static HttpClient getDefaultHttpClient() {
        if (client == null) {
            synchronized (JDKHttpClientUtils.class) {
                if (client == null) {
                    client = HttpClient.newBuilder()
                            .followRedirects(HttpClient.Redirect.NORMAL)  // Redirect.SAME_PROTOCOL
                            .connectTimeout(Duration.ofMinutes(1))
                            //.proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
                            .version(HttpClient.Version.HTTP_2)
                            .build();
                }
            }
        }

        return client;
    }


    public static CompletableFuture<HttpResponse<String>> asyncHttpGet(String url) {
        log.info("http GET URL ==> " + url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> getAsyncResponse = getDefaultHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
        getAsyncResponse.whenComplete((response, error) -> {
            if(response != null) {
                log.info("get response => " + response.statusCode());
                log.info("get response body => " + response.body());
            }
            if(error != null) {
                log.info("get error => " + error.getMessage());
            }
        });

        return getAsyncResponse;
    }

    public static CompletableFuture<HttpResponse<String>> asyncHttpPost(String url, String requestBody){
        log.info("http POST URL ==> " + url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        CompletableFuture<HttpResponse<String>> postAsyncResponse = getDefaultHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
        postAsyncResponse.whenComplete((response, error) -> {
            if(response != null) {
                log.info("post response => " + response.statusCode());
                log.info("post response body => " + response.body());
            }
            if(error != null) {
                log.info("post error => " + error.getMessage());
            }
        });

        return postAsyncResponse;
    }


}



