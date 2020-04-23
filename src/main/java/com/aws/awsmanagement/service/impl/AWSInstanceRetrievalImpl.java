package com.aws.awsmanagement.service.impl;

import com.aws.awsmanagement.service.IAWSInstanceActions;
import com.aws.awsmanagement.service.IAWSInstanceRetrieval;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;


@Service
public class AWSInstanceRetrievalImpl implements IAWSInstanceRetrieval {

    /** Class logger. **/
    private final Logger logger = LoggerFactory.getLogger(AWSInstanceRetrievalImpl.class);

    @Autowired
    private IAWSInstanceActions awsInstanceActions;

    @Autowired
    private Ec2Client ec2Client;

    /**
     * Method to get all servers
     * @return
     */
    public List<Instance> getAllServers() {
        List<Instance> instanceList = new LinkedList<>();

        String nextToken = null;
        try {
            do {
                DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().maxResults(5).nextToken(nextToken).build();
                DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);

                for (Reservation reservation : describeInstancesResponse.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        instanceList.add(instance);
                    }
                }
                nextToken = describeInstancesResponse.nextToken();
            } while (nextToken != null);

        } catch (Exception e) {
            logger.error("GetAllServers - Exception has occured. Exception: {}", e);
        }

        logger.info("getAllServers Instances count: {}", instanceList.size());
        return instanceList;
    }

    /**
     * Method to get an instance
     * @param tagName
     * @return
     */
    public List<Instance> getInstancesWithTag(String tagName) {
        List<Instance> instanceList = new LinkedList<>();
        if (StringUtils.isEmpty(tagName)) {
            logger.error("tagName passed in is null or empty");
            return instanceList;
        }

        String nextToken = null;
        try {
            do {
                Filter filter = Filter.builder().name("tag-key").values(tagName).build();
                logger.debug("filter: {}", filter);
                DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().filters(filter).build();
                DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);

                if (describeInstancesResponse == null) {
                    logger.error("An error has occured getting instances for the tag:{}", tagName);
                    return instanceList;
                }

                for (Reservation reservation : describeInstancesResponse.reservations()) {
                    instanceList.addAll(reservation.instances());
                }
                nextToken = describeInstancesResponse.nextToken();
            } while (nextToken != null);

        } catch (Exception e) {
            logger.error("An Exception has occured. Exception: {}", e);
        }
        logger.info("Total Instances for getInstancesWithTag: {}", instanceList.size());
        return instanceList;
    }
    /**
     * Method to get an instance with a tag and values
     * @param tagName
     * @param expectedTagValues
     * @return
     */
    public List<Instance> getInstancesWithTagAndValues(String tagName, List<String> expectedTagValues){
        List<Instance> instanceList = new LinkedList<>();
        if (StringUtils.isEmpty(tagName) || CollectionUtils.isEmpty(expectedTagValues)) {
            logger.error("tagName passed in is null or empty or expectedTagValues is null or empty");
            return instanceList;
        }

        String nextToken = null;
        try {
            do {
                Filter filter = Filter.builder().name("tag:"+tagName).values(expectedTagValues).build();
                logger.debug("filter: {}", filter);
                DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().filters(filter)
                        .build();
                DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);

                if (describeInstancesResponse == null) {
                    logger.error("An error has occured getting instances for the tag:{}", tagName);
                    return instanceList;
                }

                for (Reservation reservation : describeInstancesResponse.reservations()) {
                    instanceList.addAll(reservation.instances());
                }
                nextToken = describeInstancesResponse.nextToken();
            } while (nextToken != null);

        } catch (Exception e) {
            logger.error("An Exception has occured. Exception: {}", e);
        }

        logger.info("getInstancesWithTagAndValues Instances count: {}", instanceList.size());
        return instanceList;
    }


    /**
     * Method to get an instance without a tag and set of values
     * @param tagName
     * @return
     */
    public List<Instance> getInstancesWithoutTag(String tagName){
        List<Instance> instanceList = null;
        if (StringUtils.isEmpty(tagName)) {
            logger.error("tagName passed in is null or empty");
            return instanceList;
        }

        String nextToken = null;
        try {
            do {
                DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().build();
                DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);

                if (describeInstancesResponse == null) {
                    logger.error("Failure occured getting instances for the tag:{}", tagName);
                    return instanceList;
                }

                instanceList = new LinkedList<>();
                for (Reservation reservation : describeInstancesResponse.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        boolean instanceHasRequiredTags = false;
                        for (Tag tag : instance.tags()) {
                            if (tag.key().equals(tagName)) {
                                instanceHasRequiredTags = true;
                                break;
                            }
                        }
                        if (!instanceHasRequiredTags) {
                            instanceList.add(instance);
                        }
                    }
                }
                nextToken = describeInstancesResponse.nextToken();
            } while (nextToken != null);

        } catch (Exception e) {
            logger.error("Exception has occured. Exception: {}", e);
        }

        if (!CollectionUtils.isEmpty(instanceList)) {
            logger.info("Total Instances: {}", instanceList.size());
        }

        return instanceList;
    }

    /**
     * Method to get an instance without a tag and set of values
     * @param tagName
     * @param expectedTagValues
     * @return
     */
    public List<Instance> getInstancesWithoutTagAndValues(String tagName, List<String> expectedTagValues) {
        List<Instance> instanceList = null;
        if (StringUtils.isEmpty(tagName) || CollectionUtils.isEmpty(expectedTagValues)) {
            logger.error("tagName passed in is null or empty or the expectedTagValues is null or empty");
            return instanceList;
        }

        String nextToken = null;
        try {
            do {
                DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().build();
                DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);

                if (describeInstancesResponse == null) {
                    logger.error("Failure occured getting instances for the tag:{}", tagName);
                    return instanceList;
                }

                instanceList = new LinkedList<>();
                for (Reservation reservation : describeInstancesResponse.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        boolean instanceHasRequiredTags = false;
                        for (Tag tag : instance.tags()) {
                            if (tag.key().equals(tagName) && containsTagValue(tag.value(), expectedTagValues)) {
                                instanceHasRequiredTags = true;
                                break;
                            }
                        }
                        if (!instanceHasRequiredTags) {
                            instanceList.add(instance);
                        }
                    }
                }
                nextToken = describeInstancesResponse.nextToken();
            } while (nextToken != null);

        } catch (Exception e) {
            logger.error("Exception has occured. Exception: {}", e);
        }

        if (!CollectionUtils.isEmpty(instanceList)) {
            logger.info("Total Instances: {}", instanceList.size());
        }
        return instanceList;
    }

    /**
     * Method to check if a list contains a tag value or not
     * @param value
     * @param expectedTagValues
     * @return
     */
    private boolean containsTagValue(String value, List<String> expectedTagValues) {
        return expectedTagValues.contains(value);
    }
}
