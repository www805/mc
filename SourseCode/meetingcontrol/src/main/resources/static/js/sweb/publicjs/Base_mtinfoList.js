var recordstarttime=null;
var recordendtime=null;
function getBase_mtinfoList_init(currPage,pageSize) {
    var url=getUrl_manage().getBase_mtinfoList;
    var meetingtype =$("#meetingtype").val();
    var mtstate =$("#mtstate").val();
    var opened =$("#opened").val();
    var userecord =$("#userecord").val();

    var recordstarttime_startdate=null;
    var recordstarttime_enddate=null;
    var recordendtime_startdate=null;
    var recordendtime_enddate=null;

    if (isNotEmpty(recordstarttime)){
        var arr = recordstarttime.split("~");
        recordstarttime_startdate=arr[0].trim();
        recordstarttime_enddate=arr[1].trim();
    }
    if (isNotEmpty(recordendtime)){
        var arr = recordendtime.split("~");
        recordendtime_startdate=arr[0].trim();
        recordendtime_enddate=arr[1].trim();
    }

    var data={
        meetingtype:meetingtype,
        mtstate:mtstate,
        opened:opened,
        userecord:userecord,
        recordstarttime_startdate:recordstarttime_startdate,
        recordstarttime_enddate:recordstarttime_enddate,
        recordendtime_startdate:recordendtime_startdate,
        recordendtime_enddate:recordendtime_enddate,
        currPage:currPage,
        pageSize:pageSize
    };
    ajaxSubmit(url,data,callbackgetBase_mtinfoList);
}

function getBase_mtinfoList(meetingtype,mtstate,opened,userecord,recordstarttime_startdate,recordstarttime_enddate,recordendtime_startdate,recordendtime_enddate,currPage,pageSize){
    var url=getUrl_manage().getBase_mtinfoList;
    var data={
        meetingtype:meetingtype,
        mtstate:mtstate,
        opened:opened,
        userecord:userecord,
        recordstarttime_startdate:recordstarttime_startdate,
        recordstarttime_enddate:recordstarttime_enddate,
        recordendtime_startdate:recordendtime_startdate,
        recordendtime_enddate:recordendtime_enddate,
        currPage:currPage,
        pageSize:pageSize
    };
    ajaxSubmit(url,data,callbackgetBase_mtinfoList);
}


function callbackgetBase_mtinfoList(data){
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
function getBase_mtinfoListByParam(){
    var len=arguments.length;
    if(len==0){
        var currPage=1;
        var pageSize=3;//测试
        getBase_mtinfoList_init(currPage,pageSize);
    }else if (len==2){
        getBase_mtinfoList('',arguments[0],arguments[1]);
    }else if(len>2){
        getBase_mtinfoList(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5],arguments[6],arguments[7],arguments[8],arguments[9]);
    }

}

function showpagetohtml(){
    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;


        var meetingtype=pageparam.meetingtype;
        var mtstate=pageparam.mtstate;
        var opened=pageparam.opened;
        var userecord=pageparam.userecord;
        var recordstarttime_startdate=pageparam.recordstarttime_startdate;
        var recordstarttime_enddate=pageparam.recordstarttime_enddate;
        var recordendtime_startdate=pageparam.recordendtime_startdate;
        var recordendtime_enddate=pageparam.recordendtime_enddate;

        var arrparam=new Array();
        arrparam[0]=meetingtype;
        arrparam[1]=mtstate;
        arrparam[2]=opened;
        arrparam[3]=userecord;
        arrparam[4]=recordstarttime_startdate;
        arrparam[5]=recordstarttime_enddate;
        arrparam[6]=recordendtime_startdate;
        arrparam[7]=recordendtime_enddate;

        showpage("paging",arrparam,'getBase_mtinfoListByParam',currPage,pageCount,pageSize);
    }
}


$(function () {
    getBase_mtinfoListByParam();
    layui.use(['form'], function(){
        var form = layui.form;
        //使用模块

        form.on('select(meetingtype_filter)', function(data){
            getBase_mtinfoListByParam();
        });
        form.on('select(mtstate_filter)', function(data){
            getBase_mtinfoListByParam();
        });
        form.on('select(opened_filter)', function(data){
            getBase_mtinfoListByParam();
        });
        form.on('select(userecord_filter)', function(data){
            getBase_mtinfoListByParam();
        });
    });
})