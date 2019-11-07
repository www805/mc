
function getAvstmt_modelByssid(){
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getAvstmt_modelByssid;
        var data={
            ssid:ssid
        };
        ajaxSubmit(url,data,callbackgetAvstmt_modelByssid);
    }
}
function callbackgetAvstmt_modelByssid(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var data=data.data;
        if (isNotEmpty(data)){
            var avstmt_model=data.avstmt_model;
            if (isNotEmpty(avstmt_model)){
                $("#meetingtype").val(avstmt_model.meetingtype);
                $("#usernum").val(avstmt_model.usernum);
                $("#opened").val(avstmt_model.opened);
                $("#userecord").val(avstmt_model.userecord);
                $("#explain").val(avstmt_model.explain);
                $("#asrservermodel").val(avstmt_model.asrservermodel);
                $("#asrnum").val(avstmt_model.asrnum);
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

function Avstmt_modelAddOrUpdate() {
    var url=null;
    if (isNotEmpty(ssid)){
         url=getUrl_manage().updateAvstmt_model;
    }else{
         url=getUrl_manage().addAvstmt_model;
    }
    var meetingtype = $("#meetingtype").val();
    var usernum = $("#usernum").val();
    var opened = $("#opened").val();
    var userecord = $("#userecord").val();
    var explain = $("#explain").val();
    var asrservermodel = $("#asrservermodel").val();
    var asrnum = $("#asrnum").val();

    if (!isNotEmpty(meetingtype)) {
        layer.msg("请选择会议类型",{icon: 2});
        return;
    }
    if (!isNotEmpty(usernum)) {
        layer.msg("请输入人员数量",{icon: 2});
        return;
    }
    if (!isNotEmpty(opened)) {
        layer.msg("请选择是否公开",{icon: 2});
        return;
    }
    if (!isNotEmpty(userecord)) {
        layer.msg("请输入是否录制",{icon: 2});
        return;
    }
    if (!isNotEmpty(asrservermodel)) {
        layer.msg("请选择语音识别类型",{icon: 2});
        return;
    }

    var data={
        meetingtype:meetingtype,
        usernum:usernum,
        opened:opened,
        userecord:userecord,
        asrservermodel:asrservermodel,
        asrnum:asrnum,
        explain:explain,
        ssid:ssid
    };
    ajaxSubmit(url,data,callbackAvstmt_modelAddOrUpdate);
}

function callbackAvstmt_modelAddOrUpdate(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            layer.msg("保存成功", {time: 500, icon:1},function () {
                window.location.href=getUrl_manage().toAvstmt_modelList;
            });
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}