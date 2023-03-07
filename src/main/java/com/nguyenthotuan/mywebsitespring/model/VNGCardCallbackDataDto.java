package com.nguyenthotuan.mywebsitespring.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.util.List;

/**
 * Created by tuannt7 on 07/03/2023
 */
@Data
public class VNGCardCallbackDataDto {
    private long ts;
    private String sig;
    private String orderNumber;
    @JsonDeserialize(using = ListCardDeserializer.class)
    private List<CardCallbackInfo> listCards;

    @Data
    public static class CardCallbackInfo {
        private String serialNumber;
        private String password;
        private long denomination;
        private String currency;
        private String expiredDate;
        private String cardType;
    }

    private static class ListCardDeserializer extends JsonDeserializer<List<CardCallbackInfo>> {
        @Override
        public List<CardCallbackInfo> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            String hobbies = node.asText();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(hobbies, new TypeReference<List<CardCallbackInfo>>(){});
        }
    }

}
