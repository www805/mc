var etypessid;
var ssid;
var goaction = false;
var fromFTPStr;


//查询单个
function getToOutFlushbonadingById(ssid) {
    if (isNotEmpty(ssid)){
        var url=getUrl_manage().getToOutFlushbonadingById;
        var data={
            param:{
                flushbonadingetinfossid:ssid
            }
        };
        ajaxSubmitByJson(url,data,callbackgetToOutFlushbonadingById);
    }
}
function callbackgetToOutFlushbonadingById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var flushbonading = data.data;

            $("input[name='livingurl']").val(flushbonading.livingurl);
            $("input[name='previewurl']").val(flushbonading.previewurl);
            $("input[name='port']").val(flushbonading.port);
            $("input[name='user']").val(flushbonading.user);
            $("input[name='passwd']").val(flushbonading.passwd);
            $("input[name='uploadbasepath']").val(flushbonading.uploadbasepath);
            $("input[name='etnum']").val(flushbonading.etnum);
            $("input[name='etip']").val(flushbonading.etip);
            $("#diskrecbool").prop("checked", flushbonading.diskrecbool == 1);
            $("#burnbool").prop("checked", flushbonading.burnbool == 1);
            $("#defaulturlbool").prop("checked", flushbonading.defaulturlbool == 1);
            $("#burntime").find("option[value='" + flushbonading.burntime + "']").attr("selected", true);
            $("#ptshowtime").find("option[value='" + flushbonading.ptshowtime + "']").attr("selected", true);
            $("input[name='ptjson']").val(flushbonading.ptjson);
            $("#explain").text(flushbonading.explain);

            if (isNotEmpty(flushbonading.ptjson)) {
                $("#getptdjconstBtn").val("更新片头列表");
            }

            base_etip = flushbonading.etip;
            base_etnum = flushbonading.etnum;

        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}
//编辑
function AddOrUpdateToOutFlushbonading() {
    var url=null;
    if (isNotEmpty(ssid)){
        url=getUrl_manage().updateToOutFlushbonading;
    }else {
        url=getUrl_manage().addToOutFlushbonading;
    }

    var livingurl=$("input[name='livingurl']").val();
    var previewurl=$("input[name='previewurl']").val();
    var port=$("input[name='port']").val();
    var user=$("input[name='user']").val();
    var passwd=$("input[name='passwd']").val();
    var uploadbasepath=$("input[name='uploadbasepath']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();

    var diskrecbool=$("#diskrecbool").prop("checked")==true?1:0;
    var burnbool=$("#burnbool").prop("checked")==true?1:0;
    var defaulturlbool=$("#defaulturlbool").prop("checked")==true?1:0;
    var burntime = $("#burntime").val();
    var ptshowtime = $("#ptshowtime").val();
    var ptjson=$("input[name='ptjson']").val();

    var explain=$("textarea[name='explain']").val();

    if (!isNumber(port)) {
        layer.msg("端口号必须由数字组成",{icon: 5});
        return;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            livingurl: livingurl,
            previewurl: previewurl,
            port: port,
            user: user,
            passwd: passwd,
            uploadbasepath: uploadbasepath,
            etypessid: etypessid,
            etnum: etnum,
            etip: etip,
            diskrecbool: diskrecbool,
            burnbool: burnbool,
            defaulturlbool: defaulturlbool,
            burntime: burntime,
            ptshowtime: ptshowtime,
            ptjson: ptjson,
            explain: explain
        }
    };
    ajaxSubmitByJson(url, data, callbackAddOrUpdateToOutFlushbonading);
}
function callbackAddOrUpdateToOutFlushbonading(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (isNotEmpty(data.data)) {
                parent.layer.msg("保存成功",{icon:6,time:500},function () {
                    parent.dqfdssid=data.data;
                    parent.getToOutFlushbonadingList();
                    parent.getToOutFlushbonadingEttdList();
                    parent.layer.closeAll('iframe'); //关闭弹框
                });
            }
        }
    }else{
        parent.layer.msg(data.message,{icon: 5});
    }
}


//获取片头列表
function getptdjconst() {
    var url = getUrl_manage().getptdjconst;
    var ptdjconst = $("#getptdjconstBtn").val();
    var mustUpdateBool = false;
    if (ptdjconst == "更新片头列表") {
        mustUpdateBool = true;
    }
    var data={
        token:INIT_CLIENTKEY,
        param: {
            mustUpdateBool: mustUpdateBool,
            flushbonadingetinfossid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callptdjconst);
}
function callptdjconst(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != null) {
                $("#ptjson").val(data.data);
                $("#getptdjconstBtn").val("更新片头列表");
                layer.msg("获取列表成功",{icon: 6});
            }else{
                layer.msg("获取列表失败",{icon: 5});
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}


//集中存储管理中心，获取FTP上传配置
function getMiddFtp() {
    //使用模块
    var html='<form class="layui-form" style="margin-top: 20px;">\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>服务名</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="servicename" required  lay-verify="required" placeholder="请输入服务名" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">是否启用</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="enable" id="enable" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>本机设备ID</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="deviceid" required  lay-verify="required" placeholder="请输入本机设备ID" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">被动模式</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="pasvmode" id="pasvmode" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>服务器地址</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="serverip" required  lay-verify="required|setip" placeholder="请输入服务器地址" autocomplete="off" class="layui-input" onkeyup="this.value=value.replace(/[^\\d|.]/g,\'\');if(this.value==\'\')(this.value=\'\');">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>服务器端口</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="number" name="serverport" required  lay-verify="required|number" placeholder="请输入服务器端口" autocomplete="off" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>用户名</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="text" name="svrusr" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>密码</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="password" name="svrpwd" required  lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">是否自动重启</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="restart" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>心跳服务器地址</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="text" name="hreadbeatip" required  lay-verify="required|setip" placeholder="请输入心跳服务器地址" autocomplete="off" class="layui-input"  onkeyup="this.value=value.replace(/[^\\d|.]/g,\'\');if(this.value==\'\')(this.value=\'\');">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>流控限速(kb)</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="number" name="limit_speed" required  lay-verify="required|number" placeholder="请输入流控限速" autocomplete="off" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">过滤条件</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="search_filter" id="datetj" lay-verify="" placeholder="过滤该日期之前不文件上传" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">是否过滤</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="filter_enable" id="filter_enable" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>';


    layui.use(['form','laydate'], function() {
        var form = layui.form;
        var laydate = layui.laydate;
        var index = layer.open({
            type: 1,
            title: '集中存储管理中心',
            skin: 'layui-layer-rim', //加上边框
            area: ['800px', '580px'],
            btn: ['保存', '取消'],
            content: html,
            success: function (layero, index) {
                $(':focus').blur();
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromFTP').attr('lay-submit', '');//将按钮弄成能提交的
                laydate.render({
                    elem: '#datetj' //指定元素
                });
                form.render();
            },
            yes:function(index, layero){
                //自定义验证规则
                form.verify({
                    setip: function(value, item){ //value：表单的值、item：表单的DOM对象
                        if(''==value){
                            goaction = true;
                            return "设备IP不能为空";
                        }
                        if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(value))){
                            goaction = true;
                            return '请输入一个正确的IP地址';
                        }
                        goaction = false;
                    }
                });
                //监听提交
                form.on('submit(fromFTP)', function(data){
                    // console.log(data);

                    var fromFTP = JSON.stringify(data.field);
                    if(!isNotEmpty(goaction) && fromFTPStr != fromFTP) {
                        fromFTPStr = fromFTP;
                        goaction = true;

                        if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(data.field.serverip))){
                            layer.msg("服务器地址IP，不是一个正确的IP",{icon: 5});
                            return false;
                        }
                        if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(data.field.hreadbeatip))){
                            layer.msg("心跳服务器地址，不是一个正确的IP",{icon: 5});
                            return false;
                        }

                        setMiddFtp();
                    }
                    return false;
                });
            },
            btn2:function(index, layero){
                layer.close(index);
            }
        });


    });

    var url = getUrl_manage().getMiddleware_FTP;
    var data={
        token:INIT_CLIENTKEY,
        param: {
            flushbonadingetinfossid:ssid
        }
    };
    ajaxSubmitByJson(url,data,callgetMiddFtp);
}
function callgetMiddFtp(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != null) {

                var middFTP = data.data;

                $("input[name='servicename']").val(middFTP.servicename);
                $("#enable").prop("checked", middFTP.enable == 1);
                $("input[name='deviceid']").val(middFTP.deviceid);
                $("#pasvmode").prop("checked", middFTP.passvmode == 1);
                $("input[name='serverip']").val(middFTP.serverip);
                $("input[name='serverport']").val(middFTP.serviceport);
                $("input[name='svrusr']").val(middFTP.user);
                $("input[name='svrpwd']").val(middFTP.pass);
                $("input[name='hreadbeatip']").val(middFTP.hreadbeatip);
                $("input[name='limit_speed']").val(middFTP.limit_speed);
                $("input[name='search_filter']").attr("value", middFTP.search_filter);
                $("#filter_enable").prop("checked", middFTP.filter_enable == 1);

                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                });

            }else{
                layer.msg("获取列表失败",{icon: 5});
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}


function setMiddFtp() {
    var url = getUrl_manage().setMiddleware_FTP;
    var servicename=$("input[name='servicename']").val();
    var enable=$("#enable").prop("checked")==true?1:0;
    var deviceid=$("input[name='deviceid']").val();
    var pasvmode=$("#pasvmode").prop("checked")==true?1:0;
    var serverip=$("input[name='serverip']").val();
    var serverport=$("input[name='serverport']").val();
    var svrusr=$("input[name='svrusr']").val();
    var svrpwd=$("input[name='svrpwd']").val();
    var restart=$("#restart").prop("checked")==true?1:0;
    var hreadbeatip=$("input[name='hreadbeatip']").val();
    var limit_speed=$("input[name='limit_speed']").val();
    var search_filter=$("input[name='search_filter']").val();
    var filter_enable=$("#filter_enable").prop("checked")==true?1:0;


    var data = {
        token:INIT_CLIENTKEY,
        param: {
            servicename: servicename,
            enable: enable.toString(),
            deviceid: deviceid,
            pasvmode: pasvmode.toString(),
            serverip: serverip,
            serverport: serverport,
            svrusr: svrusr,
            svrpwd: svrpwd,
            restart: restart.toString(),
            hreadbeatip: hreadbeatip,
            limit_speed: limit_speed,
            search_filter: search_filter,
            filter_enable: filter_enable.toString(),
            flushbonadingetinfossid:ssid
        }
    };

    ajaxSubmitByJson(url, data, callSetMiddFtp);
}
function callSetMiddFtp(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        layer.msg("操作成功",{icon: 6});
        setTimeout("layer.closeAll();",1500);
    }else{
        layer.msg(data.message,{icon: 5});
    }
    goaction = false;
}






layui.use(['laypage', 'form', 'layer', 'layedit', 'laydate', 'table'], function () {
    var $ = layui.$ //由于layer弹层依赖jQuery，所以可以直接得到
        , form = layui.form
        , layer = layui.layer;

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

    form.render();
    form.on('submit(permissionSubmit)', function (data) {
        AddOrUpdateToOutFlushbonading();
    });
});

