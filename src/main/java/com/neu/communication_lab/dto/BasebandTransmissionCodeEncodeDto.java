package com.neu.communication_lab.dto;

import lombok.Data;

@Data
public class BasebandTransmissionCodeEncodeDto {
    private int[] AMI_result;
    private int[] HDB3_result;
    private int[] Manchester_result;
    private int[] DifferentialBiPhase_result;
    private int[] MillerCode_result;
    private int[] CMI_result;
}
