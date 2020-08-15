package com.kotoumi.sifcapapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveDetailResponse;
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
    public List<User> userSearch(String keyword, int limit, String lang) {
        // 基础信息
        List<User> userList = llProxyMapper.searchUser(keyword, limit, lang);
        updateUserInfo(userList, lang);
        return userList;
    }

    @Override
    public User userInfo(int uid, String lang) {
        User user = llProxyMapper.findUser(uid, lang);
        if (user != null) {
            updateUserInfo(Collections.singletonList(user), lang);
        }
        return user;
    }

    @Override
    public LiveInfoResponse liveInfo(int uid, int page, int limit, Integer setId, Integer eventId, String keyword, String lang) {
        int start = limit * (page - 1);
        List<Live> liveList = llProxyMapper.searchLive(uid, start, limit, setId, eventId, keyword, lang);
        int liveCount = llProxyMapper.countLive(uid, setId, eventId, keyword, lang);
        for (Live live : liveList) {
            updateLiveInfo(live, false, lang);
        }
        return new LiveInfoResponse(liveList, page, (liveCount - 1) / limit + 1, limit, liveCount);
    }

    @Override
    public LiveDetailResponse liveDetail(long id, String lang) {
        Live live = llProxyMapper.findLive(id, lang);
        if (live != null) {
            updateLiveInfo(live, true, lang);
        }
        return new LiveDetailResponse(live);
    }

    @Override
    public List<LLHelperUnit> liveUnitsExport(long id, String lang) {
        Live live = llProxyMapper.findLive(id, "cn");
        if ((live != null) && (live.getUnitListJson() != null)) {
            // 获取社员ID
            List<Unit> unitList = JSON.parseObject(live.getUnitListJson(), new TypeReference<List<Unit>>() {});
            List<Integer> unitIdList = new ArrayList<>();
            for (Unit unit : unitList) {
                unitIdList.add(unit.getUnitId());
            }

            // 初始化队伍信息
            List<LLHelperUnit> llHelperUnitList = new ArrayList<>(9);
            for (int i = 0; i < 9; i ++) {
                llHelperUnitList.add(LLHelperUnit.getBlankUnit());
            }
            Map<Integer, Unit> unitMap = llProxyMapper.findUnits(unitIdList, lang);
            for (Unit unit : unitList) {
                if (unitMap.containsKey(unit.getUnitId())) {
                    Unit mappedUnit = unitMap.get(unit.getUnitId());
                    LLHelperUnit llHelperUnit = new LLHelperUnit(
                            mappedUnit.getUnitNumber(),
                            unit.getIsRankMax() ? 1 : 0,
                            unit.getUnitSkillLevel(),
                            mappedUnit.getDefaultRemovableSkillCapacity(),
                            mappedUnit.getSmileMax(),
                            mappedUnit.getPureMax(),
                            mappedUnit.getCoolMax()
                    );
                    llHelperUnitList.set(unit.getPosition() - 1, llHelperUnit);
                }
            }
            return llHelperUnitList;
        }
        return Collections.emptyList();
    }

    @Override
    public SecretBoxLogResponse secretBoxLog(int uid, int page, int limit, Integer type, String lang) {
        int start = limit * (page - 1);
        List<SecretBoxLog> secretBoxLogList = llProxyMapper.searchSecretBoxLog(uid, start, limit, type, lang);
        int secretBoxLogCount = llProxyMapper.countSecretBoxLog(uid, type, lang);
        for (SecretBoxLog secretBoxLog : secretBoxLogList) {
            updateSecretBoxLogInfo(secretBoxLog, lang);
        }
        return new SecretBoxLogResponse(secretBoxLogList, page, (secretBoxLogCount - 1) / limit + 1, limit, secretBoxLogCount);
    }

    @Override
    public UnitsInfoResponse unitsInfo(int uid, int page, int limit, Integer ssr, Integer sr, Integer back, Integer islive, String lang) {
        int start = limit * (page - 1);
        List<Unit> unitList = llProxyMapper.searchUnits(uid, start, limit, ssr, sr, back, islive, lang);
        int unitCount = llProxyMapper.countUnits(uid, ssr, sr, back, islive, lang);
        return new UnitsInfoResponse(unitList, page, (unitCount - 1) / limit + 1, limit, unitCount);
    }

    @Override
    public List<LLHelperUnit> unitsExport(int uid, Integer ssr, Integer sr, Integer back, Integer islive, String lang) {
        return llProxyMapper.exportUnits(uid, ssr, sr, back, islive, lang);
    }

    @Override
    public DeckInfoResponse deckInfo(int uid, Integer islive, String lang) {

        List<Long> unitOwningUserIdList = new ArrayList<>();

        // 获取社员ID
        List<Deck> deckList = llProxyMapper.findDecks(uid, null, lang);
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
            Map<Long, Unit> unitMap = llProxyMapper.findUnitsByOwningIds(uid, unitOwningUserIdList, islive, lang);
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
    public List<LLHelperUnit> deckExport(int uid, int unitDeckId, Integer islive, String lang) {
        List<Deck> deckList = llProxyMapper.findDecks(uid, unitDeckId, lang);
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
                Map<Long, Unit> unitMap = llProxyMapper.findUnitsByOwningIds(uid, unitOwningUserIdList, islive, lang);
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
     * @param lang 数据语音
     */
    private void updateUserInfo(List<User> userList, String lang) {

        // 获取成员ID信息，进行批量查询
        List<Integer> unitIdList = new ArrayList<>();
        for (User user : userList) {
            user.setUid(user.getUserId());
            if (user.getNaviUnitInfoJson() != null) {
                user.setNaviUnitInfo(JSON.parseObject(user.getNaviUnitInfoJson(), Unit.class));
                user.setNaviUnitInfoJson(null);
                unitIdList.add(user.getNaviUnitInfo().getUnitId());
            }
            if (user.getCenterUnitInfoJson() != null) {
                user.setCenterUnitInfo(JSON.parseObject(user.getCenterUnitInfoJson(), Unit.class));
                user.setCenterUnitInfoJson(null);
                unitIdList.add(user.getCenterUnitInfo().getUnitId());
            }
        }

        // 查询数据库并更新用户信息
        if (!unitIdList.isEmpty()) {
            Map<Integer, Unit> unitMap = llProxyMapper.findUnits(unitIdList, lang);
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
     * @param lang 数据语音
     */
    private void updateLiveInfo(Live live, boolean withUnit, String lang) {
        live.setFc((live.getGoodCnt() == 0) && (live.getBadCnt() == 0) && (live.getMissCnt() == 0));
        live.setAp(live.getFc() && live.getGreatCnt() == 0);
        live.setScore(live.getScoreSmile() + live.getScoreCute() + live.getScoreCool());
        if (withUnit && live.getUnitListJson() != null) {
            // 获取社员ID
            List<Unit> unitList = JSON.parseObject(live.getUnitListJson(), new TypeReference<List<Unit>>() {});
            List<Integer> unitIdList = new ArrayList<>();
            for (Unit unit : unitList) {
                unitIdList.add(unit.getUnitId());
            }

            // 查询社员详细信息
            List<Unit> resultUnitList = new ArrayList<>(9);
            for (int i = 0; i < 9; i ++) {
                resultUnitList.add(null);
            }
            if (!unitIdList.isEmpty()) {
                Map<Integer, Unit> unitMap = llProxyMapper.findUnits(unitIdList, lang);
                for (Unit unit : unitList) {
                    if (unitMap.containsKey(unit.getUnitId())) {
                        Unit mappedUnit = unitMap.get(unit.getUnitId());
                        unit.setUnitNumber(mappedUnit.getUnitNumber());
                        unit.setEponym(mappedUnit.getEponym());
                        unit.setName(mappedUnit.getName());
                        unit.setNormalIconAsset(mappedUnit.getNormalIconAsset());
                        unit.setRankMaxIconAsset(mappedUnit.getRankMaxIconAsset());
                        if (unit.getIsRankMax()) {
                            unit.setDisplayRank(2);
                        } else {
                            unit.setDisplayRank(1);
                        }
                    }
                    resultUnitList.set(unit.getPosition() - 1, unit);
                }
            }
            live.setUnitList(resultUnitList);
        } else {
            live.setUnitList(null);
        }
        live.setUnitListJson(null);
        live.setUpdateTime(live.getPlayTime());
    }

    /**
     * 更新live信息
     * @param secretBoxLog 演唱会
     * @param lang 数据语音
     */
    private void updateSecretBoxLogInfo(SecretBoxLog secretBoxLog, String lang) {
        secretBoxLog.setUnits(JSON.parseObject(secretBoxLog.getUnitsJson(), new TypeReference<List<Unit>>() {}));
        secretBoxLog.setUnitsJson(null);
    }

}
