package com.kotoumi.sifcapapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guohaohao
 */
@Service
public class LlproxyServiceImpl implements LlproxyService {

    @Resource
    private LlProxyMapper llProxyMapper;

    @Override
    public List<User> userSearch(String keyword, int limit) {
        List<User> userList = llProxyMapper.searchUser(keyword, limit);
        for (User user: userList) {
            updateUserInfo(user);
        }
        return userList;
    }

    @Override
    public User userInfo(int uid) {
        User user = llProxyMapper.findUser(uid);
        if (user != null) {
            updateUserInfo(user);
        }
        return user;
    }

    @Override
    public LiveInfoResponse liveInfo(int uid, int page, int limit, Integer setId, Integer eventId, String keyword) {
        int start = limit * (page - 1);
        List<Live> liveList = llProxyMapper.searchLive(uid, start, limit, setId, eventId, keyword);
        int liveCount = llProxyMapper.countLive(uid, setId, eventId, keyword);
        for (Live live : liveList) {
            updateLiveInfo(live);
        }

        return new LiveInfoResponse(liveList, page, (liveCount - 1) / limit + 1, limit, liveCount);
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    private void updateUserInfo(User user) {
        user.setUid(user.getUserId());
        if (user.getNaviUnitInfoJson() != null) {
            user.setNaviUnitInfo(JSON.parseObject(user.getNaviUnitInfoJson(), Unit.class));
            user.setNaviUnitInfoJson(null);
            updateUnitInfo(user.getNaviUnitInfo());
        }
        if (user.getCenterUnitInfoJson() != null) {
            user.setCenterUnitInfo(JSON.parseObject(user.getCenterUnitInfoJson(), Unit.class));
            user.setCenterUnitInfoJson(null);
            updateUnitInfo(user.getCenterUnitInfo());
        }
    }

    /**
     * 更新卡面信息
     * @param unit 卡面
     */
    private void updateUnitInfo(Unit unit) {
        Unit dbUnit = llProxyMapper.findUnit(unit.getUnitId());
        unit.setNormalIconAsset(dbUnit.getNormalIconAsset());
        unit.setRankMaxIconAsset(dbUnit.getRankMaxIconAsset());
    }

    /**
     * 更新live信息
     * @param live 演唱会
     */
    private void updateLiveInfo(Live live) {
        live.setFc((live.getGoodCnt() == 0) && (live.getBadCnt() == 0) && (live.getMissCnt() == 0));
        live.setAp(live.getFc() && live.getGreatCnt() == 0);
        live.setScore(live.getScoreSmile() + live.getScoreCute() + live.getScoreCool());
        live.setUnitList(null);
        live.setUnitListJson(null);
        live.setUpdateTime(live.getPlayTime());
    }

}
