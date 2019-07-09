function getAvstmt_modeltdByssid(){
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getAvstmt_modeltdByssid;
        var data={
            ssid:ssid
        };
        ajaxSubmit(url,data,callbackgetAvstmt_modeltdByssid);
    }
}
function callbackgetAvstmt_modeltdByssid(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var data=data.data;
        if (isNotEmpty(data)){
         var avstmt_modeltd=data.avstmt_modeltd;
         if (isNotEmpty(avstmt_modeltd)){
             mtmodelssid=avstmt_modeltd.mtmodelssid;
             $("#fdssid").val(avstmt_modeltd.fdssid);
             $("#tdssid").val(avstmt_modeltd.tdssid);
             $("#polygraphssid").val(avstmt_modeltd.polygraphssid);
             $("#usepolygraph").val(avstmt_modeltd.usepolygraph);
             $("#useasr").val(avstmt_modeltd.useasr);
             $("#asrssid").val(avstmt_modeltd.asrssid);
             $("#grade").val(avstmt_modeltd.grade);
         }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
    layui.use('form', function(){
        var form =  layui.form;
        form.render();
    });
}

function Avstmt_modeltdAddOrUpdate() {
    var url=null;
    if (isNotEmpty(ssid)){
        url=getUrl_manage().updateAvstmt_modeltd;
    }else {
        url=getUrl_manage().addAvstmt_modeltd;
    }

    var fdssid = $("#fdssid").val();
    var tdssid = $("#tdssid").val();
    var polygraphssid = $("#polygraphssid").val();
    var usepolygraph = $("#usepolygraph").val();
    var useasr = $("#useasr").val();
    var asrssid = $("#asrssid").val();
    var grade = $("#grade").val();


    var data={
        fdssid:fdssid,
        tdssid:tdssid,
        polygraphssid:polygraphssid,
        usepolygraph:usepolygraph,
        useasr:useasr,
        asrssid:asrssid,
        grade:grade,
        ssid:ssid
    };
    ajaxSubmit(url,data,callbackAvstmt_modeltdAddOrUpdate);

}

function callbackAvstmt_modeltdAddOrUpdate(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            layer.msg("保存成功", {time: 500, icon:1},function () {
                window.location.href=getUrl_manage().toAvstmt_modeltdList+"?mtmodelssid="+mtmodelssid;
            });
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}