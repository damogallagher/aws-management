package com.aws.awsmanagement.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private int totalInstancesStarting;
    private int totalInstancesStopping;
    private int totalInstancesInOtherStates;

    private List<String> instancesStarting;
    private List<String> instancesStopping;
    private List<String> instancesInOtherStates;
}
