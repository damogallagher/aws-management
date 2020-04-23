package com.aws.awsmanagerment.rest;

import com.aws.awsmanagerment.dto.ResponseDTO;
import com.aws.awsmanagerment.service.IAWSManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/aws")
public class AWSManagementRestController {

    @Autowired
    private IAWSManagement awsManagement;

    @GetMapping("/serverActionsDefaults")
    public ResponseDTO performDefaultServerActions() {
        return awsManagement.performServerActions();
    }

    @PostMapping("/serverActions")
    public ResponseDTO describeInstanceWithTagAndValues(@RequestBody List<String> tagValues,
                                                        @RequestParam(value = "tagName", defaultValue = "Environment") String tagName) {
        if (CollectionUtils.isEmpty(tagValues)) {
            tagValues = Arrays.asList("prod", "production", "Production", "Prod");
        }

        return awsManagement.performServerActions(tagName, tagValues);
    }

    @GetMapping("/stopAllServers")
    public ResponseDTO stopAllServers() {
        return awsManagement.stopAllServers();
    }

    @GetMapping("/startAllServers")
    public ResponseDTO startAllServers() {
        return awsManagement.startAllServers();
    }
}
