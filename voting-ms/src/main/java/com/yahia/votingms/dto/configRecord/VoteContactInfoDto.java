package com.yahia.votingms.dto.configRecord;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "voting-ms")
public record VoteContactInfoDto(String message, Map<String,String> contactDetails, List<String> onCallSupport) {
}
