package com.encodeURLApp.URLFormatterService.service;

import com.encodeURLApp.URLFormatterService.exception.URLFormatterException;
import com.encodeURLApp.URLFormatterService.model.UrlInfo;
import com.encodeURLApp.URLFormatterService.utility.UrlEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


@Component
public class UrlFormatterServiceImpl implements UrlFormatterService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public UrlFormatterServiceImpl() {
    }

    @Override
    public List<UrlInfo> getUrlInfoForEmailId(String userEmail) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userEmail").is(userEmail));
        List<UrlInfo> allUrls = mongoTemplate.find(query, UrlInfo.class);
        return allUrls;
    }
    @Override
    public UrlInfo createShortUrl(UrlInfo urlInfo) throws URLFormatterException {
           if(Optional.ofNullable(urlInfo.getOriginalURL()).isPresent()) {
               String shortURL = UrlEncoder.shortenURL(urlInfo.getOriginalURL());
               UrlInfo newUrlInfo = formulateUrlInfo(urlInfo, shortURL);
               LOG.info("saving auto generated short url..");
               return mongoTemplate.save(newUrlInfo);
           }else {
            throw new URLFormatterException(URLFormatterException.urlDataNotFoundException());
        }
    }
    @Override
    public UrlInfo getOriginalUrl(String shortURL) {
        Query query = new Query();
        query.addCriteria(Criteria.where("shortURL").is(shortURL));
        return mongoTemplate.findOne(query, UrlInfo.class);
    }
    @Override
    public UrlInfo createCustomShortURL(UrlInfo urlInfo) throws ConstraintViolationException, URLFormatterException {
        if(urlInfo.getShortURL()!=null) {
            String shortURL = UrlEncoder.shortenURL(urlInfo.getOriginalURL(), urlInfo.getShortURL());
            if(getOriginalUrl(shortURL)!=null) {
                throw new URLFormatterException(URLFormatterException.urlAlreadyExistsException());
            }else {
                LOG.info("saving custom short url..");
                UrlInfo newUrlInfo = formulateUrlInfo(urlInfo, shortURL);
                return mongoTemplate.save(newUrlInfo);
            }
        }else {
            throw new URLFormatterException(URLFormatterException.invalidURLFormatException());
        }
    }
    private UrlInfo formulateUrlInfo(UrlInfo urlInfo, String shortURL){
        UrlInfo newUrlInfo = new UrlInfo();
        newUrlInfo.setOriginalURL(urlInfo.getOriginalURL());
        newUrlInfo.setUserEmail(urlInfo.getUserEmail());
        newUrlInfo.setShortURL(shortURL);
        return newUrlInfo;
    }
}
