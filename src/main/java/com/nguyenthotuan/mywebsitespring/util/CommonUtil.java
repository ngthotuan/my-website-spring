package com.nguyenthotuan.mywebsitespring.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tuannt7 on 20/10/2022
 */
@Component
@Slf4j
public class CommonUtil {
    private static ObjectMapper objectMapper;

    @Autowired
    public CommonUtil(ObjectMapper objectMapper) {
        CommonUtil.objectMapper = objectMapper;
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(number.toString(16));

            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("md5 data error: {}", e.getMessage(), e);
            return null;
        }
    }

    public static String sha(String algorithm, String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("sha data for sha {} error: {}", algorithm, e.getMessage(), e);
            return null;
        }
    }

    public static String objectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("write object as string fail");
            return null;
        }
    }

    public static <T> T jsonStringToObject(String json, Class<T> className) {
        try {
            return objectMapper.readValue(json, className);
        } catch (Exception e) {
            log.error("parse json: {}, exception: ", json, e);
            return null;
        }
    }

    public static <T> T jsonStringToObject(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            log.error("parse json: {}, exception: ", json, e);
            return null;
        }
    }

    public static String getClientIP(HttpServletRequest req) {
        String clientIP = "";
        if (req.getHeader("HTTP_X_FORWARDED_FOR") != null) {
            clientIP = req.getHeader("HTTP_X_FORWARDED_FOR");
        } else if (req.getHeader("X-Forwarded-For") != null) {
            clientIP = req.getHeader("X-Forwarded-For");
        } else if (req.getHeader("REMOTE_ADDR") != null) {
            clientIP = req.getHeader("REMOTE_ADDR");
        }

        if ("".equals(clientIP)) {
            clientIP = req.getRemoteAddr();
        }

        if (clientIP != null) {
            String[] ips = clientIP.split(",");
            if (ips.length > 0) {
                clientIP = ips[0];
            }
        }
        return clientIP;
    }

    public String buildURLQueryParams(String url, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(params)
                .toUriString();
    }
}
