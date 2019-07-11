var ipath;


function setPath() {
    var hostport=document.location.host;
	ipath = 'http://'+hostport;
//    ipath = 'https://'+hostport;//HTTPS加密协议
};
function getPath() {
	
	if (ipath == "" || ipath == null || ipath == undefined || ipath == 'null' || ipath == 'Null' || ipath == 'NULL') {
		setPath();
	}
	
	return ipath;
}


function getUrl_manage() {

	var basepath="/mc/";
	var pagepath="/page/";

	return {
		//后台请求
		gotologin:getPath()+basepath+"main/gotologin",
		logining:getPath()+basepath+"main/logining",
		main:getPath()+basepath+"main/main",
		logout:getPath()+basepath+"main/logout",

        //首页
        getHome:getPath()+basepath+"main/getHome",//获取首页数据

		//模板
        getAvstmt_modelList:getPath()+basepath+"avstmt_model/getAvstmt_modelList",//获取模板page列表
        getAvstmt_modelByssid:getPath()+basepath+"avstmt_model/getAvstmt_modelByssid",//获取的单个模板
        updateAvstmt_model:getPath()+basepath+"avstmt_model/updateAvstmt_model",//修改模板
        addAvstmt_model:getPath()+basepath+"avstmt_model/addAvstmt_model",//添加模板

		//模板通道
        getAvstmt_modeltdList:getPath()+basepath+"avstmt_modeltd/getAvstmt_modeltdList",//获取模板通道page列表
        getAvstmt_modeltdByssid:getPath()+basepath+"avstmt_modeltd/getAvstmt_modeltdByssid",//获取的单个模板通道
        updateAvstmt_modeltd:getPath()+basepath+"avstmt_modeltd/updateAvstmt_modeltd",//修改模板通道
        addAvstmt_modeltd:getPath()+basepath+"avstmt_modeltd/addAvstmt_modeltd",//添加模板通道
        delAvstmt_modeltd:getPath()+basepath+"avstmt_modeltd/delAvstmt_modeltd",//删除模板通道

		//会议
        getBase_mtinfoList:getPath()+basepath+"base_mtinfo/getBase_mtinfoList",//获取会议page列表

        //会议通道
        getAvstmt_tduserList:getPath()+basepath+"avstmt_tduser/getAvstmt_tduserList",//获取会议通道page列表

		//页面跳转
        toBase_mtinfoList:getPath()+pagepath+"mtpage/toBase_mtinfoList",//跳转会议页
        toAvstmt_modelList:getPath()+pagepath+"mtpage/toAvstmt_modelList",//跳转会议模板页
        toAvstmt_modeltdList:getPath()+pagepath+"mtpage/toAvstmt_modeltdList",//跳转会议模板通道
        toAvstmt_modelAddOrUpdate:getPath()+pagepath+"mtpage/toAvstmt_modelAddOrUpdate",//跳转会议模板编辑页
        toAvstmt_modeltdAddOrUpdate:getPath()+pagepath+"mtpage/toAvstmt_modeltdAddOrUpdate",//跳转会议模板通道编辑页









        /*--------------------------------以下为：提供给会议后台显示的接口---start--------------------------------------*/
        //审讯设备
        getToOutFlushbonadingList:getPath()+basepath+"flushbonading/getToOutFlushbonadingList",
        getToOutFlushbonadingById:getPath()+basepath+"flushbonading/getToOutFlushbonadingById",
        addToOutFlushbonading:getPath()+basepath+"flushbonading/addToOutFlushbonading",
        updateToOutFlushbonading:getPath()+basepath+"flushbonading/updateToOutFlushbonading",
        //测谎仪
        getToOutPolygraphList:getPath()+basepath+"polygraph/getToOutPolygraphList",
        getToOutPolygraphById:getPath()+basepath+"polygraph/getToOutPolygraphById",
        addToOutPolygraph:getPath()+basepath+"polygraph/addToOutPolygraph",
        updateToOutPolygraph:getPath()+basepath+"polygraph/updateToOutPolygraph",

        //语音识别
        getToOutAsrList:getPath()+basepath+"asr/getToOutAsrList",
        getToOutAsrById:getPath()+basepath+"asr/getToOutAsrById",
        addToOutAsr:getPath()+basepath+"asr/addToOutAsr",
        updateToOutAsr:getPath()+basepath+"asr/updateToOutAsr",

        //设备通道
        getToOutFlushbonadingEttdList:getPath()+basepath+"flushbonadingettd/getToOutFlushbonadingEttdList",
        getToOutFlushbonadingEttdById:getPath()+basepath+"flushbonadingettd/getToOutFlushbonadingEttdById",
        addToOutFlushbonadingEttd:getPath()+basepath+"flushbonadingettd/addToOutFlushbonadingEttd",
        updateToOutFlushbonadingEttd:getPath()+basepath+"flushbonadingettd/updateToOutFlushbonadingEttd",

        //tts
        getToOutTtsEtinfoList:getPath()+basepath+"tts/getToOutTtsEtinfoList",
        getToOutTtsEtinfoById:getPath()+basepath+"tts/getToOutTtsEtinfoById",
        addToOutTtsEtinfo:getPath()+basepath+"tts/addToOutTtsEtinfo",
        updateToOutTtsEtinfo:getPath()+basepath+"tts/updateToOutTtsEtinfo",

        /*--------------------------------以下为：提供给会议后台显示的接口---end--------------------------------------*/
	};
}


