package com.aws.awsmanagerment.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ssm.SsmClient;

@Configuration
public class BeanConfig {

    private static final Region DEFAULT_REGION = Region.US_EAST_1;
    @Bean
    public Ec2Client getEC2Client() {
        return Ec2Client.builder().region(DEFAULT_REGION).build();
    }

    @Bean
    public SsmClient getSsmClient() {
        return SsmClient.builder().region(DEFAULT_REGION).build();
    }


    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return objectMapper;
    }
}
