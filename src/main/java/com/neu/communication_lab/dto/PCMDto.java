package com.neu.communication_lab.dto;

import lombok.Data;

@Data
public class PCMDto {
    private String folded_binary_code;
    private double Vs;
    private double quantization_error;
}
