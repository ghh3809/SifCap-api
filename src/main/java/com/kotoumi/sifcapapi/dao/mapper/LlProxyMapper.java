package com.kotoumi.sifcapapi.dao.mapper;

import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.service.Deck;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author guohaohao
 */
public interface LlProxyMapper {

    /**
     * 根据关键字寻找用户
     * @param keyword UID/ID/昵称
     * @param limit limit数
     * @return 用户信息列表
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
     * 查询演唱会信息
     * @param id 演唱会ID
     * @return 演唱会信息
     */
    Live findLive(@Param("id") long id);

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
     * 根据unitId列表寻找卡牌信息
     * @param unitList unitId列表
     * @return 卡牌信息
     */
    @MapKey("unitId")
    Map<Integer, Unit> findUnits(@Param("unitList") List<Integer> unitList);

    /**
     * 根据unitOwningUserId列表寻找卡牌信息
     * @param userId 用户ID
     * @param unitList unitOwningList列表
     * @return 卡牌信息
     */
    @MapKey("unitOwningUserId")
    Map<Long, Unit> findUnitsByOwningIds(@Param("userId") Integer userId,
                                         @Param("unitList") List<Long> unitList);

    /**
     * 获取用户卡组信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @return 卡组信息
     */
    List<Unit> searchUnits(@Param("userId") int userId,
                           @Param("start") int start,
                           @Param("limit") int limit,
                           @Param("ssr") Integer ssr,
                           @Param("sr") Integer sr,
                           @Param("back") Integer back);

    /**
     * 演唱卡组总数
     * @param userId 用户ID
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @return 卡组总数
     */
    int countUnits(@Param("userId") int userId,
                   @Param("ssr") Integer ssr,
                   @Param("sr") Integer sr,
                   @Param("back") Integer back);

    /**
     * 获取llhelper用户卡组信息
     * @param userId 用户ID
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> exportUnits(@Param("userId") int userId,
                                   @Param("ssr") Integer ssr,
                                   @Param("sr") Integer sr,
                                   @Param("back") Integer back);

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

    /**
     * 获取用户队伍信息
     * @param userId 用户ID
     * @param unitDeckId 队伍ID
     * @return 队伍信息
     */
    List<Deck> findDecks(@Param("userId") int userId,
                         @Param("unitDeckId") Integer unitDeckId);

}
