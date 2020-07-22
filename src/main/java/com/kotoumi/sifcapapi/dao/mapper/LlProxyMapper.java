package com.kotoumi.sifcapapi.dao.mapper;

import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
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
     * @param keyword 搜索关键词
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
     * @param keyword 搜索关键词
     * @return 演唱会总数
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

    /**
     * 获取用户卡组信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @return 卡组信息
     */
    List<Unit> searchUnits(@Param("userId") int userId,
                           @Param("start") int start,
                           @Param("limit") int limit);

    /**
     * 演唱卡组总数
     * @param userId 用户ID
     * @return 卡组总数
     */
    int countUnits(@Param("userId") int userId);

    /**
     * 获取llhelper用户卡组信息
     * @param userId 用户ID
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> exportUnits(@Param("userId") int userId);

    /**
     * 获取用户招募信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param type 招募类型，0/不存在表示正常招募，1表示普通生招募
     * @return 招募信息
     */
    List<SecretBoxLog> searchSecretBoxLog(@Param("userId") int userId,
                                          @Param("start") int start,
                                          @Param("limit") int limit,
                                          @Param("type") Integer type);

    /**
     * 获取用户招募信息总数
     * @param userId 用户ID
     * @param type 招募类型，0/不存在表示正常招募，1表示普通生招募
     * @return 招募信息总数
     */
    int countSecretBoxLog(@Param("userId") int userId,
                          @Param("type") Integer type);

}
