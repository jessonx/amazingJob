package com.xcm.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

/**
 * Created by 35394 on 2016/10/17.
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        this.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true);
        this.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
        this.configure(SerializationConfig.Feature.WRITE_EMPTY_JSON_ARRAYS,
                true);
        this.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        this.getSerializationConfig().setSerializationInclusion(
                JsonSerialize.Inclusion.ALWAYS);
    }
}