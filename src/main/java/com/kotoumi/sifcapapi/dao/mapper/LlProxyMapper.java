package com.kotoumi.sifcapapi.dao.mapper;

import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.service.AddType;
import com.kotoumi.sifcapapi.model.vo.service.Award;
import com.kotoumi.sifcapapi.model.vo.service.Background;
import com.kotoumi.sifcapapi.model.vo.service.Deck;
import com.kotoumi.sifcapapi.model.vo.service.DuelLiveBox;
import com.kotoumi.sifcapapi.model.vo.service.EffortBox;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.LpRecovery;
import com.kotoumi.sifcapapi.model.vo.service.LpRecoverySummary;
import com.kotoumi.sifcapapi.model.vo.service.RecoveryItem;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.UnitRemovableSkill;
import com.kotoumi.sifcapapi.model.vo.service.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author guohaohao
 */
public interface LlProxyMapper {

    /**
     * 根据关键字寻找用户
     * @param keyword UID/ID/昵称
     * @param limit limit数
     * @param lang 数据语言
     * @return 用户信息列表
     */
    List<User> searchUser(@Param("keyword") String keyword,
                          @Param("limit") int limit,
                          @Param("lang") String lang);

    /**
     * 根据uid寻找用户
     * @param userId 用户uid
     * @param lang 数据语言
     * @return 用户信息
     */
    User findUser(@Param("userId") int userId,
                  @Param("lang") String lang);

    /**
     * 搜索演唱会信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param setId live_setting_id
     * @param eventId 活动ID
     * @param keyword 搜索关键词
     * @param isDuel 是否是百人协力
     * @param lang 数据语言
     * @return 演唱会信息
     */
    List<Live> searchLive(@Param("userId") int userId,
                          @Param("start") int start,
                          @Param("limit") int limit,
                          @Param("setId") Integer setId,
                          @Param("eventId") Integer eventId,
                          @Param("keyword") String keyword,
                          @Param("isDuel") Integer isDuel,
                          @Param("lang") String lang);

    /**
     * 查询演唱会信息
     * @param id 演唱会ID
     * @param lang 数据语言
     * @return 演唱会信息
     */
    Live findLive(@Param("id") long id,
                  @Param("lang") String lang);

    /**
     * 演唱会信息总数
     * @param userId 用户ID
     * @param setId live_setting_id
     * @param eventId 活动ID
     * @param keyword 搜索关键词
     * @param isDuel 是否是百人协力
     * @param lang 数据语言
     * @return 演唱会总数
     */
    int countLive(@Param("userId") int userId,
                  @Param("setId") Integer setId,
                  @Param("eventId") Integer eventId,
                  @Param("keyword") String keyword,
                  @Param("isDuel") Integer isDuel,
                  @Param("lang") String lang);

    /**
     * 根据unitId列表寻找卡牌信息
     * @param unitList unitId列表
     * @param lang 数据语言
     * @return 卡牌信息
     */
    @MapKey("unitId")
    Map<Integer, Unit> findUnits(@Param("unitList") List<Integer> unitList,
                                 @Param("lang") String lang);

    /**
     * 根据unitOwningUserId列表寻找卡牌信息
     * @param userId 用户ID
     * @param unitList unitOwningList列表
     * @param islive 是否是live社员模式
     * @param lang 数据语言
     * @return 卡牌信息
     */
    @MapKey("unitOwningUserId")
    Map<Long, Unit> findUnitsByOwningIds(@Param("userId") Integer userId,
                                         @Param("unitList") List<Long> unitList,
                                         @Param("islive") Integer islive,
                                         @Param("lang") String lang);

    /**
     * 获取用户卡组信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @param live 是否live成员模式
     * @param lang 数据语言
     * @return 卡组信息
     */
    List<Unit> searchUnits(@Param("userId") int userId,
                           @Param("start") int start,
                           @Param("limit") int limit,
                           @Param("ssr") Integer ssr,
                           @Param("sr") Integer sr,
                           @Param("back") Integer back,
                           @Param("islive") Integer live,
                           @Param("lang") String lang);

    /**
     * 演唱卡组总数
     * @param userId 用户ID
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @param live 是否live成员模式
     * @param lang 数据语言
     * @return 卡组总数
     */
    int countUnits(@Param("userId") int userId,
                   @Param("ssr") Integer ssr,
                   @Param("sr") Integer sr,
                   @Param("back") Integer back,
                   @Param("islive") Integer live,
                   @Param("lang") String lang);

    /**
     * 获取llhelper用户卡组信息
     * @param userId 用户ID
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @param live 是否live成员模式
     * @param lang 数据语言
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> exportUnits(@Param("userId") int userId,
                                   @Param("ssr") Integer ssr,
                                   @Param("sr") Integer sr,
                                   @Param("back") Integer back,
                                   @Param("islive") Integer live,
                                   @Param("lang") String lang);

    /**
     * 获取用户招募信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param type 招募类型，0/不存在表示正常招募，1表示普通生招募
     * @param lang 数据语言
     * @return 招募信息
     */
    List<SecretBoxLog> searchSecretBoxLog(@Param("userId") int userId,
                                          @Param("start") int start,
                                          @Param("limit") int limit,
                                          @Param("type") Integer type,
                                          @Param("lang") String lang);

    /**
     * 获取用户招募信息总数
     * @param userId 用户ID
     * @param type 招募类型，0/不存在表示正常招募，1表示普通生招募
     * @param lang 数据语言
     * @return 招募信息总数
     */
    int countSecretBoxLog(@Param("userId") int userId,
                          @Param("type") Integer type,
                          @Param("lang") String lang);

    /**
     * 获取用户队伍信息
     * @param userId 用户ID
     * @param unitDeckId 队伍ID
     * @param lang 数据语言
     * @return 队伍信息
     */
    List<Deck> findDecks(@Param("userId") int userId,
                         @Param("unitDeckId") Integer unitDeckId,
                         @Param("lang") String lang);

    /**
     * 获取用户开箱信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param lang 数据语言
     * @param minOpenTime 最早开箱时间
     * @return 开箱信息
     */
    List<EffortBox> searchEffortBoxLog(@Param("userId") int userId,
                                       @Param("start") int start,
                                       @Param("limit") int limit,
                                       @Param("limited") Integer limited,
                                       @Param("lang") String lang,
                                       @Param("minOpenTime") String minOpenTime);

    /**
     * 获取用户开箱信息总数
     * @param userId 用户ID
     * @param lang 数据语言
     * @return 招募信息总数
     */
    int countEffortBoxLog(@Param("userId") int userId,
                          @Param("limited") Integer limited,
                          @Param("lang") String lang);

    /**
     * 获取用户开箱信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param lang 数据语言
     * @param minOpenTime 最早开箱时间
     * @return 开箱信息
     */
    List<DuelLiveBox> searchDuelLiveBoxLog(@Param("userId") int userId,
                                           @Param("start") int start,
                                           @Param("limit") int limit,
                                           @Param("lang") String lang,
                                           @Param("minOpenTime") String minOpenTime);

    /**
     * 获取用户开箱信息总数
     * @param userId 用户ID
     * @param lang 数据语言
     * @return 招募信息总数
     */
    int countDuelLiveBoxLog(@Param("userId") int userId,
                            @Param("lang") String lang);

    /**
     * 获取开蛋的asset
     * @param limitedEffortEventId 蛋类型
     * @param liveEffortPointBoxSpecId 蛋大小
     * @param lang 数据语言
     * @return 蛋的asset
     */
    String getLimitedBoxAsset(@Param("limitedEffortEventId") int limitedEffortEventId,
                              @Param("liveEffortPointBoxSpecId") int liveEffortPointBoxSpecId,
                              @Param("lang") String lang);

    /**
     * 根据addType集合寻找类型信息
     * @param addTypeSet addType集合
     * @param lang 数据语言
     * @return 类型信息
     */
    @MapKey("addType")
    Map<Integer, AddType> findAddTypes(@Param("addTypeSet") Set<Integer> addTypeSet,
                                       @Param("lang") String lang);

    /**
     * 根据宝石ID列表寻找宝石信息
     * @param removableIdList 宝石ID列表
     * @param lang 数据语言
     * @return 宝石信息
     */
    @MapKey("unitRemovableSkillId")
    Map<Integer, UnitRemovableSkill> findUnitRemovableSkills(@Param("removableIdList") List<Integer> removableIdList,
                                                             @Param("lang") String lang);

    /**
     * 获取称号
     * @param awardId 称号ID
     * @param lang 数据语言
     * @return 称号信息
     */
    Award findAward(@Param("awardId") int awardId,
                    @Param("lang") String lang);

    /**
     * 获取称号列表
     * @param awardIdList 称号ID列表
     * @param lang 数据语言
     * @return 称号信息
     */
    @MapKey("awardId")
    Map<Integer, Award> findAwards(@Param("awardIdList") List<Integer> awardIdList,
                                   @Param("lang") String lang);

    /**
     * 获取背景
     * @param backgroundId 背景ID
     * @param lang 数据语言
     * @return 背景信息
     */
    Background findBackground(@Param("backgroundId") int backgroundId,
                              @Param("lang") String lang);

    /**
     * 获取LP回复信息
     * @param userId 用户ID
     * @param start 开始顺位
     * @param limit 显示数量
     * @param loveca 是否仅显示爱心消耗
     * @param lang 数据语言
     * @return LP回复信息
     */
    List<LpRecovery> searchLpRecoveryLog(@Param("userId") int userId,
                                         @Param("start") int start,
                                         @Param("limit") int limit,
                                         @Param("loveca") Integer loveca,
                                         @Param("lang") String lang);

    /**
     * 获取LP回复信息总数
     * @param userId 用户ID
     * @param loveca 是否仅显示爱心消耗
     * @param lang 数据语言
     * @return LP回复信息总数
     */
    int countLpRecoveryLog(@Param("userId") int userId,
                           @Param("loveca") Integer loveca,
                           @Param("lang") String lang);

    /**
     * 获取恢复道具信息
     * @param lang 数据语言
     * @return 恢复道具信息
     */
    List<RecoveryItem> findLpRecoveryItems(@Param("lang") String lang);

    /**
     * 获取LP回复统计信息
     * @param lang 数据语言
     * @return 回复统计信息
     */
    LpRecoverySummary summaryLpRecoveryLog(@Param("userId") int userId,
                                           @Param("lang") String lang);

}
