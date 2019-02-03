package com.desuo.activity.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Fuyuanwu
 * @date 2019/1/28 16:10
 */
public class JsonUtil {

    public static ObjectMapper getInstance() {
        return Instance.objectMapper;
    }

    private static class Instance {
        private static ObjectMapper objectMapper = new ObjectMapper();
    }
}
