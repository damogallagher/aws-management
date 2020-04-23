package com.aws.awsmanagerment.service;

import software.amazon.awssdk.services.ec2.model.Instance;

import java.util.List;

public interface IAWSSsmActions {

    /**
     * Method to get a parameter store value
     * @param name
     * @return
     */
    String getParameterStoreValue(String name);
}
