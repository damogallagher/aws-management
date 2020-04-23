package com.aws.awsmanagerment.rest;

import com.aws.awsmanagerment.service.IAWSInstanceActions;
import com.aws.awsmanagerment.service.IAWSInstanceRetrieval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.ec2.model.Instance;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/aws/instanceActions")
public class AWSInstanceActionsRestController {
    /** Class logger. **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSInstanceActionsRestController.class);

    @Autowired
    private IAWSInstanceActions awsInstanceActions;

    @GetMapping("/startInstance")
    public boolean startInstance(@RequestParam(value = "instanceId", defaultValue = "i-0599f7c480a7f7087") String instanceId) {
        return awsInstanceActions.startServers(Arrays.asList(new String[]{instanceId}));
    }

    @GetMapping("/stopInstance")
    public boolean stopInstance(@RequestParam(value = "instanceId", defaultValue = "i-089b56b473277254b") String instanceId) {
        return awsInstanceActions.stopServers(Arrays.asList(new String[]{instanceId}));
    }
}
