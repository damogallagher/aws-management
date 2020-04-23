package com.aws.awsmanagerment.service;

import java.util.List;

public interface IAWSInstanceActions {

    /**
     * Method to start servers
     * @param instanceIds
     * @return
     */
    boolean startServers(List<String> instanceIds);

    /**
     * Method to stop servers
     * @param instanceIds
     * @return
     */
    boolean stopServers(List<String> instanceIds);
}
