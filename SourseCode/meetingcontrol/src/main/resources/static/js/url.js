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

	return {
		//后台请求
		gotologin:getPath()+basepath+"main/gotologin",

		logining:getPath()+basepath+"main/logining",

		main:getPath()+basepath+"main/main",
		logout:getPath()+basepath+"main/logout",
	};
}

