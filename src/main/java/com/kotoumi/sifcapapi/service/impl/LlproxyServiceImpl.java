package com.kotoumi.sifcapapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.Deck;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        // 基础信息
        List<User> userList = llProxyMapper.searchUser(keyword, limit);
        updateUserInfo(userList);
        return userList;
    }

    @Override
    public User userInfo(int uid) {
        User user = llProxyMapper.findUser(uid);
        if (user != null) {
            updateUserInfo(Collections.singletonList(user));
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

    @Override
    public DeckInfoResponse deckInfo(int uid) {

        List<Long> unitOwningUserIdList = new ArrayList<>();

        // 获取社员ID
        List<Deck> deckList = llProxyMapper.findDecks(uid, null);
        for (Deck deck : deckList) {
            List<Unit> unitDeckDetail = JSON.parseObject(deck.getUnitDeckDetailJson(), new TypeReference<List<Unit>>() {});
            deck.setUnitDeckDetailJson(null);
            if (unitDeckDetail != null) {
                for (Unit unit : unitDeckDetail) {
                    unitOwningUserIdList.add(unit.getUnitOwningUserId());
                }
                deck.setUnits(unitDeckDetail);
            }
        }

        // 查询社员详细信息
        if (!unitOwningUserIdList.isEmpty()) {
            Map<Long, Unit> unitMap = llProxyMapper.findUnitsByOwningIds(uid, unitOwningUserIdList);
            for (Deck deck : deckList) {
                List<Unit> unitDeckDetail = deck.getUnits();
                // 初始化队伍信息
                List<Unit> units = new ArrayList<>(9);
                for (int i = 0; i < 9; i ++) {
                    units.add(new Unit());
                }
                if (unitDeckDetail != null) {
                    for (Unit unit : unitDeckDetail) {
                        units.set(unit.getPosition() - 1, unitMap.getOrDefault(unit.getUnitOwningUserId(), unit));
                    }
                }
                deck.setUnits(units);
            }
        }

        return new DeckInfoResponse(deckList);
    }

    @Override
    public List<LLHelperUnit> deckExport(int uid, int unitDeckId) {
        List<Deck> deckList = llProxyMapper.findDecks(uid, unitDeckId);
        if (!deckList.isEmpty()) {

            List<Long> unitOwningUserIdList = new ArrayList<>();

            // 获取社员ID
            Deck deck = deckList.get(0);
            List<Unit> unitDeckDetail = JSON.parseObject(deck.getUnitDeckDetailJson(), new TypeReference<List<Unit>>() {});
            if (unitDeckDetail != null) {
                for (Unit unit : unitDeckDetail) {
                    unitOwningUserIdList.add(unit.getUnitOwningUserId());
                }
                deck.setUnits(unitDeckDetail);
            }

            // 查询社员详细信息
            if (!unitOwningUserIdList.isEmpty()) {
                Map<Long, Unit> unitMap = llProxyMapper.findUnitsByOwningIds(uid, unitOwningUserIdList);
                // 初始化队伍信息
                List<LLHelperUnit> llHelperUnitList = new ArrayList<>(9);
                for (int i = 0; i < 9; i ++) {
                    llHelperUnitList.add(LLHelperUnit.getBlankUnit());
                }
                for (Unit unit : unitDeckDetail) {
                    if (unitMap.containsKey(unit.getUnitOwningUserId())) {
                        Unit mappedUnit = unitMap.get(unit.getUnitOwningUserId());
                        llHelperUnitList.set(unit.getPosition() - 1, new LLHelperUnit(
                                mappedUnit.getUnitNumber(),
                                mappedUnit.getRank() - 1,
                                mappedUnit.getUnitSkillLevel(),
                                mappedUnit.getUnitRemovableSkillCapacity(),
                                mappedUnit.getSmileMax(),
                                mappedUnit.getPureMax(),
                                mappedUnit.getCoolMax()
                        ));
                    }
                }
                return llHelperUnitList;
            }

        }
        return Collections.emptyList();
    }

    /**
     * 更新用户信息
     * @param userList 用户信息列表
     */
    private void updateUserInfo(List<User> userList) {

        // 获取成员ID信息，进行批量查询
        List<Integer> unitList = new ArrayList<>();
        for (User user : userList) {
            user.setUid(user.getUserId());
            if (user.getNaviUnitInfoJson() != null) {
                user.setNaviUnitInfo(JSON.parseObject(user.getNaviUnitInfoJson(), Unit.class));
                user.setNaviUnitInfoJson(null);
                unitList.add(user.getNaviUnitInfo().getUnitId());
            }
            if (user.getCenterUnitInfoJson() != null) {
                user.setCenterUnitInfo(JSON.parseObject(user.getCenterUnitInfoJson(), Unit.class));
                user.setCenterUnitInfoJson(null);
                unitList.add(user.getCenterUnitInfo().getUnitId());
            }
        }

        // 查询数据库并更新用户信息
        if (!unitList.isEmpty()) {
            Map<Integer, Unit> unitMap = llProxyMapper.findUnits(unitList);
            for (User user: userList) {
                if (user.getNaviUnitInfo() != null) {
                    if (unitMap.containsKey(user.getNaviUnitInfo().getUnitId())) {
                        Unit naviUnit = unitMap.get(user.getNaviUnitInfo().getUnitId());
                        user.getNaviUnitInfo().setUnitNumber(naviUnit.getUnitNumber());
                        user.getNaviUnitInfo().setEponym(naviUnit.getEponym());
                        user.getNaviUnitInfo().setName(naviUnit.getName());
                        user.getNaviUnitInfo().setNormalIconAsset(naviUnit.getNormalIconAsset());
                        user.getNaviUnitInfo().setRankMaxIconAsset(naviUnit.getRankMaxIconAsset());
                    }
                }
                if (user.getCenterUnitInfo() != null) {
                    if (unitMap.containsKey(user.getCenterUnitInfo().getUnitId())) {
                        Unit centerUnit = unitMap.get(user.getCenterUnitInfo().getUnitId());
                        user.getCenterUnitInfo().setUnitNumber(centerUnit.getUnitNumber());
                        user.getCenterUnitInfo().setEponym(centerUnit.getEponym());
                        user.getCenterUnitInfo().setName(centerUnit.getName());
                        user.getCenterUnitInfo().setNormalIconAsset(centerUnit.getNormalIconAsset());
                        user.getCenterUnitInfo().setRankMaxIconAsset(centerUnit.getRankMaxIconAsset());
                    }
                }
            }
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
