package com.aws.awsmanagerment.service;

import java.util.List;

public interface IAWSInstanceRetrieval {

    /**
     * Method to get all servers
     * @return
     */
    boolean getAllServers();

    /**
     * Method to get an instance with a tag
     * @param tagName
     * @return
     */
    boolean getInstancesWithTag(String tagName);

    /**
     * Method to get an instance without a tag and set of values
     * @param tagName
     * @param values
     * @return
     */
    boolean getInstancesWithoutTagAndvalues(String tagName, List<String> values);
}
