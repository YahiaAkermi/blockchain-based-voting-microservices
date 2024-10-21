package com.yahia.vmms;

import com.yahia.vmms.dto.configRecord.VmContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableConfigurationProperties(VmContactInfoDto.class)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
public class VmMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmMsApplication.class, args);
    }

}
