package com.aws.awsmanagement.rest;

import com.aws.awsmanagement.service.IAWSInstanceActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/aws/instanceActions")
public class AWSInstanceActionsRestController {

    @Autowired
    private IAWSInstanceActions awsInstanceActions;

    @GetMapping("/startInstance")
    public boolean startInstance(@RequestParam(value = "instanceId", defaultValue = "i-0599f7c480a7f7087") String instanceId) {
        return awsInstanceActions.startServers(Arrays.asList(instanceId));
    }

    @GetMapping("/stopInstance")
    public boolean stopInstance(@RequestParam(value = "instanceId", defaultValue = "i-089b56b473277254b") String instanceId) {
        return awsInstanceActions.stopServers(Arrays.asList(instanceId));
    }
}
