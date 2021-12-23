package com.spartaglobal.musicapiproject.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTypeService {

    private static final List<String> validContentTypesList = List.of("application/json", "application/xml");

    public static String getReturnContentType(String acceptHeaderValue) {
        if (acceptHeaderValue.split(" ").length > 1) {
            String[] possibles = acceptHeaderValue.split(" ");
            for (String possible : possibles) {
                if (validContentTypesList.contains(possible)) {
                    return possible;
                }
            }
        } else if (validContentTypesList.contains(acceptHeaderValue)) {
            return acceptHeaderValue;
        }
        return null;
    }

}

