package security;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import security.model.User;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test"})
public class TestForSecurity {

    /**
     * mvc controller层测试
     * service  业务逻辑测试
     * dao 数据持久化测试
     */
    @Resource
    private MockMvc mvc;
    @Resource
    private TestRestTemplate testRestTemplate;

    @Disabled
    @Test
    public void test1() throws Exception {
        User user = BDDMockito.mock(User.class);

        mvc.perform(
                MockMvcRequestBuilders.request(HttpMethod.GET, "/simple/home")
                    .header("Authentication", "Bearer xxxx")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    }


}
