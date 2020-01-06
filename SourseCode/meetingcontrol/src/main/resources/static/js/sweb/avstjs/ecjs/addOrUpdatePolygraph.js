var etypessid;
var ssid;

//查询单个
function getToOutPolygraphById(ssid) {
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getToOutPolygraphById;
        var data={
            param:{
                ssid:ssid
            }
        };
        ajaxSubmitByJson(url,data,callbackgetToOutPolygraphById);
    }
}
function callbackgetToOutPolygraphById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var polygraph = data.data;
            $("input[name='port']").val(polygraph.port);
            $("input[name='polygraphtype']").val(polygraph.polygraphtype);
            $("input[name='polygraphkey']").val(polygraph.polygraphkey);
            $("input[name='etnum']").val(polygraph.etnum);
            $("input[name='etip']").val(polygraph.etip);
            $("#explain").text(polygraph.explain);

            base_etip = polygraph.etip;
            base_etnum = polygraph.etnum;
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}


function AddOrUpdateToOutPolygraph(version) {
    var url=null;
    if (isNotEmpty(ssid)){
        url=getUrl_manage().updateToOutPolygraph;
    }else {
        url=getUrl_manage().addToOutPolygraph;
    }
    var port=$("input[name='port']").val();
    var polygraphtype=$("input[name='polygraphtype']").val();
    var polygraphkey=$("input[name='polygraphkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var explain=$("textarea[name='explain']").val();

    if (!isNumber(port)) {
        layer.msg("端口号必须由数字组成",{icon: 5});
        return;
    }

    var data={
        param:{
            ssid: ssid,
            port: port,
            polygraphtype: polygraphtype,
            polygraphkey: polygraphkey,
            etypessid: etypessid,
            etnum: etnum,
            etip: etip,
            explain: explain
        }
    };
    ajaxSubmitByJson(url,data,callbackAddOrUpdateToOutPolygraph);
}
function callbackAddOrUpdateToOutPolygraph(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                parent.layer.msg("保存成功",{icon:6,time:500},function () {
                    parent.dqphssid=data.data;
                    parent.getToOutPolygraphList();
                    parent.layer.closeAll('iframe'); //关闭弹框
                });
            }
        }
    }else{
        parent.layer.msg(data.message,{icon: 5});
    }
}


layui.use(['laypage', 'form', 'layer', 'layedit', 'laydate', 'table'], function () {
    var $ = layui.$ //由于layer弹层依赖jQuery，所以可以直接得到
        , form = layui.form
        , layer = layui.layer;
    form.render();

    form.verify({
        setip: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(''==value){
                return "设备IP不能为空";
            }
            if(!(/^([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/.test(value))){
                return '请输入一个正确的IP地址';
            }
        }
    });

    form.on('submit(permissionSubmit)', function(data) {
        AddOrUpdateToOutPolygraph();
    });
});

