package com.yahia.vmms.dto.configRecord;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "voting-managment")
public record VmContactInfoDto(String message, Map<String,String> contactDetails, List<String> onCallSupport) {
}


