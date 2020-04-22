package com.aws.awsmanagerment.rest;

import com.aws.awsmanagerment.service.IAWSInstanceActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aws")
public class AWSManagementRestController {
    /** Class logger **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSManagementRestController.class);

    @Autowired
    private IAWSInstanceActions awsServerStatusActions;

    @GetMapping("/startInstance")
    public boolean startInstance() {
        return awsServerStatusActions.startServer("i-0599f7c480a7f7087");
    }

    @GetMapping("/stopInstance")
    public boolean stopInstance() {
        return awsServerStatusActions.stopServer("i-089b56b473277254b");
    }

    @GetMapping("/describeInstance")
    public boolean describeInstance() {
        return awsServerStatusActions.stopServer("i-089b56b473277254b");
    }
}
