package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;

/**
 * 模板通道的全部信息
 */
public class Avstmt_modeltdAll extends Avstmt_modeltd {
    //设备信息
    private String fdexplain;//审讯设备说明

    private String asrexplain;//语音识别说明

    private String phexplain;//测谎仪说明

    private String tdexplain;//设备通道说明

    public String getFdexplain() {
        return fdexplain;
    }

    public void setFdexplain(String fdexplain) {
        this.fdexplain = fdexplain;
    }

    public String getAsrexplain() {
        return asrexplain;
    }

    public void setAsrexplain(String asrexplain) {
        this.asrexplain = asrexplain;
    }

    public String getPhexplain() {
        return phexplain;
    }

    public void setPhexplain(String phexplain) {
        this.phexplain = phexplain;
    }

    public String getTdexplain() {
        return tdexplain;
    }

    public void setTdexplain(String tdexplain) {
        this.tdexplain = tdexplain;
    }
}
