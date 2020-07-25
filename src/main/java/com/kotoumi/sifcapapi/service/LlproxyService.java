package com.kotoumi.sifcapapi.service;

import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
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
     * @return 用户列表
     */
    List<User> userSearch(String keyword, int limit);

    /**
     * 获取用户信息
     * @param uid 用户uid
     * @return 用户信息
     */
    User userInfo(int uid);

    /**
     * 获取演唱会信息
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @param setId live_setting_id
     * @param eventId 活动ID
     * @return 演唱会信息
     */
    LiveInfoResponse liveInfo(int uid, int page, int limit, Integer setId, Integer eventId, String keyword);

    /**
     * 获取抽卡记录
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @param type 抽卡类型，0为卡池抽卡，1为普通生抽卡
     * @return 抽卡记录
     */
    SecretBoxLogResponse secretBoxLog(int uid, int page, int limit, Integer type);

    /**
     * 获取卡组信息
     * @param uid 用户id
     * @param page 页码
     * @param limit 数量
     * @return 抽卡记录
     */
    UnitsInfoResponse unitsInfo(int uid, int page, int limit);

    /**
     * 导出llhelper卡组信息
     * @param uid 用户id
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> unitsExport(int uid);

    /**
     * 获取队伍信息
     * @param uid 用户id
     * @return 抽卡记录
     */
    DeckInfoResponse deckInfo(int uid);

    /**
     * 导出llhelper队伍信息
     * @param uid 用户id
     * @param unitDeckId 卡组ID
     * @return llhelper卡组信息
     */
    List<LLHelperUnit> deckExport(int uid, int unitDeckId);

}
