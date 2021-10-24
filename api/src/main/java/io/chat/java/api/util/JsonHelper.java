package io.chat.java.api.util;

import io.chat.java.api.support.annotation.Exclude;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.security.core.GrantedAuthority;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class JsonHelper {
    private static final Gson GSON = new GsonBuilder()
            .disableInnerClassSerialization()
            .setDateFormat("yyyyMMddHHmmss")
            .disableHtmlEscaping()
//            .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
//            .registerTypeAdapter(GrantedAuthority.class, new GrantedAuthoritySerializer())
//            .registerTypeAdapter(LocalDateTime.class, new CustomLocalDateTimeSerializer())
//            .registerTypeAdapter(LocalDate.class, new CustomLocalDateSerializer())
//            .registerTypeAdapter(LocalTime.class, new CustomLocalTimeSerializer())
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getAnnotation(Exclude.class) != null;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return GSON.toJson(obj, type);
    }

    public static Map<String, String> toMap(String json) {
        try {
            return GSON.fromJson(json, MAP_TYPE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        return GSON.fromJson(json, clz);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
