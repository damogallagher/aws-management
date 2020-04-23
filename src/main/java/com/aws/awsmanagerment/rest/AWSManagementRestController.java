package com.aws.awsmanagerment.rest;

import com.aws.awsmanagerment.dto.ResponseDTO;
import com.aws.awsmanagerment.service.IAWSInstanceActions;
import com.aws.awsmanagerment.service.IAWSInstanceRetrieval;
import com.aws.awsmanagerment.service.IAWSManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.ec2.model.Instance;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/aws")
public class AWSManagementRestController {
    /** Class logger. **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSManagementRestController.class);

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
            tagValues = Arrays.asList(new String[]{"prod", "production", "Production", "Prod"});
        }

        return awsManagement.performServerActions(tagName, tagValues);
    }
}
