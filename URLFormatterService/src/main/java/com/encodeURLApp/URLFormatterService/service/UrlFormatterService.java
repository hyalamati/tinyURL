package com.encodeURLApp.URLFormatterService.service;

import com.encodeURLApp.URLFormatterService.exception.URLFormatterException;
import com.encodeURLApp.URLFormatterService.model.UrlInfo;

import java.math.BigInteger;
import java.util.List;

public interface UrlFormatterService {
    List<UrlInfo> getUrlInfoForEmailId(String email);

    UrlInfo createShortUrl(UrlInfo urlInfo) throws URLFormatterException;

    UrlInfo getOriginalUrl(String shortUrl);

    UrlInfo createCustomShortURL(UrlInfo urlInfo) throws URLFormatterException;
}
