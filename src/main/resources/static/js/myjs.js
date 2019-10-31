function PCM_encode() {
    var Vs=$("#PCM_encode").val();
    $.ajax({
        type:"GET",
        url:"/PCM/encode/"+Vs,
        contentType:"application/json",
        success:function (response) {
            $("#PCM_encode_Vs").text(Vs);
            $("#PCM_encode_BinaryCode").text(response.folded_binary_code);
            $("#PCM_encode_quantization_error").text(response.quantization_error);
        },
        dataType:"json"
    });
}

function PCM_decode() {
    var binary_code=$("#PCM_decode").val();
    $.ajax({
        type:"GET",
        url:"/PCM/decode/"+binary_code,
        contentType:"application/json",
        success:function (response) {
            alert(response.vs)
            $("#PCM_decode_Vs").text(response.vs);
            $("#PCM_decode_BinaryCode").text(binary_code);
            // $("#PCM_decode_quantization_error").text(response.quantization_error);
        },
        dataType:"json"
    });
}

/**
 * 传递数字基带信号到后台编码，得到编码结果
 */
function digital_baseband_signal_encode() {
    var code=$("#digital_baseband_signal").val();
    $.ajax({
        type:"GET",
        url:"/BasebandTransmissionCode/"+code,
        contentType:"application/json",
        success:function (response) {
            ami(code,response.ami_result);
            manchester(code,response.manchester_result);
            hdb3(code,response.hdb3_result);
            differentialBiPhase(code,response.differentialBiPhase_result);
            miller(code,response.millerCode_result);
            cmi(code,response.cmi_result);
        },
        dataType:"json"
    });
    
}

/**
 * Manchester码转图形
 */
function manchester(code,result_array) {
    var result=result_array.slice(0);
    //处理横坐标
    var a=code.split("");           //010 -> 0,1,0
    var x=a.join("|").split("");    //0,1,0 -> 0|1|0 ->0,|,1,|,0
    for (i=0;i<x.length;i++){
        if (x[i]=="|"){
            x[i]="";
        }
    }
    x.push("");
    x.unshift("");
    //处理纵坐标
    result_array.unshift(result_array[0]);

    // 基于准备好的dom，初始化echarts实例
    var Manchester = echarts.init(document.getElementById('Manchester'));
    // 指定图表的配置项和数据
    show_chart(x,result_array,Manchester,"Manchester码:"+result);
}

/**
 * AMI码转图形
 */
function ami(code,result_array) {
    var result=result_array.slice(0);

    var x=code.split("");
    x.push("");
    //处理纵坐标
    result_array.unshift(0);
    result_array.push(0);

    // 基于准备好的dom，初始化echarts实例
    var AMI = echarts.init(document.getElementById('AMI'));
    // 指定图表的配置项和数据
    show_chart(x,result_array,AMI,"AMI码:"+result);
}

/**
 * HDB3码转图形
 */
function hdb3(code,result_array) {
    var result=result_array.slice(0);
    var x=code.split("");
    x.push("");
    //处理纵坐标
    result_array.unshift(0);
    result_array.push(0);

    // 基于准备好的dom，初始化echarts实例
    var AMI = echarts.init(document.getElementById('HDB3'));
    // 指定图表的配置项和数据
    show_chart(x,result_array,AMI,"HDB3码:"+result);
}

/**
 * 差分双相码转图形
 */
function differentialBiPhase(code,result_array) {
    var result=result_array.slice(0);
    //处理横坐标
    var x=code.split("").join("|").split("");    //010 -> 0,1,0 -> 0|1|0 ->0,|,1,|,0
    for (i=0;i<x.length;i++){
        if (x[i]=="|"){
            x[i]="";
        }
    }
    x.push("");
    x.unshift("");
    //处理纵坐标
    result_array.unshift(result_array[0]);

    // 基于准备好的dom，初始化echarts实例
    var DifferentialBiPhase = echarts.init(document.getElementById('DifferentialBiPhase'));
    // 指定图表的配置项和数据
    show_chart(x,result_array,DifferentialBiPhase,"差分双相码:"+result);
}

/**
 * 密勒码转图形
 */
function miller(code,result_array) {
    var result=result_array.slice(0);
    //处理横坐标
    var x=code.split("").join("|").split("");    //010 -> 0,1,0 -> 0|1|0 ->0,|,1,|,0
    for (i=0;i<x.length;i++){
        if (x[i]=="|"){
            x[i]="";
        }
    }
    x.push("");
    x.unshift("");
    //处理纵坐标
    result_array.unshift(result_array[0]);

    // 基于准备好的dom，初始化echarts实例
    var Miller = echarts.init(document.getElementById('Miller'));
    // 指定图表的配置项和数据
    show_chart(x,result_array,Miller,"Miller码:"+result);
}


/**
 * CMI码转图形
 */
function cmi(code,result_array) {
    var result=result_array.slice(0);
    //处理横坐标
    var x=code.split("").join("|").split("");    //010 -> 0,1,0 -> 0|1|0 ->0,|,1,|,0
    for (i=0;i<x.length;i++){
        if (x[i]=="|"){
            x[i]="";
        }
    }
    x.push("");
    x.unshift("");
    //处理纵坐标
    result_array.unshift(result_array[0]);

    // 基于准备好的dom，初始化echarts实例
    var CMI = echarts.init(document.getElementById('CMI'));
    // 指定图表的配置项和数据
    show_chart(x,result_array,CMI,"CMI码:"+result);
}

function show_chart(x, y, chart,title) {
    var option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: x

        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'Step Start',
                type: 'line',
                step: 'start',
                data: y
            }
        ]
    };
// 使用刚指定的配置项和数据显示图表。
    chart.setOption(option);
}
