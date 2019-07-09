
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
    window.location.href=getUrl_manage().toAvstmt_modeltdAddOrUpdate+"?ssid="+ssid;
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
    });
})