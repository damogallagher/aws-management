package com.aws.awsmanagement.service.impl;

import com.aws.awsmanagement.service.IAWSSsmActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@Service
public class AWSSsmActionsImpl implements IAWSSsmActions {

    /** Class logger. **/
    private final Logger logger = LoggerFactory.getLogger(AWSSsmActionsImpl.class);

    @Autowired
    private SsmClient ssmClient;

    /**
     * Method to get a parameter store value
     * @param name
     * @return
     */
    public String getParameterStoreValue(String name) {
        String parameterValue = null;
        if (StringUtils.isEmpty(name)) {
            logger.error("Name passed in is null or empty");
            return parameterValue;
        }
        GetParameterRequest getParameterRequest = GetParameterRequest.builder().name(name).build();
        GetParameterResponse getParameterResponse = ssmClient.getParameter(getParameterRequest);

        if (getParameterResponse == null || getParameterResponse.parameter() == null) {
            logger.error("Failed to get value from the SSM Parameter store for the name:{}", name);
            return parameterValue;
        }
        parameterValue = getParameterResponse.parameter().value();

        return parameterValue;
    }
}
