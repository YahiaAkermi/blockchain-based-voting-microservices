package com.yahia.votingms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "auditAwareImpl")
public class VotingMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingMsApplication.class, args);
	}

}
