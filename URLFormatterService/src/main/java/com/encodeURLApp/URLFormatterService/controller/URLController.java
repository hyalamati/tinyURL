package com.encodeURLApp.URLFormatterService.controller;

import com.encodeURLApp.URLFormatterService.exception.URLFormatterException;
import com.encodeURLApp.URLFormatterService.model.UrlInfo;
import com.encodeURLApp.URLFormatterService.service.UrlFormatterService;
import com.encodeURLApp.URLFormatterService.service.UrlRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class URLController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UrlFormatterService urlFormatterService;
    @Autowired
    private UrlRepositoryService urlRepositoryService;
    @Value("${server.path}")
    private String SERVERURL;

    @RequestMapping(value = "/format", method = RequestMethod.POST)
    public ResponseEntity<?> createShortURL(@RequestBody UrlInfo urlInfo) throws URLFormatterException {
        try{
            LOG.info("Creating short URL..");
            UrlInfo newUrl = urlFormatterService.createShortUrl(urlInfo);
            newUrl.setShortURL(SERVERURL+newUrl.getShortURL());
            return new ResponseEntity<UrlInfo>(newUrl, HttpStatus.OK);
        } catch(ConstraintViolationException exception){
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch(URLFormatterException urlFormatException){
                return new ResponseEntity<>(urlFormatException.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @RequestMapping (value = "/{shorturl}", method = RequestMethod.GET)
    public ResponseEntity<?> redirect(@PathVariable String shorturl, HttpServletResponse response) throws IOException {
        UrlInfo existingUrlInfo = urlFormatterService.getOriginalUrl(shorturl);
        if(existingUrlInfo!=null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(existingUrlInfo.getOriginalURL()))
                    .build();
        }else {
            return new ResponseEntity<>("No Short URL Exists to Redirect", HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping (value = "/createcustomurl", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomURL(@RequestBody UrlInfo urlInfo) throws URLFormatterException {
        try{
            LOG.info("Creating custom URL..");
            UrlInfo newUrl= urlFormatterService.createCustomShortURL(urlInfo);
            newUrl.setShortURL(SERVERURL+newUrl.getShortURL());
            return new ResponseEntity<UrlInfo>(newUrl, HttpStatus.OK);
        } catch(ConstraintViolationException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch(URLFormatterException urlFormatException){
            return new ResponseEntity<>(urlFormatException.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @RequestMapping (value = "/get/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUrlInfobyEmail(@PathVariable String email, HttpServletResponse response) throws IOException {
        List<UrlInfo> existingUrlInfo = urlFormatterService.getUrlInfoForEmailId(email);
        if(existingUrlInfo!=null) {
            LOG.info("Getting all URLs for the user email..");
            return new ResponseEntity<List<UrlInfo>>(existingUrlInfo, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No User Email Exists", HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping (value = "/getbyurlid/{shortUrlId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUrlInfobyUrlId(@PathVariable BigInteger shortUrlId, HttpServletResponse response) throws IOException {
        Optional<UrlInfo> existingUrlInfo = urlRepositoryService.findById(shortUrlId);
        if(existingUrlInfo.isPresent()) {
            LOG.info("Getting all URLs for the user email..");
            return new ResponseEntity<>(existingUrlInfo,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Short URL Exists", HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value ="/findall", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUrls() {
        List<UrlInfo> urlInfoList = urlRepositoryService.findAll();
        if(urlInfoList.size() > 0){
            LOG.info("Getting all URLs..");
            return new ResponseEntity<List<UrlInfo>>(urlInfoList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Urls Data Found", HttpStatus.NOT_FOUND);
        }
    }
}
