package com.neu.communication_lab.algorithm;

public class BasebandTransmissionCodeEncode {
    private static final char one='1',zero='0';

    /**
     * 数字基带信号转AMI码
     * @param code
     * @return
     */
    public static int[] toAMI(String code){
        boolean flag=true;
        int[] result=new int[code.length()];
        for (int i=0;i<code.length();i++){
            if (code.charAt(i)==zero){
                result[i]=0;
            }else if(code.charAt(i)==one){
                result[i]=flag?1:-1;
                flag=flag?false:true;
            }
        }
        return result;
    }

    /**
     * 数字基带信号转HDB3码
     * @param code
     * @return
     */
    public static int[] toHDB3(String code){
        boolean flag=true;
        int count=-1;    //前一个V码后的传号数目
        int[] result=new int[code.length()];
        for (int i=0;i<code.length();i++){

            if (code.charAt(i)==zero&&i<code.length()-3){
                //该位为0,且不是最后的三位，对其接下来的3为进行判断
                if (isContinuousZero(code.substring(i+1,i+4))){
                    //i位为0，i+1，i+2、i+3为皆为0，即四位连0，进行抑制
                    if (count%2==0&&count>=0){
                        //经过偶数位传号,将连0改为B00V
                        result[i+3]=result[i]=flag?1:-1;
                        result[i+1]=result[i+2]=0;
                        flag=flag?false:true;
                    }else {
                        //经过奇数位传号,将连0改为000V
                        result[i]=result[i+2]=result[i+3]=0;
                        result[i+3]=flag?-1:1;
                        count=0;
                    }
                    i+=3;//后三位处理完毕，将索引后移3位
                }else {
                    //非四位连0
                    result[i]=0;
                }
            }else if(code.charAt(i)==zero){
                //该位为0，且为最后的三位，不进行连0判断
                result[i]=0;
            }else {
                //该位为1,进行交替翻转
                result[i]=flag?1:-1;
                flag=flag?false:true;
                //传号数目累加
                if (count>=0)count+=1;
            }
        }
        return result;
    }

    /**
     * 判断字符串是否为连0
     * @param code_slice
     * @return
     */
    private static boolean isContinuousZero(String code_slice){
        for(char c:code_slice.toCharArray()){
            if (c!=zero)return false;
        }
        return true;
    }

    /**
     * 数字基带信号转Manchester码
     * @param code
     * @return
     */
    public static int[] toManchester(String code){
        int[] result=new int[code.length()*2];
        for (int i=0;i<result.length;i++){
            if (code.charAt((int)i/2)==zero){
                result[i]=0;
                i++;
                result[i]=1;
            }else if(code.charAt((int)i/2)==one){
                result[i]=1;
                i++;
                result[i]=0;
            }
        }
        return result;
    }

    /**
     * 数字基带信号转差分双相码
     * @param code
     * @return
     */
    public static int[] toDifferentialBiPhase(String code){
        int[] result=new int[code.length()*2];
        for (int i=0;i<result.length;i++){
            if (code.charAt((int)i/2)==zero){
                result[i]=-1;
                i++;
                result[i]=1;
            }else if(code.charAt((int)i/2)==one){
                result[i]=1;
                i++;
                result[i]=-1;
            }
        }
        return result;
    }

    /**
     * 数字基带信号转密勒码
     * 1”码用码元中心点出现跃变来表示，即用“10”或“01”
     * “0”码有两种情况：
     * 		单个“0”时，在码元持续时间内不出现电平跃变，且与相邻码元的边界处也不跃变，
     * 		连“0”时，在两个“0”码的边界处出现电平跃变，即"00”与“11”交替。
     * @param code
     * @return
     */
    public static int[] toMillerCode(String code){
        int[] result=new int[code.length()*2];
        int last_bit=1;
        for (int i=0;i<result.length;i++){
            if (code.charAt((int)i/2)==zero){
                //当前位为0
                if(i<result.length-2){
                    //code非最后一位,需要进行连0判断
                    if (isContinuousZero(code.substring(i/2,(i/2)+2))){
                        //若有两个连0,即本位与下一位都为0，连0的边界出现跃变
                        result[i]=result[i+1]=last_bit;
                        last_bit=-last_bit;
                        result[i+2]=result[i+3]=last_bit;
                        i++;
                    }else {
                        //无连0，即下一位非0,不出现电平跃变
                        result[i]=last_bit;
                        i++;
                        result[i]=last_bit;
                    }
                }else {
                    //code的最后一位且为0，不出现电平跃变
                    result[i]=last_bit;
                    i++;
                    result[i]=last_bit;
                }
            }else if(code.charAt((int)i/2)==one){
                //该位为1，出现跃变
                result[i]=last_bit;
                last_bit=-last_bit;
                i++;
                result[i]=last_bit;
            }
        }
        return result;
    }

    /**
     * 数字基带信号转CMI码
     * “1”码交替用“1 1”和“0 0”两位码表示；“0”码固定地用“01”表示
     * @param code
     * @return
     */
    public static int[] toCMI(String code){
        boolean flag=true;
        int result[]=new int[code.length()*2];
        for (int i=0;i<result.length;i++){
            if (code.charAt((int)i/2)==zero){
                result[i]=0;
                i++;
                result[i]=1;
            }else if(code.charAt((int)i/2)==one){
                result[i]=flag?1:0;
                i++;
                result[i]=flag?1:0;
                flag=flag?false:true;
            }
        }
        return result;
    }
}
