package com.demo.converter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.Timestamp;

@Configuration
public class SpringDataConverter extends JsonDeserializer {
//    @Autowired
//    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
//    @PostConstruct
//    public void addConversionConfig() {
//        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
//        if (initializer.getConversionService() != null) {
//            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
//            genericConversionService.addConverter(new FloatToTimestampConverter());
//        }
//    }


    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        Timestamp targetDate = null;
        String originDate = p.getText();
        if (StringUtils.isNotEmpty(originDate)) {
            try {
                targetDate = new Timestamp(Double.valueOf(originDate.trim()).longValue());
            } catch (NumberFormatException e) {
//                    try {
//                        targetDate = DateUtils.parseDate(originDate, DateJacksonConverter.pattern);
//                    } catch (ParseException pe) {
//
//                    }
            }
        }


        return targetDate;


    }
}



