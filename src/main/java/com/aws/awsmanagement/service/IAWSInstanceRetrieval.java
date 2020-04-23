package com.aws.awsmanagement.service;

import software.amazon.awssdk.services.ec2.model.Instance;

import java.util.List;

public interface IAWSInstanceRetrieval {

    /**
     * Method to get all servers
     * @return
     */
    List<Instance> getAllServers();

    /**
     * Method to get an instance with a tag
     * @param tagName
     * @return
     */
    List<Instance> getInstancesWithTag(String tagName);

    /**
     * Method to get an instance with a tag and values
     * @param tagName
     * @param expectedTagValues
     * @return
     */
    List<Instance> getInstancesWithTagAndValues(String tagName, List<String> expectedTagValues);

    /**
     * Method to get an instance without a tag and set of values
     * @param tagName
     * @return
     */
    List<Instance> getInstancesWithoutTag(String tagName);

    /**
     * Method to get an instance without a tag and set of values
     * @param tagName
     * @param expectedTagValues
     * @return
     */
    List<Instance> getInstancesWithoutTagAndValues(String tagName, List<String> expectedTagValues);
}
