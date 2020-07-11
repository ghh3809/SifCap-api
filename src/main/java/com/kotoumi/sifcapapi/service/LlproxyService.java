package com.kotoumi.sifcapapi.service;

import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
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
    LiveInfoResponse liveInfo(int uid, int page, int limit, Integer setId, Integer eventId);

}
