package com.neu.communication_lab.algorithm;

public class PCM {
    /**
     * 将抽样信号电平幅度编码为折叠二进制码
     * @param Vs
     * @return
     */
    public static String encode(double Vs){
        String encodeResult=Vs>0?"1":"0";

        //抽样信号的电平绝对值
        double value=Math.abs(Vs)*2048;

        //计算该绝对值落在第几大段(0~7)
        double temp=value/16;
        int paragraph=0;
        int paragraph_pow2=1;  //2^paragraph
        while (temp>paragraph_pow2){
            paragraph_pow2*=2;
        }
        paragraph=(int)(Math.log(paragraph_pow2)/Math.log(2));

        //用抽样电平信号减去该大段的下限值
        double remain=value-( paragraph>0 ? Math.pow(2,paragraph-1)*16 : 0 );

        //计算落在该大段的第几小段
        int step_length;    //小段的长度
        if (paragraph<=1){
            step_length=1;
        }else {
            step_length=(int)Math.pow(2,paragraph-1);
        }
        int step=(int)Math.floor(remain/step_length);

        //拼接并返回编码
        encodeResult+=Integer.toBinaryString(paragraph)+""+Integer.toBinaryString(step);
        return encodeResult;
    }


    /**
     * 将折叠二进制码解码
     * @param binary_code
     * @return
     */
    public static double decode(String binary_code){
        int paragraph=Integer.valueOf(binary_code.substring(1,4),2);
        int step=Integer.valueOf(binary_code.substring(4,binary_code.length()),2);
        //获得大段下限值和所在小段
        int[] floorAndStepLengthOfParagraph = getFloorAndStepLengthOfParagraph(paragraph);
        //解码结果即为该大段下限值加上所在小段的中点值
//        double decode_result=(floorAndStepLengthOfParagraph[0]+(step+0.5)*floorAndStepLengthOfParagraph[1])/2048;
        double decode_result=((double)(floorAndStepLengthOfParagraph[0]+step*floorAndStepLengthOfParagraph[1]))/2048;
        return decode_result;
    }

    /**
     * 量化误差
     * @param binary_code
     * @param Vs
     * @return
     */
    public static double quantization_error(String binary_code,double Vs){
        int paragraph=Integer.valueOf(binary_code.substring(1,4),2);
        int step=Integer.valueOf(binary_code.substring(4,binary_code.length()),2);
        //获得大段下限值和所在小段
        int[] floorAndStepLengthOfParagraph = getFloorAndStepLengthOfParagraph(paragraph);
        //解码结果即为该大段下限值加上所在小段的中点值
        double decode_result=(floorAndStepLengthOfParagraph[0]+(step+0.5)*floorAndStepLengthOfParagraph[1])/2048;
        return Math.abs(Vs-decode_result);
    }

    //计算第N大段的下限值和量化阶步
    private static int[] getFloorAndStepLengthOfParagraph(int n){
        int[] result;
        if(n<1){
            result=new int[]{0,1};
        }else {
            result=new int[]{(int)(16*Math.pow(2,n-1)),(int)(Math.pow(2,n-1))};
        }
        return result;
    }
}
