//package com.aha.tech.model.mapper;
//
//import com.aha.tech.model.entity.MessageLogCompensationEntity;
//import com.aha.tech.model.entity.MessageLogEntity;
//import org.apache.ibatis.annotations.Param;
//import tk.mybatis.mapper.common.Mapper;
//
//import java.util.List;
//
//public interface MessageLogCompensationMapper extends Mapper<MessageLogCompensationEntity> {
//    /**
//     * 查询需要补偿的消息
//     *
//     * @param latestId
//     * @return
//     */
//    List<MessageLogCompensationEntity> selectCompensation(@Param("latestId") Long latestId);
//}
