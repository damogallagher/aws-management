package com.aws.awsmanagerment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.ec2.Ec2Client;

@Configuration
public class BeanConfig {

    @Bean
    public Ec2Client getEC2Client() {
        return Ec2Client.create();
    }
}
