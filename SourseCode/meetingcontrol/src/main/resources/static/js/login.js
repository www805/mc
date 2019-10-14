
function logining(){
    var url=getUrl_manage().logining;
    var loginaccount =$('input[name="loginaccount"]').val();
    var password =$('input[name="password"]').val();
    var data={
        loginaccount:loginaccount,
        password:password
    };
    ajaxSubmit(url,data,callbacklogining);

}

function logout(){
    var url=getUrl_manage().logout;
    ajaxSubmit(url,null,callLogout);
}

function getNavList() {
    var url=getUrl_manage().getNavList;
    ajaxSubmitByJson(url,null,callgetNavList);
}

function callbacklogining(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        var url=getUrl_manage().main;
        window.location.href=url;
    }else{
        layer.msg(data.message, {icon: 2,offset: 't'});
    }
}

function callgetNavList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){

        if (isNotEmpty(data.data.data)) {
            var appCache = data.data;

            if (isNotEmpty(appCache.data.title)) {
                //页头
                var Top_Title = appCache.data.title;
                $("#topTitle").html("欢迎进入" + Top_Title);
                $("#spanTitle").html("欢迎进入" + Top_Title);
            }

            if (isNotEmpty(appCache.data.bottom)) {
                if (!isNotEmpty(appCache.data.bottom) || !isNotEmpty(appCache.data.bottom.name) || !isNotEmpty(appCache.data.bottom.declaration) ) {
                    return;
                }
                //页脚
                var bottom_html = "";
                var bottom_name = appCache.data.bottom.name;
                var bottom_declaration = appCache.data.bottom.declaration;
                var bottom_url = appCache.data.bottom.url;
                if(!isNotEmpty(bottom_url)){
                    bottom_url = "#";
                }

                if (isNotEmpty(appCache.data.bottom.image.src) && appCache.data.bottom.image.src != '/') {
                    $(".systemlogo").css({"background-image":"url(" + appCache.data.bottom.image.src + ")"});
                    if(isNotEmpty(appCache.data.bottom.image.width) && isNotEmpty(appCache.data.bottom.image.height)){
                        $(".systemlogo").css("background-size", appCache.data.bottom.image.width + "px " + appCache.data.bottom.image.height + 'px');
                    }
                }else{
                    $(".systemlogo").css({"background-image":"url(/uimaker/images/loginlogo-5aa2cf210dbf067bd57c42a470703719.png)"});
                }

                bottom_html = bottom_declaration + " <a href=\"" + bottom_url + "\">" + bottom_name + "</a>";
                $("#bottom_mian").html(bottom_html);

            }
            if (isNotEmpty(appCache.data.guidepageUrl)) {
                //设置引导页a标签
                $("#guidepage").attr("href", appCache.data.guidepageUrl);
            }
        }
        layui.use('element', function(){
            var element =  layui.element;
            element.render();
        });
    }else{
        layer.msg(data.message);
    }
}

function callLogout(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        var url=getUrl_manage().gotologin;
        window.location.href=url;
    }else{
        layer.msg(data.message, {time: 5000, icon:5});
    }
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if(r!=null)return  unescape(r[2]); return null;
}

