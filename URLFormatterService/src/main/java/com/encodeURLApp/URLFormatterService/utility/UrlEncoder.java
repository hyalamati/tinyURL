package com.encodeURLApp.URLFormatterService.utility;

import com.encodeURLApp.URLFormatterService.exception.URLFormatterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.util.UUID;

public class UrlEncoder {
    private static BigInteger generateUUID() {
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString().replaceAll("-","");
        BigInteger userId = new BigInteger(str, 16);
        return userId;
    }
    public static String shortenURL(String originalURL) {
        String uniqueID = EncodeURL.encode(generateUUID());
        int urlLength= uniqueID.length() - originalURL.length();
        String shortURL = (urlLength >= 0)? uniqueID.substring(0, urlLength - 1): uniqueID;
        return shortURL;
    }
    public static String shortenURL(String originalURL, String customURL) throws URLFormatterException {
            String regexp = "^(https?|ftp|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*";
            String customShortURL = customURL.matches(regexp) ? customURL.replaceFirst("(https?|ftp|http)://", "") : customURL;
            return customShortURL;
    }
}
