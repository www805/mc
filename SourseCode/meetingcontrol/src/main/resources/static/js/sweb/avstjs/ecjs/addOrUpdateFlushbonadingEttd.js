var masterssid;
var etypessid;
var ssid;


//查询单个
function getToOutFlushbonadingEttdById(ssid) {
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getToOutFlushbonadingEttdById;
        var data={
            param:{
                ssid:ssid
            }
        };
        ajaxSubmitByJson(url,data,callbackgetToOutFlushbonadingEttdById);
    }
}
function callbackgetToOutFlushbonadingEttdById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var flushbonadingEttd = data.data;
            $("input[name='tdnum']").val(flushbonadingEttd.tdnum);
            $("input[name='pullflowurl']").val(flushbonadingEttd.pullflowurl);
            $("input[name='shockenergy']").val(flushbonadingEttd.shockenergy);
            if (flushbonadingEttd.tdtype == 1) {
                $("#tdtype option[value='1']").attr("selected", true);
            }else{
                $("#tdtype option[value='2']").attr("selected", true);
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}


function AddOrUpdateFlushbonadingEttd() {
    var url=null;
    if (isNotEmpty(ssid)){
        url=getUrl_manage().updateToOutFlushbonadingEttd;
    }else {
        url=getUrl_manage().addToOutFlushbonadingEttd;
    }

    var tdnum=$("input[name='tdnum']").val();
    var pullflowurl=$("input[name='pullflowurl']").val();
    var shockenergy=$("input[name='shockenergy']").val();
    var tdtype=$("#tdtype").val();

    var data={
        param:{
            ssid: ssid,
            flushbonadingssid:masterssid,
            tdnum: tdnum,
            pullflowurl: pullflowurl,
            tdtype: tdtype,
            shockenergy: shockenergy,
        }
    };
    ajaxSubmitByJson(url,data,callbackAddOrUpdateToOutFlushbonadingEttd);
}
function callbackAddOrUpdateToOutFlushbonadingEttd(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                parent.layer.msg("保存成功",{icon:6,time:500},function () {
                    parent.dqtdssid=data.data;
                    parent.getToOutFlushbonadingEttdList();
                    parent.layer.closeAll('iframe'); //关闭弹框
                });
            }
        }
    }else{
        parent.layer.msg(data.message,{icon: 5});
    }
}

$(function () {
    layui.use(['laypage', 'form', 'layer', 'layedit', 'laydate', 'table'], function () {
        var $ = layui.$ //由于layer弹层依赖jQuery，所以可以直接得到
            , form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate
            , laypage = layui.laypage;
        var table = layui.table;

        form.render();

        form.on('submit(permissionSubmit)', function (data) {
            AddOrUpdateFlushbonadingEttd();
        });

    });
});

