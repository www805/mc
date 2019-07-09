
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

function callbacklogining(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        var url=getUrl_manage().main;
        window.location.href=url;
    }else{
        layer.msg(data.message, {icon: 2});
    }
}

function logout(){
    var url=getUrl_manage().logout;
    ajaxSubmit(url,null,callLogout);
}

function callLogout(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        var url=getUrl_manage().gotologin;
        window.location.href=url;
    }else{
        layer.msg(data.message, {time: 5000, icon:6});
    }
}


