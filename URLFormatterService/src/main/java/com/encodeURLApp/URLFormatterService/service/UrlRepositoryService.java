package com.encodeURLApp.URLFormatterService.service;

import com.encodeURLApp.URLFormatterService.model.UrlInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UrlRepositoryService extends MongoRepository<UrlInfo, BigInteger> {
}
