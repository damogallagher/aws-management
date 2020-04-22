package com.aws.awsmanagerment.service.impl;

import com.aws.awsmanagerment.service.IAWSInstanceActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

@Service
public class AWSInstanceActionsImpl implements IAWSInstanceActions {

    /** Class logger **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSInstanceActionsImpl.class);

    @Autowired
    private Ec2Client ec2Client;

    /**
     * Method to start a server
     * @param instanceId
     * @return
     */
    public boolean startServer(String instanceId) {
        if (StringUtils.isEmpty(instanceId)) {
            LOGGER.error("instanceId passed in is null or empty");
            return false;
        }
        StartInstancesRequest startInstancesRequest = StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        StartInstancesResponse startInstancesResponse = ec2Client.startInstances(startInstancesRequest);
        LOGGER.debug("{}", startInstancesResponse);
        if (startInstancesResponse == null) {
            LOGGER.error("Failure occured starting instances");
            return false;
        }
        return true;
    }

    /**
     * Method to stop a server
     * @param instanceId
     * @return
     */
    public boolean stopServer(String instanceId) {
        if (StringUtils.isEmpty(instanceId)) {
            LOGGER.error("instanceId passed in is null or empty");
            return false;
        }

        StopInstancesRequest stopInstancesRequest = StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        StopInstancesResponse stopInstancesResponse = ec2Client.stopInstances(stopInstancesRequest);
        LOGGER.debug("{}", stopInstancesResponse);
        if (stopInstancesResponse == null) {
            LOGGER.error("Failure occured stopping instances");
            return false;
        }
        return true;
    }
}
