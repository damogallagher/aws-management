package com.aws.awsmanagement.rest;

import com.aws.awsmanagement.service.IAWSInstanceRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.ec2.model.Instance;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/aws/instanceRetrieval")
public class AWSInstanceRetrievalRestController {

    @Autowired
    private IAWSInstanceRetrieval awsInstanceRetrieval;

    @GetMapping("/getAllServers")
    public List<Instance> getAllServers() {
        return awsInstanceRetrieval.getAllServers();
    }


    @GetMapping("/describeInstancesWithTag")
    public List<Instance> describeInstanceWithTag(@RequestParam(value = "tagName", defaultValue = "Environment") String tagName) {
        return awsInstanceRetrieval.getInstancesWithTag(tagName);
    }

    @PostMapping("/describeInstancesWithTagAndValues")
    public List<Instance> describeInstanceWithTagAndValues(@RequestBody List<String> tagValues,
                                                              @RequestParam(value = "tagName", defaultValue = "Environment") String tagName) {
        if (CollectionUtils.isEmpty(tagValues)) {

            tagValues = Arrays.asList("prod", "production", "Production", "Prod");
        }

        return awsInstanceRetrieval.getInstancesWithTagAndValues(tagName, tagValues);
    }

    @GetMapping("/describeInstancesWithoutTag")
    public List<Instance> describeInstanceWithoutTag(@RequestParam(value = "tagName", defaultValue = "Environment") String tagName) {
        return awsInstanceRetrieval.getInstancesWithoutTag(tagName);
    }

    @PostMapping("/describeInstancesWithoutTagAndValues")
    public List<Instance> describeInstanceWithoutTagAndValues(@RequestBody List<String> tagValues,
                                                               @RequestParam(value = "tagName", defaultValue = "Environment") String tagName) {
        if (CollectionUtils.isEmpty(tagValues)) {
            tagValues = Arrays.asList("prod", "production", "Production", "Prod");
        }

        return awsInstanceRetrieval.getInstancesWithoutTagAndValues(tagName, tagValues);
    }
}
