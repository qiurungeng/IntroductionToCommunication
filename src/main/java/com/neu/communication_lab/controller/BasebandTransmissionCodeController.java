package com.neu.communication_lab.controller;

import com.neu.communication_lab.algorithm.BasebandTransmissionCodeEncode;
import com.neu.communication_lab.algorithm.PCM;
import com.neu.communication_lab.dto.BasebandTransmissionCodeEncodeDto;
import com.neu.communication_lab.dto.PCMDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
public class BasebandTransmissionCodeController {
    @GetMapping("/BasebandTransmissionCode/{code}")
    public BasebandTransmissionCodeEncodeDto getEncodeResult(@PathVariable(name = "code")String code){
        BasebandTransmissionCodeEncodeDto result=new BasebandTransmissionCodeEncodeDto();
        result.setAMI_result(BasebandTransmissionCodeEncode.toAMI(code));
        result.setHDB3_result(BasebandTransmissionCodeEncode.toHDB3(code));
        result.setManchester_result(BasebandTransmissionCodeEncode.toManchester(code));
        result.setDifferentialBiPhase_result(BasebandTransmissionCodeEncode.toDifferentialBiPhase(code));
        result.setMillerCode_result(BasebandTransmissionCodeEncode.toMillerCode(code));
        result.setCMI_result(BasebandTransmissionCodeEncode.toCMI(code));
        return result;
    }

    @GetMapping("/PCM/encode/{Vs}")
    public PCMDto getPCMEncodeResult(@PathVariable(name = "Vs")double Vs){
        System.out.println(Vs);
        PCMDto pcmDto=new PCMDto();
        pcmDto.setVs(Vs);
        pcmDto.setFolded_binary_code(PCM.encode(Vs));
        pcmDto.setQuantization_error(PCM.quantization_error(pcmDto.getFolded_binary_code(),Vs));
        System.out.println(pcmDto);
        return pcmDto;
    }

    @GetMapping("/PCM/decode/{code}")
    public PCMDto getPCMDecodeResult(@PathVariable(name = "code")String code){
        PCMDto pcmDto=new PCMDto();
        pcmDto.setVs(PCM.decode(code));
        pcmDto.setFolded_binary_code(code);
        pcmDto.setQuantization_error(PCM.quantization_error(code,pcmDto.getVs()));
        System.out.println(pcmDto);
        return pcmDto;
    }
}
