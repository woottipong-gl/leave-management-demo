package com.woottipong.leavemanagementdemo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {
    @Qualifier("messageSerializer")
    @Bean
    public Serializer messageSerializer(ObjectMapper mapper) {
        ObjectMapper newMapper = new ObjectMapper();
        newMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
        newMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return JacksonSerializer.builder()
                .objectMapper(newMapper)
                .lenientDeserialization()
                .build();
    }
}
