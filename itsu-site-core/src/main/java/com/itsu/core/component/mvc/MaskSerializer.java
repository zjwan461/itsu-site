package com.itsu.core.component.mvc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.itsu.core.vo.sys.ItsuSiteConstant;

import java.io.IOException;

/**
 * @author Jerry.Su
 * @Date 2021/7/20 16:37
 */
public class MaskSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(ItsuSiteConstant.MASK_CONTENT);
    }
}
