package com.demo.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;

public class FloatToTimestampConverter implements Converter<Float, Timestamp> {
    @Override
    public Timestamp convert(Float source) {
        return source == null ? null : new Timestamp(source.longValue());
    }
}
