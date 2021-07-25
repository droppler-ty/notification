package com.droppler.notification.converter;

public interface Converter <Source, Destination>{
    Destination convert(Source source);
}
