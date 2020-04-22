package com.aws.awsmanagerment.service;

public interface IAWSInstanceActions {

    /**
     * Method to start a server
     * @param instanceId
     * @return
     */
    boolean startServer(String instanceId);

    /**
     * Method to stop a server
     * @param instanceId
     * @return
     */
    boolean stopServer(String instanceId);
}
