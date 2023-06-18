package org.evs.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.evs.models.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


/**
 * 摘录实现 jackson 工具类
 */
public class JacksonUtils {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);

    private static volatile ObjectMapper JACKSON_MAPPER;

    private static final List<JsonReadFeature> JSON_READ_FEATURES_ENABLED = Arrays.asList(
        //允许在JSON中使用Java注释
        JsonReadFeature.ALLOW_JAVA_COMMENTS,
        //允许 json 存在没用双引号括起来的 field
        JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES,
        //允许 json 存在使用单引号括起来的 field
        JsonReadFeature.ALLOW_SINGLE_QUOTES,
        //允许 json 存在没用引号括起来的 ascii 控制字符
        JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS,
        //允许 json number 类型的数存在前导 0 (例: 0001)
        JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS,
        //允许 json 存在 NaN, INF, -INF 作为 number 类型
        JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS,
        //允许 只有Key没有Value的情况
        JsonReadFeature.ALLOW_MISSING_VALUES,
        //允许数组json的结尾多逗号
        JsonReadFeature.ALLOW_TRAILING_COMMA
    );

    static {
        try {
            //初始化
            JacksonUtils.JACKSON_MAPPER = initMapper();
        } catch (Exception e) {
            log.error("jackson config error", e);
        }
    }

    public static ObjectMapper initMapper() {
        JsonMapper.Builder builder = JsonMapper.builder().enable(
            JSON_READ_FEATURES_ENABLED.toArray(JsonReadFeature[]::new)
        );

        return JacksonUtils.initMapperConfig(builder.build());
    }

    private static ObjectMapper initMapperConfig(ObjectMapper objectMapper) {
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
        // 命名自动转换
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        //配置序列化级别
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //配置JSON缩进支持
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        //允许单个数值当做数组处理
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //禁止重复键, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //对象为空时不抛异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //时间格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //允许未知字段
        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //识别Java8时间
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

    public static <T> T getJsonStringByPath(String json, String path, Class<T> clazz) throws JsonProcessingException {
        String[] paths = path.split(",");  // 可以使用pattern代替 更多样的
        JsonNode treeNodes = JACKSON_MAPPER.readTree(json);

        for(String pathLevel : paths) {
            treeNodes = treeNodes.path(pathLevel);
            if (treeNodes.isMissingNode()){
                // 没有当前节点
                break;
            }
        }

        if(treeNodes.isMissingNode()){
            throw new BusinessException("json parse from path error !");
        }

        return JACKSON_MAPPER.treeToValue(treeNodes, clazz);
    }

    // 将对象转换为json string
    public static String toJsonString(Object obj) throws JsonProcessingException {
        return JacksonUtils.JACKSON_MAPPER.writeValueAsString(obj);
    }

    public static <T> T fromJsonString(String json, Class<T> clazz) throws JsonProcessingException {
        return JacksonUtils.JACKSON_MAPPER.readValue(json, clazz);
    }

    public static <T> T fromJsonString(String json, Type typeOfT) throws JsonProcessingException {
        return JacksonUtils.JACKSON_MAPPER.readValue(
            json, JacksonUtils.JACKSON_MAPPER.getTypeFactory().constructType(typeOfT)
        );
    }
}




