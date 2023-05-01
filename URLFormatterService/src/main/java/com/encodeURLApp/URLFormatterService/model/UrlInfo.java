package com.encodeURLApp.URLFormatterService.model;

import javax.validation.constraints.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document
public class UrlInfo {
    @Id
    private BigInteger urlId;
    @NotNull(message = "URL address is Required")
    @Size(min=10, message="please enter a valid URL")
    @Pattern(regexp = "^(https?|ftp|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", message = "please enter a valid URL")
    private String originalURL;
    @NotNull(message = "short URL cannot be empty")
    @Size(min=2, message="please enter a valid URL")
    private String shortURL;
    @NotNull(message = "Email address is required")
    @Email(message = "Email is not valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String userEmail;

    public UrlInfo() {
    }

    public BigInteger getUrlId() {
        return urlId;
    }

    public void setUrlId(BigInteger urlId) {
        this.urlId = urlId;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
