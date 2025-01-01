package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CodeUtil {

    private CodeUtil() {

    }

    public static String generateCode(String name) {
        String code = name.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        code = code.replaceAll("\\s+", "-");
        return code;
    }

    public static String generateCodeByTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }
}
