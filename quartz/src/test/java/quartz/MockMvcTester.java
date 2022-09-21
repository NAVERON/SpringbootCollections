package quartz;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;


/**
 * 官方文档 https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications  
 * 
 * @author wangy
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ) 
@AutoConfigureMockMvc 
@AutoConfigureWebTestClient 
class MockMvcTester {
    
    @Test 
    void testWithMockMvc(@Autowired MockMvc mvc) throws Exception {
        mvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(
                content().string("Hello World")
            );
    }

    // If Spring WebFlux is on the classpath, you can drive MVC tests with a WebTestClient
    @Test 
    void testWithWebTestClient(@Autowired WebTestClient webClient) {
        webClient
                .get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello World");
    }
    
    // WebFlux 
    @Test
    void exampleTest(@Autowired WebTestClient webClient) {
        webClient
            .get().uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class).isEqualTo("Hello World");
    }
    
    
}















