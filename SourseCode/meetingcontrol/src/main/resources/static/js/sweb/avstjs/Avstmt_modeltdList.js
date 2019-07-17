
var createtime=null;
function getAvstmt_modeltdList_init(currPage,pageSize) {
    var url=getUrl_manage().getAvstmt_modeltdList;
    var usepolygraph =$("#usepolygraph").val();
    var useasr =$("#useasr").val();
    var grade =$("#grade").val();

    var startdate=null;
    var enddate=null;

    if (isNotEmpty(createtime)){
        var arr = createtime.split("~");
        startdate=arr[0].trim();
        enddate=arr[1].trim();
    }

    var data={
        usepolygraph:usepolygraph,
        useasr:useasr,
        grade:grade,
        startdate:startdate,
        enddate:enddate,
        mtmodelssid:mtmodelssid,
        currPage:currPage,
        pageSize:pageSize
    };
    ajaxSubmit(url,data,callbackgetAvstmt_modeltdList);
}

function getAvstmt_modeltdList(usepolygraph,useasr,grade,startdate,enddate,mtmodelssid,currPage,pageSize){
    var url=getUrl_manage().getAvstmt_modeltdList;
    var data={
        usepolygraph:usepolygraph,
        useasr:useasr,
        grade:grade,
        startdate:startdate,
        enddate:enddate,
        mtmodelssid:mtmodelssid,
        currPage:currPage,
        pageSize:pageSize
    };
    ajaxSubmit(url,data,callbackgetAvstmt_modeltdList);
}

//修改监听是否需要测谎状态
function updateUsepolygraphBool(ssid, shieldbool) {

    var url=getUrl_manage().updateUsepolygraphBool;
    var data = {
        token:"123",
        param:{
            ssid: ssid,
            shieldbool: shieldbool
        }
    };

    ajaxSubmitByJson(url,data,callupdateUsepolygraphBool);
}

//修改监听是否语音识别开关状态
function updateUseasrBool(ssid, shieldbool) {

    var url=getUrl_manage().updateUseasrBool;
    var data = {
        token:"123",
        param:{
            ssid: ssid,
            shieldbool: shieldbool
        }
    };

    ajaxSubmitByJson(url,data,callupdateUsepolygraphBool);
}

//监听是否需要测谎状态返回值
function callupdateUsepolygraphBool(data) {

    if(null!=data&&data.actioncode=='SUCCESS'){

        if(data.data == 1){
            layer.msg("操作成功", {icon: 1});
        }else{
            layer.msg("操作失败",{icon: 1});
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callbackgetAvstmt_modeltdList(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            pageshow(data);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}


/**
 * 局部刷新
 */
function getAvstmt_modeltdListByParam(){
    var len=arguments.length;
    if(len==0){
        var currPage=1;
        var pageSize=3;//测试
        getAvstmt_modeltdList_init(currPage,pageSize);
    }else if (len==2){
        getAvstmt_modeltdList('',arguments[0],arguments[1]);
    }else if(len>2){
        getAvstmt_modeltdList(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5],arguments[6],arguments[7]);
    }

}


function showpagetohtml(){
    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;


        var usepolygraph=pageparam.usepolygraph;
        var useasr=pageparam.useasr;
        var grade=pageparam.grade;
        var startdate=pageparam.startdate;
        var enddate=pageparam.enddate;
        var mtmodelssid=pageparam.mtmodelssid;


        var arrparam=new Array();
        arrparam[0]=usepolygraph;
        arrparam[1]=useasr;
        arrparam[2]=grade;
        arrparam[3]=startdate;
        arrparam[4]=enddate;
        arrparam[5]=mtmodelssid;

        showpage("paging",arrparam,'getAvstmt_modeltdListByParam',currPage,pageCount,pageSize);
    }
}

function toAvstmt_modeltdAddOrUpdate(ssid){
    if (isNotEmpty(mtmodelssid)){
        window.location.href=getUrl_manage().toAvstmt_modeltdAddOrUpdate+"?ssid="+ssid+"&mtmodelssid="+mtmodelssid;
    } else{
        layer.msg("会议模板错误")
    }
}

function delAvstmt_modeltd(ssid){
    if (isNotEmpty(ssid)){
        layer.confirm('确定要删除该模板通道吗', {
            btn: ['确认','取消'], //按钮
            shade: [0.1,'#fff'], //不显示遮罩
        }, function(index){
            var url=getUrl_manage().delAvstmt_modeltd;
            var data={
                ssid:ssid
            };
            ajaxSubmit(url,data,callbackdelAvstmt_modeltd);
            layer.close(index);
        }, function(index){
            layer.close(index);
        });
    } else {
        layer.msg("会议模板错误")
    }
}

function callbackdelAvstmt_modeltd(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            layer.msg("删除成功",{time:500,icon:1},function () {
                getAvstmt_modeltdListByParam();
            })
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}


$(function () {

    layui.use(['form'], function(){
        var form = layui.form;
        //使用模块

        form.on('select(usepolygraph_filter)', function(data){
            getAvstmt_modeltdListByParam();
        });
        form.on('select(useasr_filter)', function(data){
            getAvstmt_modeltdListByParam();
        });
        form.on('select(grade_filter)', function(data){
            getAvstmt_modeltdListByParam();
        });

        //监听是否需要测谎开关
        form.on('switch(switchUsepolygraph)', function(data){
            var shieldbool = -1;
            if(this.checked){
                shieldbool = 1;
            }
            updateUsepolygraphBool(data.value, shieldbool);
        });

        //监听是否语音识别开关
        form.on('switch(switchUseasr)', function(data){
            var shieldbool = -1;
            if(this.checked){
                shieldbool = 1;
            }
            updateUseasrBool(data.value, shieldbool);
        });

    });
})