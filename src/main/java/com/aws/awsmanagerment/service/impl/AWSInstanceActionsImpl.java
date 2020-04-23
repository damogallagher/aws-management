package com.aws.awsmanagerment.service.impl;

import com.aws.awsmanagerment.service.IAWSInstanceActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.List;

@Service
public class AWSInstanceActionsImpl implements IAWSInstanceActions {

    /** Class logger. **/
    private final Logger logger = LoggerFactory.getLogger(AWSInstanceActionsImpl.class);

    @Autowired
    private Ec2Client ec2Client;

    /**
     * Method to start servers
     * @param instanceIds
     * @return
     */
    public boolean startServers(List<String> instanceIds) {
        if (CollectionUtils.isEmpty(instanceIds)) {
            logger.error("instanceIds passed in are null or empty");
            return false;
        }
        StartInstancesRequest startInstancesRequest = StartInstancesRequest.builder()
                .instanceIds(instanceIds)
                .build();

        StartInstancesResponse startInstancesResponse = ec2Client.startInstances(startInstancesRequest);
        logger.debug("{}", startInstancesResponse);
        if (startInstancesResponse == null) {
            logger.error("Failure occured starting instances");
            return false;
        }
        return true;
    }


    /**
     * Method to stop servers
     * @param instanceIds
     * @return
     */
    public boolean stopServers(List<String> instanceIds) {
        if (CollectionUtils.isEmpty(instanceIds)) {
            logger.error("instanceIds passed in are null or empty");
            return false;
        }

        StopInstancesRequest stopInstancesRequest = StopInstancesRequest.builder()
                .instanceIds(instanceIds)
                .build();

        StopInstancesResponse stopInstancesResponse = ec2Client.stopInstances(stopInstancesRequest);
        logger.debug("{}", stopInstancesResponse);
        if (stopInstancesResponse == null) {
            logger.error("Failure occured stopping instances");
            return false;
        }
        return true;
    }
}
