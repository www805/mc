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
    var html='<form class="layui-form  site-inline"  style="margin: 30px;">\
        <div class="layui-form-item">\
            <label class="layui-form-label">直播地址</label>\
            <div class="layui-input-block">\
            <input type="text" name="livingurl" id="livingurl" lay-verify="required" placeholder="请输入直播地址" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">开放接口的端口</label>\
            <div class="layui-input-block">\
            <input type="number" name="port" id="port" required="" lay-verify="required" placeholder="请输入开放接口的端口" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">登录用户名</label>\
            <div class="layui-input-block">\
            <input type="text" name="user" id="user" required="" lay-verify="required" placeholder="请输入登录用户名" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">登录密码</label>\
            <div class="layui-input-block">\
            <input type="text" name="passwd" id="passwd" required="" placeholder="请输入登录密码" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">ftp上传存储路径</label>\
            <div class="layui-input-block">\
            <input type="text" name="uploadbasepath" id="uploadbasepath" required="" placeholder="请输入ftp上传存储路径" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">设备编号</label>\
            <div class="layui-input-block">\
            <input type="text" name="etnum" id="etnum" required="" placeholder="请输入设备编号" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">设备IP</label>\
            <div class="layui-input-block">\
            <input type="text" name="etip" id="etip" required="" lay-verify="required" placeholder="请输入设备IP" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item layui-form-text">\
            <label class="layui-form-label">中文解释</label>\
            <div class="layui-input-block">\
            <textarea name="explain" id="explain" placeholder="请输入中文解释" class="layui-textarea"></textarea>\
            </div>\
        </div>\
        </form>';
   layer.open({
        type:1,
        title:'审讯设备编辑',
        content: html,
        area: ['800px', '700px'],
        btn: ['确定', '取消'],
        yes:function(index, layero){
            var url=null;
            if (isNotEmpty(ssid)){
                url=getUrl_manage().updateToOutFlushbonading;
            }else {
                url=getUrl_manage().addToOutFlushbonading;
            }

            var livingurl=$("input[name='livingurl']").val();
            var port=$("input[name='port']").val();
            var user=$("input[name='user']").val();
            var passwd=$("input[name='passwd']").val();
            var uploadbasepath=$("input[name='uploadbasepath']").val();
            var etnum=$("input[name='etnum']").val();
            var etip=$("input[name='etip']").val();
            var explain=$("textarea[name='explain']").val();

            if (!isNotEmpty(livingurl)||!isNotEmpty(port)||!isNotEmpty(user)||!isNotEmpty(passwd)||!isNotEmpty(uploadbasepath)||!isNotEmpty(etnum)||!isNotEmpty(etip)||!isNotEmpty(explain)) {
                layer.msg("请输入完整",{icon: 2});
                return;
            }


            var data={
                param:{
                    livingurl: livingurl,
                    port: port,
                    user: user,
                    passwd: passwd,
                    uploadbasepath: uploadbasepath,
                    etnum: etnum,
                    etip: etip,
                    etypessid: etypessid,
                    explain: explain,
                    ssid:ssid
                }
            };
            ajaxSubmitByJson(url,data,callbackAddOrUpdateToOutFlushbonading);
            layer.close(index);
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
    getToOutFlushbonadingById(ssid);
    layui.use('form', function(){
        var form =  layui.form;
        form.render();
    });
}

function getToOutFlushbonadingById(ssid) {
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getToOutFlushbonadingById;
        var data={
            param:{
                ssid:ssid
            }
        };
        ajaxSubmitByJson(url,data,callbackgetToOutFlushbonadingById);
    }
}
function callbackgetToOutFlushbonadingById(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var data=data.data;
            if (isNotEmpty(data)) {
                var flushbonading = data;

                $("input[name='livingurl']").val(flushbonading.livingurl);
                $("input[name='port']").val(flushbonading.port);
                $("input[name='user']").val(flushbonading.user);
                $("input[name='passwd']").val(flushbonading.passwd);
                $("input[name='uploadbasepath']").val(flushbonading.uploadbasepath);
                $("input[name='etnum']").val(flushbonading.etnum);
                $("input[name='etip']").val(flushbonading.etip);
                $("#explain").text(flushbonading.explain);

                etypessid=flushbonading.etypessid;
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callbackAddOrUpdateToOutFlushbonading(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                dqfdssid=data.data;
                layer.msg("保存成功",{icon: 1,time:1000},function () {
                    getToOutFlushbonadingList();
                });
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
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
    var html='<form class="layui-form  site-inline"  style="margin: 30px;">\
        <div class="layui-form-item">\
            <label class="layui-form-label">通道编号</label>\
            <div class="layui-input-block">\
            <input type="text" name="tdnum" id="tdnum" required="" lay-verify="required" placeholder="请输入通道编号" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">通道拉流地址</label>\
            <div class="layui-input-block">\
            <input type="text" name="pullflowurl" id="pullflowurl" required="" lay-verify="required" placeholder="请输入通道拉流地址" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">通道类型</label>\
            <div class="layui-input-block">\
            <input type="number" name="tdtype" id="tdtype" required="" placeholder="请输入通道类型" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        </form>';
    layer.open({
        type:1,
        title:'设备通道编辑',
        content: html,
        area: ['800px', '700px'],
        btn: ['确定', '取消'],
        yes:function(index, layero){
            var url=null;
            if (isNotEmpty(ssid)){
                url=getUrl_manage().updateToOutFlushbonadingEttd;
            }else {
                url=getUrl_manage().addToOutFlushbonadingEttd;
            }

            var tdnum=$("input[name='tdnum']").val();
            var pullflowurl=$("input[name='pullflowurl']").val();
            var tdtype=$("input[name='tdtype']").val();
            if (!isNotEmpty(tdnum)||!isNotEmpty(pullflowurl)||!isNotEmpty(tdtype)) {
                layer.msg("请输入完整",{icon: 2});
                return;
            }
            var data={
                param:{
                    ssid: ssid,
                    flushbonadingssid:dqfdssid,
                    tdnum: tdnum,
                    pullflowurl: pullflowurl,
                    tdtype: tdtype
                }
            };
            ajaxSubmitByJson(url,data,callbackAddOrUpdateToOutFlushbonadingEttd);
            layer.close(index);
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
    getToOutFlushbonadingEttdById(ssid);
    layui.use('form', function(){
        var form =  layui.form;
        form.render();
    });
}

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
function callbackgetToOutFlushbonadingEttdById(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var data=data.data;
            if (isNotEmpty(data)) {
                var flushbonadingEttd = data;

                $("input[name='tdnum']").val(flushbonadingEttd.tdnum);
                $("input[name='pullflowurl']").val(flushbonadingEttd.pullflowurl);
                $("input[name='tdtype']").val(flushbonadingEttd.tdtype);

                etypessid=flushbonadingEttd.etypessid;
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callbackAddOrUpdateToOutFlushbonadingEttd(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                dqtdssid=data.data;
                layer.msg("保存成功",{icon: 1,time:1000},function () {
                    getToOutFlushbonadingEttdList();
                });
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
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
    var html='<form class="layui-form  site-inline"  style="margin: 30px;">\
        <div class="layui-form-item">\
            <label class="layui-form-label">开放接口的端口</label>\
            <div class="layui-input-block">\
            <input type="number" name="port" id="port" required="" lay-verify="required" placeholder="请输入开放接口的端口" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">服务类型</label>\
            <div class="layui-input-block">\
            <input type="text" name="polygraphtype" id="polygraphtype" required="" lay-verify="required" placeholder="请输入服务类型" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">验证密匙</label>\
            <div class="layui-input-block">\
            <input type="text" name="polygraphkey" id="polygraphkey" required="" placeholder="请输入验证密匙" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">设备编号</label>\
            <div class="layui-input-block">\
            <input type="text" name="etnum" id="etnum" required="" placeholder="请输入设备编号" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">设备IP</label>\
            <div class="layui-input-block">\
            <input type="text" name="etip" id="etip" required="" lay-verify="required" placeholder="请输入设备IP" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item layui-form-text">\
            <label class="layui-form-label">中文解释</label>\
            <div class="layui-input-block">\
            <textarea name="explain" id="explain" placeholder="请输入中文解释" class="layui-textarea"></textarea>\
            </div>\
        </div>\
        </form>';
    layer.open({
        type:1,
        title:'测谎仪编辑',
        content: html,
        area: ['800px', '700px'],
        btn: ['确定', '取消'],
        yes:function(index, layero){
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
            if (!isNotEmpty(port)||!isNotEmpty(polygraphtype)||!isNotEmpty(polygraphkey)||!isNotEmpty(etnum)||!isNotEmpty(etip)||!isNotEmpty(explain)) {
                layer.msg("请输入完整",{icon: 2});
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
            layer.close(index);
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
    getToOutPolygraphById(ssid);
    layui.use('form', function(){
        var form =  layui.form;
        form.render();
    });
}

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
function callbackgetToOutPolygraphById(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var data=data.data;
            if (isNotEmpty(data)) {
                var polygraph = data;

                $("input[name='port']").val(polygraph.port);
                $("input[name='polygraphtype']").val(polygraph.polygraphtype);
                $("input[name='polygraphkey']").val(polygraph.polygraphkey);
                $("input[name='etnum']").val(polygraph.etnum);
                $("input[name='etip']").val(polygraph.etip);
                $("#explain").text(polygraph.explain);

                etypessid=polygraph.etypessid;
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callbackAddOrUpdateToOutPolygraph(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                dqphssid=data.data;
                layer.msg("保存成功",{icon: 1,time:1000},function () {
                    getToOutPolygraphList();
                });
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
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
    var html='<form class="layui-form  site-inline" action="" style="margin-top: 30px;">\
        <div class="layui-form-item">\
            <label class="layui-form-label">识别语种</label>\
            <div class="layui-input-block">\
            <input type="text" name="language" id="language" required="" lay-verify="required" placeholder="请输入识别语种" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">并发数</label>\
            <div class="layui-input-block">\
            <input type="number" name="maxnum" id="maxnum" required="" lay-verify="required" placeholder="请输入并发数" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">开放接口的端口</label>\
            <div class="layui-input-block">\
            <input type="number" name="port" id="port" required="" lay-verify="required" placeholder="请输入开放接口的端口" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">语音服务类型</label>\
            <div class="layui-input-block">\
            <input type="text" name="asrtype" id="asrtype" required="" placeholder="请输入语音服务类型" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">识别验证密匙</label>\
            <div class="layui-input-block">\
            <input type="text" name="asrkey" id="asrkey" required="" placeholder="请输入识别验证密匙" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">设备编号</label>\
            <div class="layui-input-block">\
            <input type="text" name="etnum" id="etnum" required="" placeholder="请输入设备编号" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item">\
            <label class="layui-form-label">设备IP</label>\
            <div class="layui-input-block">\
            <input type="text" name="etip" id="etip" required="" lay-verify="required" placeholder="请输入设备IP" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item layui-form-text">\
            <label class="layui-form-label">中文解释</label>\
            <div class="layui-input-block">\
            <textarea name="explain" id="explain" placeholder="请输入中文解释" class="layui-textarea"></textarea>\
            </div>\
        </div>\
        </form>';
    layer.open({
        type:1,
        title:'语音识别服务编辑',
        content: html,
        area: ['800px', '700px'],
        btn: ['确定', '取消'],
        yes:function(index, layero){
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
            if (!isNotEmpty(language)||!isNotEmpty(maxnum)||!isNotEmpty(port)||!isNotEmpty(asrtype)||!isNotEmpty(asrkey)||!isNotEmpty(etnum)||!isNotEmpty(etip)||!isNotEmpty(explain)) {
                layer.msg("请输入完整",{icon: 2});
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
            ajaxSubmitByJson(url,data,callbackAddOrUpdateToOutAsr);
            layer.close(index);
        },
        btn2:function(index, layero){
            layer.close(index);
        }
    });
    getToOutAsrById(ssid);
    layui.use('form', function(){
        var form =  layui.form;
        form.render();
    });
}

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
function callbackgetToOutAsrById(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var data=data.data;
            if (isNotEmpty(data)) {
                var asr = data;

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
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callbackAddOrUpdateToOutAsr(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                dqasrssid=data.data;
                layer.msg("保存成功",{icon: 1,time:1000},function () {
                    getToOutAsrList();
                });
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
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
