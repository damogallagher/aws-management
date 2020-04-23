package com.aws.awsmanagerment.service;

import com.aws.awsmanagerment.dto.ResponseDTO;

import java.util.List;

public interface IAWSManagement {

    /**
     * Method to perform server actions based on a tagName and tag values
     * @param tagName
     * @param tagValues
     * @return
     */
    ResponseDTO performServerActions(String tagName, List<String> tagValues);


    /**
     * Method to perform server actions based on a default tagName and tagValues
     * @return
     */
    ResponseDTO performServerActions();
}
