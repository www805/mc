package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.outside.interfacetoout.vo.getTDByMTListVO;
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
public interface Avstmt_modelMapper extends BaseMapper<Avstmt_model> {


    @Select("SELECT " +
            "m.EXPLAIN, " +
            "md.grade, " +
            "md.fdssid, " +
            "md.polygraphssid, " +
            "md.asrssid " +
            "FROM " +
            "avstmt_model m " +
            "LEFT JOIN avstmt_modeltd md ON m.ssid = md.mtmodelssid")
    public List<getTDByMTListVO> getTDByMTList();

}
