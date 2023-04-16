package security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@AutoConfigureMockMvc
public class BirthdayTest {

    @Resource
    private MockMvc mvc;

    @Test
    public void test1() throws Exception {
        String dateString = LocalDate.of(2022, 12, 5).format(DateTimeFormatter.ISO_DATE);
        this.testValidBirthDay(dateString);
    }

    public void testValidBirthDay(String dateString) throws Exception {
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/tester/valid", "2021-04-12")
            )
            .andDo(MockMvcResultHandlers.print());
        MockHttpServletResponse response = result.andReturn().getResponse();

        System.out.println(response.getContentAsString());
    }

}
