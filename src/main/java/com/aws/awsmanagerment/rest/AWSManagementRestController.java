package com.aws.awsmanagerment.rest;

import com.aws.awsmanagerment.service.IAWSInstanceActions;
import com.aws.awsmanagerment.service.IAWSInstanceRetrieval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/aws")
public class AWSManagementRestController {
    /** Class logger **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSManagementRestController.class);

    @Autowired
    private IAWSInstanceActions awsInstanceActions;
    @Autowired
    private IAWSInstanceRetrieval awsInstanceRetrieval;

    @GetMapping("/startInstance")
    public boolean startInstance() {
        return awsInstanceActions.startServer("i-0599f7c480a7f7087");
    }

    @GetMapping("/stopInstance")
    public boolean stopInstance() {
        return awsInstanceActions.stopServer("i-089b56b473277254b");
    }

    @GetMapping("/getAllServers")
    public boolean getAllServers() {
        return awsInstanceRetrieval.getAllServers();
    }


    @GetMapping("/describeInstancesWithTags")
    public boolean describeInstanceWithTags() {
        return awsInstanceRetrieval.getInstancesWithTag("Environment");
    }

    @GetMapping("/describeInstancesWithoutTags")
    public boolean describeInstanceWithoutTags() {
        //List<String> values = Arrays.asList(new String[]{"prod", "production", "Production"});
        List<String> values = Arrays.asList(new String[]{"Prod"});
        return awsInstanceRetrieval.getInstancesWithoutTagAndvalues("Environment", values);
    }
}
