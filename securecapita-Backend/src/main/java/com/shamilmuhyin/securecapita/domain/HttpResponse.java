package com.shamilmuhyin.securecapita.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?,?> data;
}
