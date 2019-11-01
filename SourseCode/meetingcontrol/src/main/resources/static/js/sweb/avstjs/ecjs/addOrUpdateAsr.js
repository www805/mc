var etypessid;
var ssid;
//查询单个
function getToOutAsrById(ssid) {
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getToOutAsrById;
        var data={
            param:{
                ssid:ssid
            }
        };
        ajaxSubmitByJson(url,data,callbackgetToOutAsrById);
    }
}
function callbackgetToOutAsrById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var asr = data.data;
            $("input[name='language']").val(asr.language);
            $("input[name='maxnum']").val(asr.maxnum);
            $("input[name='port']").val(asr.port);
            $("input[name='asrtype']").val(asr.asrtype);
            $("input[name='asrkey']").val(asr.asrkey);
            $("input[name='etnum']").val(asr.etnum);
            $("input[name='etip']").val(asr.etip);
            $("#explain").text(asr.explain);
            etypessid=asr.etypessid;
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

//编辑
function AddOrUpdateToOutAsr() {
    var url=null;
    if (isNotEmpty(ssid)){
        url=getUrl_manage().updateToOutAsr;
    }else {
        url=getUrl_manage().addToOutAsr;
    }
    var language=$("input[name='language']").val();
    var maxnum=$("input[name='maxnum']").val();
    var port=$("input[name='port']").val();
    var asrtype=$("input[name='asrtype']").val();
    var asrkey=$("input[name='asrkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var explain=$("textarea[name='explain']").val();

    if (!isNumber(port)) {
        layer.msg("端口号必须由数字组成",{icon: 5});
        return;
    }
    if (!isNumber(maxnum)) {
        layer.msg("并发数必须由数字组成",{icon: 5});
        return;
    }
    var data={
        param:{
            ssid: ssid,
            language: language,
            maxnum: maxnum,
            port: port,
            asrtype: asrtype,
            asrkey: asrkey,
            etypessid: etypessid,
            etnum: etnum,
            etip: etip,
            explain: explain
        }
    };
    ajaxSubmitByJson(url, data, callbackAddOrUpdateToOutAsr);
}
function callbackAddOrUpdateToOutAsr(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                parent.layer.msg("保存成功",{icon:6,time:500},function () {
                    parent.dqasrssid=data.data;
                    parent.getToOutAsrList();
                    parent.layer.closeAll('iframe'); //关闭弹框
                });
            }
        }
    }else{
        parent.layer.msg(data.message,{icon: 5});
    }
}

$(function () {
    layui.use(['layer','laydate','form'], function() {
        var form = layui.form;
        var laydate = layui.laydate;


        form.verify({
            setip: function(value, item){
                if(''==value){
                    return "设备IP不能为空";
                }
                if(!(/^([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/.test(value))){
                    return '请输入一个正确的IP地址';
                }
            }
        });

        form.on('submit(permissionSubmit)', function(data) {
            AddOrUpdateToOutAsr();
        });

    })
})

