package com.kotoumi.sifcapapi.service;

import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.EffortBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.EffortBoxStatResponse;
import com.kotoumi.sifcapapi.model.vo.response.EffortBoxTypeStat;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveDetailResponse;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.User;

import java.util.List;

/**
 * @author guohaohao
 */
public interface LlproxyService {

    /**
     * 搜索用户
     * @param keyword 关键词
     * @param limit 数量
     * @param lang 数据语音
     * @return 用户列表
     */
    List<User> userSearch(String keyword, int limit, String lang);

    /**
     * 获取用户信息
     * @param uid 用户uid
     * @param lang 数据语音
     * @return 用户信息
     */
    User userInfo(int uid, String lang);

    /**
     * 获取演唱会信息
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @param setId live_setting_id
     * @param eventId 活动ID
     * @param keyword 关键词
     * @param lang 数据语音
     * @return 演唱会信息
     */
    LiveInfoResponse liveInfo(int uid, int page, int limit, Integer setId, Integer eventId, String keyword, String lang);

    /**
     * 获取演唱会信息
     * @param id 演唱会id
     * @param lang 数据语音
     * @return 演唱会信息
     */
    LiveDetailResponse liveDetail(long id, String lang);

    /**
     * 导出llhelper队伍信息
     * @param id 演唱会id
     * @param lang 数据语音
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> liveUnitsExport(long id, String lang);

    /**
     * 获取抽卡记录
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @param type 抽卡类型，0为卡池抽卡，1为普通生抽卡
     * @param lang 数据语音
     * @return 抽卡记录
     */
    SecretBoxLogResponse secretBoxLog(int uid, int page, int limit, Integer type, String lang);

    /**
     * 获取卡组信息
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @param islive 是否live成员模式
     * @param lang 数据语音
     * @return 抽卡记录
     */
    UnitsInfoResponse unitsInfo(int uid, int page, int limit, Integer ssr, Integer sr, Integer back, Integer islive, String lang);

    /**
     * 导出llhelper卡组信息
     * @param uid 用户id
     * @param ssr 是否包含ssr
     * @param sr 是否包含sr
     * @param back 是否包含预备教室成员
     * @param islive 是否live成员模式
     * @param lang 数据语音
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> unitsExport(int uid, Integer ssr, Integer sr, Integer back, Integer islive, String lang);

    /**
     * 获取队伍信息
     * @param uid 用户id
     * @param islive 是否live社员
     * @param lang 数据语音
     * @return 抽卡记录
     */
    DeckInfoResponse deckInfo(int uid, Integer islive, String lang);

    /**
     * 导出llhelper队伍信息
     * @param uid 用户id
     * @param unitDeckId 卡组ID
     * @param islive 是否live社员
     * @param lang 数据语音
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> deckExport(int uid, int unitDeckId, Integer islive, String lang);

    /**
     * 获取开箱记录
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @param limited 是否是蛋
     * @param lang 数据语音
     * @return 开箱记录
     */
    EffortBoxLogResponse effortBoxLog(int uid, int page, int limit, Integer limited, String lang);

    /**
     * 获取开箱统计
     * @param uid 用户id
     * @param limited 是否是蛋
     * @return 开箱记录
     */
    EffortBoxStatResponse effortBoxStat(Integer uid, Integer limited);

}
