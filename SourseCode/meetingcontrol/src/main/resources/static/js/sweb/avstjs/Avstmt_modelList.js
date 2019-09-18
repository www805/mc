
var createtime=null;
function getAvstmt_modelList_init(currPage,pageSize) {
    var url=getUrl_manage().getAvstmt_modelList;
    var meetingtype =$("#meetingtype").val();
    var opened =$("#opened").val();
    var userecord =$("#userecord").val();

    var explain=$("#explain").val();
    var startdate=null;
    var enddate=null;

    if (isNotEmpty(createtime)){
        var arr = createtime.split("~");
        startdate=arr[0].trim();
        enddate=arr[1].trim();
    }

    var data={
        meetingtype:meetingtype,
        opened:opened,
        userecord:userecord,
        startdate:startdate,
        enddate:enddate,
        explain:explain,
        currPage:currPage,
        pageSize:pageSize
    };
    ajaxSubmit(url,data,callbackgetAvstmt_modelList);
}

function getAvstmt_modelList(meetingtype,opened,userecord,startdate,enddate,explain,currPage,pageSize){
    var url=getUrl_manage().getAvstmt_modelList;
    var data={
        meetingtype:meetingtype,
        opened:opened,
        userecord:userecord,
        startdate:startdate,
        enddate:enddate,
        explain:explain,
        currPage:currPage,
        pageSize:pageSize
    };
    ajaxSubmit(url,data,callbackgetAvstmt_modelList);
}


function callbackgetAvstmt_modelList(data){
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
function getAvstmt_modelListByParam(){
    var len=arguments.length;
    if(len==0){
        var currPage=1;
        var pageSize=10;//测试
        getAvstmt_modelList_init(currPage,pageSize);
    }else if (len==2){
        getAvstmt_modelList('',arguments[0],arguments[1]);
    }else if(len>2){
        getAvstmt_modelList(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5],arguments[6],arguments[7]);
    }

}


function showpagetohtml(){
    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;


        var meetingtype=pageparam.meetingtype;
        var opened=pageparam.opened;
        var userecord=pageparam.userecord;
        var startdate=pageparam.startdate;
        var enddate=pageparam.enddate;
        var explain=pageparam.explain;


        var arrparam=new Array();
        arrparam[0]=meetingtype;
        arrparam[1]=opened;
        arrparam[2]=userecord;
        arrparam[3]=startdate;
        arrparam[4]=enddate;
        arrparam[5]=explain;
        showpage("paging",arrparam,'getAvstmt_modelListByParam',currPage,pageCount,pageSize);
    }
}

function toAvstmt_modelAddOrUpdate(ssid){
    window.location.href=getUrl_manage().toAvstmt_modelAddOrUpdate+"?ssid="+ssid;
}

function toAvstmt_modeltdList(mtmodelssid){
    window.location.href=getUrl_manage().toAvstmt_modeltdList+"?mtmodelssid="+mtmodelssid;
}



$(function () {
    getAvstmt_modelListByParam();

    layui.use(['form'], function(){
        var form = layui.form;
        //使用模块

        form.on('select(meetingtype_filter)', function(data){
            getAvstmt_modelListByParam();
        });
        form.on('select(opened_filter)', function(data){
            getAvstmt_modelListByParam();
        });
        form.on('select(userecord_filter)', function(data){
            getAvstmt_modelListByParam();
        });

    });
})