package com.yahia.votingms;

import com.yahia.votingms.dto.configRecord.VoteContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = VoteContactInfoDto.class)
public class VotingMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingMsApplication.class, args);
	}

}
