
function getHome() {
        var url=getUrl_manage().getHome;
        var data={
        };
        ajaxSubmitByJson(url,data,callbackgetHome);
}

function callbackgetHome(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        var data=data.data;
        if (isNotEmpty(data)){
            $("#fdcount").text(data.fdcount==null?0:data.fdcount);
            $("#phcount").text(data.phcount==null?0:data.phcount);
            $("#asrcount").text(data.asrcount==null?0:data.asrcount);
            $("#ttscount").text(data.ttscount==null?0:data.ttscount);

            $("#modelcount").text(data.modelcount==null?0:data.modelcount);
            $("#modeltdcount").text(data.modeltdcount==null?0:data.modeltdcount);

            $("#mtinfo_count").text(data.mtinfo_count==null?0:data.mtinfo_count);
            $("#mtinfo_initcount").text(data.mtinfo_initcount==null?0:data.mtinfo_initcount);
            $("#mtinfo_ingcount").text(data.mtinfo_ingcount==null?0:data.mtinfo_ingcount);
            $("#mtinfo_endtcount").text(data.mtinfo_endtcount==null?0:data.mtinfo_endtcount);
            $("#mtinfo_pausecount").text(data.mtinfo_pausecount==null?0:data.mtinfo_pausecount);
            $("#mtinfo_abnormalcount").text(data.mtinfo_abnormalcount==null?0:data.mtinfo_abnormalcount);

        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}