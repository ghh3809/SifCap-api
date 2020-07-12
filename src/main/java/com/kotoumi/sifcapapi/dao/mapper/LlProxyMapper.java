package com.kotoumi.sifcapapi.dao.mapper;

import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guohaohao
 */
public interface LlProxyMapper {

    /**
     * 根据关键字寻找用户
     * @param keyword UID/ID/昵称
     * @param limit limit数
     * @return 用户信息
     */
    List<User> searchUser(@Param("keyword") String keyword,
                          @Param("limit") int limit);

    /**
     * 根据uid寻找用户
     * @param userId 用户uid
     * @return 用户信息
     */
    User findUser(@Param("userId") int userId);

    /**
     * 搜索演唱会信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param setId live_setting_id
     * @param eventId 活动ID
     * @return 演唱会信息
     */
    List<Live> searchLive(@Param("userId") int userId,
                          @Param("start") int start,
                          @Param("limit") int limit,
                          @Param("setId") Integer setId,
                          @Param("eventId") Integer eventId,
                          @Param("keyword") String keyword);

    /**
     * 演唱会信息总数
     * @param userId 用户ID
     * @param setId live_setting_id
     * @param eventId 活动ID
     * @return 演唱会信息
     */
    int countLive(@Param("userId") int userId,
                  @Param("setId") Integer setId,
                  @Param("eventId") Integer eventId,
                  @Param("keyword") String keyword);

    /**
     * 根据unitId寻找卡牌
     * @param unitId unit id
     * @return 用户信息
     */
    Unit findUnit(@Param("unitId") int unitId);

}
