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
             $("#fdssid").find("option[value='"+avstmt_modeltd.fdssid+"']").attr("selected",true);
             $("#tdssid").val(avstmt_modeltd.tdssid);
             $("#polygraphssid").val(avstmt_modeltd.polygraphssid);
             $("#usepolygraph").val(avstmt_modeltd.usepolygraph);
             $("#useasr").val(avstmt_modeltd.useasr);
             $("#asrssid").val(avstmt_modeltd.asrssid);
             $("#grade").val(avstmt_modeltd.grade);


              dqfdssid=avstmt_modeltd.fdssid;//当前dqfdssid
              dqphssid=avstmt_modeltd.polygraphssid;//当前dqphssid
              dqasrssid=avstmt_modeltd.asrssid;//当前dqasrssid
              dqtdssid=avstmt_modeltd.tdssid;//当前tdssid

             getToOutFlushbonadingEttdList();

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


    if (!isNotEmpty(fdssid)) {
        layer.msg("请选择审讯设备",{icon: 2});
        return;
    }
    if (!isNotEmpty(tdssid)) {
        layer.msg("请选择设备通道",{icon: 2});
        return;
    }
    if (!isNotEmpty(polygraphssid)) {
        layer.msg("请选择测谎仪",{icon: 2});
        return;
    }
    if (!isNotEmpty(asrssid)) {
        layer.msg("请选择语音识别服务",{icon: 2});
        return;
    }
    if (!isNotEmpty(grade)) {
        layer.msg("请选择通道级别",{icon: 2});
        return;
    }
    if (!isNotEmpty(usepolygraph)) {
        layer.msg("请选择是否需要测谎",{icon: 2});
        return;
    }
    if (!isNotEmpty(useasr)) {
        layer.msg("请选择是否语音识别",{icon: 2});
        return;
    }


    var data={
        fdssid:fdssid,
        tdssid:tdssid,
        polygraphssid:polygraphssid,
        usepolygraph:usepolygraph,
        useasr:useasr,
        asrssid:asrssid,
        grade:grade,
        mtmodelssid:mtmodelssid,
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








//**eq接口**/

//审讯设备--------------------------------------------------------------------------------------------------------------start
function getToOutFlushbonadingList() {
    var url=getUrl_manage().getToOutFlushbonadingList;
    var data={
       param:{

       }
    };
    ajaxSubmitByJson(url,data,callbackgetToOutFlushbonadingList);
}
function callbackgetToOutFlushbonadingList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var list=data.data;
        $('#fdssid option').not(":lt(1)").remove();
        if (isNotEmpty(list)){
           if (isNotEmpty(list)){
               for (var i = 0; i < list.length; i++) {
                   var l = list[i];
                   $("#fdssid").append("<option value='"+l.ssid+"'> "+l.explain+"</option>");
               }
           }

           //选中select
            if (isNotEmpty(dqfdssid)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    if (dqfdssid==l.ssid){
                        $("#fdssid").val(l.ssid);
                    }
                }
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

//bool 1为需要修改
function open_html(bool) {
    var ssid=null;
    if (isNotEmpty(bool)&&bool==1){
        ssid=$("#fdssid").val();
        if (!isNotEmpty(ssid)){
            layer.msg("请选择需要修改的审讯设备");
            return;
        }
    }
    var url=getUrl_manage().toaddOrUpdateFlushbonading+"?ssid="+ssid;
    layer.open({
        type: 2,
        title:'审讯设备编辑',
        content:url,
        area: ['70%', '90%'],
        btn: ['确定','取消'],
        success:function(layero, index){
            $(':focus').blur();
            var fd_iframe = window['layui-layer-iframe' + index];
            var td_body=layer.getChildFrame('body', index);
        },
        yes:function(index, layero){
            var formSubmit=layer.getChildFrame('body', index);
            var submited = formSubmit.find('#permissionSubmit')[0];
            submited.click();
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
}
//审讯设备--------------------------------------------------------------------------------------------------------------end

//设备通道--------------------------------------------------------------------------------------------------------------start
function getToOutFlushbonadingEttdList() {
    if (isNotEmpty(dqfdssid)){
        var url=getUrl_manage().getToOutFlushbonadingEttdList;
        var data={
            param:{
                ssid:dqfdssid
            }
        };
        ajaxSubmitByJson(url,data,callbackgetToOutFlushbonadingEttdList);
    }
}
function callbackgetToOutFlushbonadingEttdList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var list=data.data;
        $('#tdssid option').not(":lt(1)").remove();
        if (isNotEmpty(list)){
            if (isNotEmpty(list)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    $("#tdssid").append("<option value='"+l.ssid+"'> "+l.pullflowurl+"</option>");
                }
            }

            //选中select
            if (isNotEmpty(dqtdssid)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    if (dqtdssid==l.ssid){
                        $("#tdssid").val(l.ssid);
                    }
                }
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

//bool 1为需要修改
function open_html4(bool) {
    var ssid=null;
    if (isNotEmpty(bool)&&bool==1){
        ssid=$("#tdssid").val();
        if (!isNotEmpty(ssid)){
            layer.msg("请选择需要修改的设备通道");
            return;
        }
    }
    var url=getUrl_manage().toaddOrUpdateFlushbonadingEttd+"?ssid="+ssid+"&masterssid="+dqfdssid;
    layer.open({
        type: 2,
        title:'设备通道编辑',
        content:url,
        area: ['65%', '80%'],
        btn: ['确定','取消'],
        success:function(layero, index){
            $(':focus').blur();
            var td_iframe = window['layui-layer-iframe' + index];
            var td_body=layer.getChildFrame('body', index);
        },
        yes:function(index, layero){
            var formSubmit=layer.getChildFrame('body', index);
            var submited = formSubmit.find('#permissionSubmit')[0];
            submited.click();
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
}
//设备通道--------------------------------------------------------------------------------------------------------------end




//测谎仪--------------------------------------------------------------------------------------------------------------start
function getToOutPolygraphList() {
    var url=getUrl_manage().getToOutPolygraphList;
    var data={
        param:{

        }
    };
    ajaxSubmitByJson(url,data,callbackgetToOutPolygraphList);
}
function callbackgetToOutPolygraphList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var list=data.data;
        $('#polygraphssid option').not(":lt(1)").remove();
        if (isNotEmpty(list)){
            if (isNotEmpty(list)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    $("#polygraphssid").append("<option value='"+l.ssid+"'> "+l.explain+"</option>");
                }
            }

            //选中select
            if (isNotEmpty(dqphssid)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    if (dqphssid==l.ssid){
                        $("#polygraphssid").val(l.ssid);
                    }
                }
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

//bool 1为需要修改
function open_html2(bool) {
    var ssid=null;
    if (isNotEmpty(bool)&&bool==1){
        ssid=$("#polygraphssid").val();
        if (!isNotEmpty(ssid)){
            layer.msg("请选择需要修改的测谎仪");
            return;
        }
    }
    var url=getUrl_manage().toaddOrUpdatePolygraph+"?ssid="+ssid;
    layer.open({
        type: 2,
        title:'测谎仪编辑',
        content:url,
        area: ['65%', '80%'],
        btn: ['确定','取消'],
        success:function(layero, index){
            $(':focus').blur();
            var ph_iframe = window['layui-layer-iframe' + index];
            var ph_body=layer.getChildFrame('body', index);
        },
        yes:function(index, layero){
            var formSubmit=layer.getChildFrame('body', index);
            var submited = formSubmit.find('#permissionSubmit')[0];
            submited.click();
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
}
//测谎仪--------------------------------------------------------------------------------------------------------------end



//语音识别--------------------------------------------------------------------------------------------------------------start
function getToOutAsrList() {
    var url=getUrl_manage().getToOutAsrList;
    var data={
        param:{

        }
    };
    ajaxSubmitByJson(url,data,callbackgetToOutAsrList);
}
function callbackgetToOutAsrList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var list=data.data;
        $('#asrssid option').not(":lt(1)").remove();
        if (isNotEmpty(list)){
            if (isNotEmpty(list)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    $("#asrssid").append("<option value='"+l.ssid+"'> "+l.explain+"</option>");
                }
            }

            //选中select
            if (isNotEmpty(dqasrssid)){
                for (var i = 0; i < list.length; i++) {
                    var l = list[i];
                    if (dqasrssid==l.ssid){
                        $("#asrssid").val(l.ssid);
                    }
                }
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

//bool 1为需要修改
function open_html3(bool) {
    var ssid=null;
    if (isNotEmpty(bool)&&bool==1){
        ssid=$("#asrssid").val();
        if (!isNotEmpty(ssid)){
            layer.msg("请选择需要修改语音识别服务");
            return;
        }
    }
    var url=getUrl_manage().toaddOrUpdateAsr+"?ssid="+ssid+"&etypessid="+etypessid;
    layer.open({
        type: 2,
        title:'语音识别服务编辑',
        content:url,
        area: ['65%', '80%'],
        btn: ['确定','取消'],
        success:function(layero, index){
            $(':focus').blur();
            var asr_iframe = window['layui-layer-iframe' + index];
            var asr_body=layer.getChildFrame('body', index);
        },
        yes:function(index, layero){
            var formSubmit=layer.getChildFrame('body', index);
            var submited = formSubmit.find('#permissionSubmit')[0];
            submited.click();
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
}

//语音识别--------------------------------------------------------------------------------------------------------------end




$(function () {
    layui.use(['form','jquery','laydate'], function() {
        var form=layui.form;

        form.on('select(change_fdssid)', function(data){
            dqfdssid=data.value;
            getToOutFlushbonadingEttdList();
            form.render('select','change_fdssid');
        });

        form.on('select(change_tdssid)', function(data){
            dqtdssid=data.value;
            form.render('select','change_tdssid');
        });

        form.on('select(change_polygraphssid)', function(data){
            dqpolygraphssid=data.value;
            form.render('select','change_polygraphssid');
        });

        form.on('select(change_asrssid)', function(data){
            dqasrssid=data.value;
            form.render('select','change_asrssid');
        });

    });
});
