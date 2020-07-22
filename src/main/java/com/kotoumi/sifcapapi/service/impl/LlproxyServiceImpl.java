package com.kotoumi.sifcapapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guohaohao
 */
@Service
@Slf4j
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

    @Override
    public SecretBoxLogResponse secretBoxLog(int uid, int page, int limit, Integer type) {
        int start = limit * (page - 1);
        List<SecretBoxLog> secretBoxLogList = llProxyMapper.searchSecretBoxLog(uid, start, limit, type);
        int secretBoxLogCount = llProxyMapper.countSecretBoxLog(uid, type);
        for (SecretBoxLog secretBoxLog : secretBoxLogList) {
            updateSecretBoxLogInfo(secretBoxLog);
        }
        return new SecretBoxLogResponse(secretBoxLogList, page, (secretBoxLogCount - 1) / limit + 1, limit, secretBoxLogCount);
    }

    @Override
    public UnitsInfoResponse unitsInfo(int uid, int page, int limit) {
        int start = limit * (page - 1);
        List<Unit> unitList = llProxyMapper.searchUnits(uid, start, limit);
        int unitCount = llProxyMapper.countUnits(uid);
        return new UnitsInfoResponse(unitList, page, (unitCount - 1) / limit + 1, limit, unitCount);
    }

    @Override
    public List<LLHelperUnit> unitsExport(int uid) {
        return llProxyMapper.exportUnits(uid);
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
        if (dbUnit != null) {
            unit.setNormalIconAsset(dbUnit.getNormalIconAsset());
            unit.setRankMaxIconAsset(dbUnit.getRankMaxIconAsset());
        }
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

    /**
     * 更新live信息
     * @param secretBoxLog 演唱会
     */
    private void updateSecretBoxLogInfo(SecretBoxLog secretBoxLog) {
        secretBoxLog.setUnits(JSON.parseObject(secretBoxLog.getUnitsJson(), new TypeReference<List<Unit>>() {}));
        secretBoxLog.setUnitsJson(null);
    }

}
