var createtime=null;
var mtssid;

function getBase_avstmt_tduserList_init(currPage,pageSize) {
    var url=getUrl_manage().getAvstmt_tduserList;
    var username =$("#username").val();
    var usertype =$("#usertype").val();

    var createtime_startdate=null;
    var createtime_enddate=null;

    if (isNotEmpty(createtime)){
        var arr = createtime.split("~");
        createtime_startdate=arr[0].trim();
        createtime_enddate=arr[1].trim();
    }

    var data = {
        mtssid: mtssid,
        username: username,
        usertype: usertype,
        createtime_startdate: createtime_startdate,
        createtime_enddate: createtime_enddate,
        currPage: currPage,
        pageSize: pageSize
    };

    $("#wushuju").show();
    $("#pagelistview").hide();
    $("#wushuju").html("数据查询中，请稍后...");
    ajaxSubmit(url,data,callbackgetBase_avstmt_tduserList);
}

function getBase_avstmt_tduserList(username, usertype, createtime_startdate, createtime_enddate, currPage, pageSize) {
    var url = getUrl_manage().getAvstmt_tduserList;
    var data = {
        mtssid: mtssid,
        username: username,
        usertype: usertype,
        createtime_startdate: createtime_startdate,
        createtime_enddate: createtime_enddate,
        currPage: currPage,
        pageSize: pageSize
    };

    $("#wushuju").show();
    $("#pagelistview").hide();
    $("#wushuju").html("数据查询中，请稍后...");
    ajaxSubmit(url, data, callbackgetBase_avstmt_tduserList);
}


function callbackgetBase_avstmt_tduserList(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            $("#wushuju").hide();
            $("#pagelistview").show();
            pageshow(data);
        }else{
            $("#wushuju").show();
            $("#wushuju").html("暂无会议人员设备通道信息");
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

/**
 * 局部刷新
 */
function getBase_avstmt_tduserListByParam(){
    var len=arguments.length;
    if(len==0){
        var currPage=1;
        var pageSize=2;//测试
        getBase_avstmt_tduserList_init(currPage,pageSize);
    }else if (len==2){
        getBase_avstmt_tduserList('',arguments[0],arguments[1]);
    }else if(len>2){
        getBase_avstmt_tduserList(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
    }

}

function showpagetohtml(){
    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var username=pageparam.username;
        var usertype=pageparam.usertype;
        var createtime_startdate=pageparam.createtime_startdate;
        var createtime_enddate=pageparam.createtime_enddate;

        var arrparam=new Array();
        arrparam[0]=username;
        arrparam[1]=usertype;
        arrparam[2]=createtime_startdate;
        arrparam[3]=createtime_enddate;

        showpage("paging",arrparam,'getBase_avstmt_tduserListByParam',currPage,pageCount,pageSize);
    }
}


$(function () {
    mtssid = getQueryString("mtssid");

    // console.log("=============mtssid=============");
    // console.log(mtssid);

    getBase_avstmt_tduserListByParam();
    layui.use(['form'], function(){
        var form = layui.form;
        //使用模块

        form.on('select(usertype_filter)', function(data){
            getBase_avstmt_tduserListByParam();
        });

    });
})

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}