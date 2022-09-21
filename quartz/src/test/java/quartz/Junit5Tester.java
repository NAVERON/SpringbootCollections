package quartz;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


/**
 * 官方文档 
 * https://junit.org/junit5/docs/current/user-guide/#overview 
 * @author wangy
 *
 */

@ExtendWith(SpringExtension.class)  // 涉及到需要spring的一些组件 注入
@SpringBootTest   // 启动spring 上下文 
class Junit5Tester {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Junit5Tester.class);

    //@Test   // repeattest 和test注解不能同时使用 
    @RepeatedTest(value = 3)
    @DisplayName(value = "Repeat Test = 3")
    public void repeatTest() {
        LOGGER.info("@Repeat Test ---");
        
        Assertions.assertEquals(1, 1);  // 断言 
    }
    
    /**
     * 必须使用静态方法 
     * https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/BeforeAll.html  
     * @BeforeAll methods must have a void return type, must not be private, and must be static by default.  
     */
    @BeforeAll 
    public static void beforeAll() {
        LOGGER.info("在所有之前 --> @BeforeAll");
    }
    
    @AfterAll 
    public static void afterAll() {
        LOGGER.info("在所有之后 --> @After All");
    }
    
    @BeforeEach 
    public void beforeEach() {
        LOGGER.info("每个测试之前的动作 --> @Before each");
    }
    
    @AfterEach 
    public void afterEach() {
        LOGGER.info("每个测试之后的动作 --> @After each");
    }
    
    @Test 
    public void testSth() {
        LOGGER.info("do sth for test");
    }
    
    
}











