package com.aws.awsmanagerment.service.impl;

import com.aws.awsmanagerment.service.IAWSInstanceRetrieval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.List;

@Service
public class AWSInstanceRetrievalImpl implements IAWSInstanceRetrieval {


    /** Class logger **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSInstanceActionsImpl.class);

    @Autowired
    private Ec2Client ec2Client;

    /**
     * Method to get all servers
     * @return
     */
    public boolean getAllServers() {
        String nextToken = null;
        LOGGER.info("Entered here");
        try {

            do {
                DescribeInstancesRequest request = DescribeInstancesRequest.builder().maxResults(5).nextToken(nextToken).build();
                LOGGER.debug("request:{}", request);
                DescribeInstancesResponse response = ec2Client.describeInstances(request);
                LOGGER.debug("response:{}", response);
                for (Reservation reservation : response.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        System.out.printf(
                                "Found reservation with id %s, " +
                                        "AMI %s, " +
                                        "type %s, " +
                                        "state %s " +
                                        "and monitoring state %s",
                                instance.instanceId(),
                                instance.imageId(),
                                instance.instanceType(),
                                instance.state().name(),
                                instance.monitoring().state());

                        System.out.println("");
                    }
                }
                nextToken = response.nextToken();
            } while (nextToken != null);

        } catch (Exception e) {
            LOGGER.error("Exception has occured. Exception: {}", e);
            e.getStackTrace();
        }

        return true;
    }

    /**
     * Method to get an instance
     * @param tagName
     * @return
     */
    public boolean getInstancesWithTag(String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            LOGGER.error("tagName passed in is null or empty");
            return false;
        }

        Filter filter = Filter.builder().name("tag:"+tagName).values("Prod").build();
        LOGGER.debug("filter: {}", filter);
        DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().filters(filter)
                .build();
        DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);


        for (Reservation reservation : describeInstancesResponse.reservations()) {
            Instance instance = reservation.instances().get(0);
            LOGGER.debug("{}", reservation.instances().size());
            LOGGER.debug("{}", instance.instanceId());
        }

        if (describeInstancesResponse == null) {
            LOGGER.error("Failure occured getting instances for the tag:{}", tagName);
            return false;
        }
        return true;
    }

    /**
     * Method to get an instance without a tag and set of values
     * @param tagName
     * @param values
     * @return
     */
    public boolean getInstancesWithoutTagAndvalues(String tagName, List<String> values){
        if (StringUtils.isEmpty(tagName) || CollectionUtils.isEmpty(values)) {
            LOGGER.error("tagName passed in is null or empty or the values is null or empty");
            return false;
        }

        //values.replaceAll( e -> "!" + e);

        Filter filter = Filter.builder().name("tag:"+tagName).values("*").build();
        LOGGER.debug("filter: {}", filter);
        DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().filters(filter)
                .build();
        DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);
        //LOGGER.debug("{}", describeInstancesResponse);
        // LOGGER.debug("{}", describeInstancesResponse.reservations());

        for (Reservation reservation : describeInstancesResponse.reservations()) {
            Instance instance = reservation.instances().get(0);
            LOGGER.debug("{}", instance.instanceId());
        }

        if (describeInstancesResponse == null) {
            LOGGER.error("Failure occured getting instances for the tag:{}", tagName);
            return false;
        }
        return true;
    }
}
