package com.avst.meetingcontrol.outside.interfacetoout.conf;

import com.avst.meetingcontrol.common.conf.SSType;
import com.avst.meetingcontrol.common.util.*;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.ss.SaveFile_localParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.PhForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.PhDataParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.PhForMCCache_oneParam;

import java.util.Date;
import java.util.List;

/**
 * 测谎数据新增线程
 */
public class MC_Ph_AddThread extends Thread{

    public static Object bool=new Object();

    private String staticpath="ph/";//暂时不写到外面去

    private List<PhDataParam_toout> phDataParam_tooutList;//等待新增的数据

    private String mtssid;//会议ssid

    private String phssid;//测谎仪s服务sid

    private String iid;//存储的唯一标识

    private int addcount=0;//当前添加次数

    private String filepath;

    public MC_Ph_AddThread( List<PhDataParam_toout> phDataParam_tooutList, String mtssid, String phssid,String iid,int addcount,String filepath) {
        this.phDataParam_tooutList = phDataParam_tooutList;
        this.mtssid = mtssid;
        this.phssid = phssid;
        this.iid=iid;
        this.addcount=addcount;
        this.filepath=filepath;
    }

    @Override
    public void run() {

            //请求测谎仪服务获取数据
            LogUtil.intoLog(this.getClass(),phssid+"------phssid MC_Ph_AddThread mtssid:"+mtssid);
            try {

                if(null==phDataParam_tooutList||phDataParam_tooutList.size() ==0){
                    LogUtil.intoLog(4,this.getClass(),"----MC_Ph_AddThread--phDataParam_tooutList数据为空");
                    return ;
                }

                //判断是不是第一次新增
                if(addcount==0){

                    LogUtil.intoLog(1,this.getClass(),"--MC_Ph_AddThread----开始第一次写数据");
                    //获取iid，存储路径，新增/添加

                    EquipmentControl equipmentControl=SpringUtil.getBean(EquipmentControl.class);
                    if(null==equipmentControl){
                        LogUtil.intoLog(4,this.getClass(),"---MC_Ph_AddThread---EquipmentControl 获取失败，大问题");
                        return ;
                    }

                    String filename=iid+".txt";
                    String sourseRelativePath= OpenUtil.createpath_fileByBasepath(staticpath)+filename;

                    SaveFile_localParam param=new SaveFile_localParam();
                    param.setIid(iid);
                    param.setSourseRelativePath(sourseRelativePath);
                    param.setSsType(SSType.AVST);
                    RResult result=equipmentControl.saveFile_local(param);
                    if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                        filepath=result.getData().toString();
                        String data="";
                        writeFile(phDataParam_tooutList,data);
                    }else{
                        LogUtil.intoLog(4,this.getClass(),"-----MC_Ph_AddThread-equipmentControl.saveFile_local 失败");
                    }
                }else{
                    LogUtil.intoLog(1,this.getClass(),"----MC_Ph_AddThread--已经写入数据次数addcount："+addcount);
                    //直接写入
                    String data=ReadWriteFile.readTxtFileToStr(filepath);
                    data=data.trim()+"\r\n";//换行追加
                    writeFile(phDataParam_tooutList,data);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        LogUtil.intoLog(this.getClass(),"MC_Ph_AddThread 出来了--phssid:"+phssid+"--mtssid:"+mtssid+"--"+new Date());
    }


    private boolean writeFile(List<PhDataParam_toout> phDataParam_tooutList,String data){

        for(PhDataParam_toout out:phDataParam_tooutList){
            try {
                data+=out.getReqTime()+";"+JacksonUtil.objebtToString(out.getT())+"\r\n";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        synchronized (bool) {

            ReadWriteFile.writeTxtFile(data, filepath);
        }

        PhForMCCache.updateAddcount(addcount,mtssid,phssid,filepath);//更新添加数

        return false;
    }
}
