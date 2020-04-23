package com.aws.awsmanagerment.service;

public interface IAWSSsmActions {

    /**
     * Method to get a parameter store value
     * @param name
     * @return
     */
    String getParameterStoreValue(String name);
}
