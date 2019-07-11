package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tduser;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_tduserAll;
import com.avst.meetingcontrol.web.vo.AvstmtTduserVO;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * InnoDB free: 38912 kB Mapper 接口
 * </p>
 *
 * @author Mht
 * @since 2019-05-13
 */
@Component
public interface Avstmt_tduserMapper extends BaseMapper<Avstmt_tduser> {


    @Select("select tu.*,at.asrserverssid asrserverssid ,at.filesavessid filesavessid,at.recordtime filesavessid," +
            "at.starttime starttime,at.startrecordtime startrecordtime,at.ssid asrtdssid,at.asrid asrid from avstmt_tduser tu " +
            "  left join avstmt_asrtd at on tu.ssid=at.mttduserssid " +
            "  where 1=1 ${ew.sqlSegment} ")
    public List<Avstmt_tduserAll> getAvstmt_tduserAll(@Param("ew") EntityWrapper ew);

    @Select("select * from avstmt_tduser" +
            "  where 1=1 ${ew.sqlSegment} ")
    public List<AvstmtTduserVO> selectListPageAll(Page page, @Param("ew") EntityWrapper ew);


}
