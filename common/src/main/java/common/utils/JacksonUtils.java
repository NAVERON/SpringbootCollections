package common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JacksonUtils {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);

    public static void listToJson(List<String> list) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(list);
            log.info("list to json --> {}", result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void jsonToList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode result = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
