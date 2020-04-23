package com.aws.awsmanagement.service;

public interface IAWSSsmActions {

    /**
     * Method to get a parameter store value
     * @param name
     * @return
     */
    String getParameterStoreValue(String name);
}
