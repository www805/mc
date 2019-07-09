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

		//会议
        getBase_mtinfoList:getPath()+basepath+"base_mtinfo/getBase_mtinfoList",//获取会议page列表



		//页面跳转
        toBase_mtinfoList:getPath()+pagepath+"mtpage/toBase_mtinfoList",//跳转会议页
        toAvstmt_modelList:getPath()+pagepath+"mtpage/toAvstmt_modelList",//跳转会议模板页
        toAvstmt_modeltdList:getPath()+pagepath+"mtpage/toAvstmt_modeltdList",//跳转会议模板通道
        toAvstmt_modelAddOrUpdate:getPath()+pagepath+"mtpage/toAvstmt_modelAddOrUpdate",//跳转会议模板编辑页
        toAvstmt_modeltdAddOrUpdate:getPath()+pagepath+"mtpage/toAvstmt_modeltdAddOrUpdate",//跳转会议模板通道编辑页
	};
}


