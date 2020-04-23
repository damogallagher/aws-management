package com.aws.awsmanagerment.service.impl;

import com.aws.awsmanagerment.dto.ResponseDTO;
import com.aws.awsmanagerment.service.IAWSInstanceActions;
import com.aws.awsmanagerment.service.IAWSInstanceRetrieval;
import com.aws.awsmanagerment.service.IAWSManagement;
import com.aws.awsmanagerment.service.IAWSSsmActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class AWSManagementImpl implements IAWSManagement {

    /** Class logger. **/
    private final Logger LOGGER = LoggerFactory.getLogger(AWSInstanceActionsImpl.class);

    @Autowired
    private IAWSInstanceActions awsInstanceActions;

    @Autowired
    private IAWSInstanceRetrieval awsInstanceRetrieval;

    @Autowired
    private IAWSSsmActions awsSsmActions;

    /**
     * Method to perform server actions based on a tagName and tag values
     * @param tagName
     * @param tagValues
     * @return
     */
    public ResponseDTO performServerActions(String tagName, List<String> tagValues) {

        List<Instance> instanceList = awsInstanceRetrieval.getInstancesWithoutTagAndValues(tagName, tagValues);

        List<String> instancesToStart = new LinkedList<>();
        List<String> instancesToStop = new LinkedList<>();
        List<String> instancesInOtherState = new LinkedList<>();

        for (Instance instance : instanceList) {
            LOGGER.debug("instance:{}", instance.state());
            String instanceId = instance.instanceId();

            switch ( InstanceStateName.fromValue(instance.state().name().toString())) {
                case RUNNING:
                    instancesToStop.add(instanceId);
                    break;
                case STOPPED:
                    instancesToStart.add(instanceId);
                    break;
                default:
                    instancesInOtherState.add(instanceId);
            }
        }

        boolean instancesStarted = awsInstanceActions.startServers(instancesToStart);
        if (!instancesStarted) {
            LOGGER.error("Failed to start some instances");
        }
        boolean instancesStopped = awsInstanceActions.stopServers(instancesToStop);
        if (!instancesStopped) {
            LOGGER.error("Failed to stop some instances");
        }

        ResponseDTO responseDTO = ResponseDTO.builder().instancesStarting(instancesToStart)
                .instancesStopping(instancesToStop)
                .instancesInOtherStates(instancesInOtherState)
                .totalInstancesStarting(instancesToStart.size())
                .totalInstancesStopping(instancesToStop.size())
                .totalInstancesInOtherStates(instancesInOtherState.size()).build();


        return responseDTO;
    }


    /**
     * Method to perform server actions based on a default tagName and tagValues
     * @return
     */
    public ResponseDTO performServerActions() {
        String ssmTagName = "INSTANCES_TAG_NAME";
        String ssmTagValue = "INSTANCES_TAG_VALUE";

        String tagName = awsSsmActions.getParameterStoreValue(ssmTagName);

        //Tag value comes in a pipe delimited format
        String tagValue = awsSsmActions.getParameterStoreValue(ssmTagValue);
        List<String> tagValueList = Arrays.asList(tagValue.split("\\|"));

        return performServerActions(tagName, tagValueList);
    }
}
