package com.kotoumi.sifcapapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.DuelLiveBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.EffortBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.BoxStatResponse;
import com.kotoumi.sifcapapi.model.vo.response.BoxTypeStat;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveDetailResponse;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.AddType;
import com.kotoumi.sifcapapi.model.vo.service.Award;
import com.kotoumi.sifcapapi.model.vo.service.Background;
import com.kotoumi.sifcapapi.model.vo.service.Deck;
import com.kotoumi.sifcapapi.model.vo.service.DuelLiveBox;
import com.kotoumi.sifcapapi.model.vo.service.DuelLiveBoxStat;
import com.kotoumi.sifcapapi.model.vo.service.EffortBox;
import com.kotoumi.sifcapapi.model.vo.service.EffortBoxStat;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.UnitRemovableSkill;
import com.kotoumi.sifcapapi.model.vo.service.Reward;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author guohaohao
 */
@Service
@Slf4j
public class LlproxyServiceImpl implements LlproxyService {

    private static final String EFFORT_BOX_ASSET = "assets/flash/ui/live/img/box_icon_%s.png";
    private static final String ITEM_ASSET = "assets/image/item/item_%s_m.png";
    private static final String STICKER_ASSET = "assets/image/exchange_point/exchange_point_2_x.png";
    private static final String SYRUP_ASSET = "assets/image/recovery_item/recovery_07_m.png";

    private static final int ADD_TYPE_ITEM = 1000;
    private static final int ADD_TYPE_UNIT = 1001;
    private static final int ADD_TYPE_G = 3000;
    private static final int ADD_TYPE_FRIEND = 3002;
    private static final int ADD_TYPE_STICKER = 3006;
    private static final int ADD_TYPE_AWARD = 5100;
    private static final int ADD_TYPE_BACKGROUND = 5200;
    private static final int ADD_TYPE_REMOVABLE = 5500;
    private static final int ADD_TYPE_LP = 8000;
    private static final int ITEM_ID_LOVECA_PIECE = 1000;
    private static final int ITEM_ID_STICKER = 2;
    private static final int ITEM_ID_SYRUP = 7;
    private static final String NAME_LOVECA_PIECE = "爱心碎片";
    private static final String NAME_STICKER = "贴纸";
    private static final String NAME_SYRUP = "糖浆";

    private static final Map<Integer, EffortBoxStat> EFFORT_BOX_STAT_MAP = new HashMap<>();
    private static final Map<Integer, DuelLiveBoxStat> DUEL_LIVE_BOX_STAT_MAP = new HashMap<>();

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
    public LiveInfoResponse liveInfo(int uid, int page, int limit, Integer setId, Integer eventId, String keyword, Integer isDuel, String lang) {
        int start = limit * (page - 1);
        List<Live> liveList = llProxyMapper.searchLive(uid, start, limit, setId, eventId, keyword, isDuel, lang);
        int liveCount = llProxyMapper.countLive(uid, setId, eventId, keyword, isDuel, lang);
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
            updateSecretBoxLogInfo(secretBoxLog);
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

    @Override
    public EffortBoxLogResponse effortBoxLog(int uid, int page, int limit, Integer limited, String lang) {
        int start = limit * (page - 1);
        List<EffortBox> effortBoxList = llProxyMapper.searchEffortBoxLog(uid, start, limit, limited, lang, null);
        int secretBoxLogCount = llProxyMapper.countEffortBoxLog(uid, limited, lang);
        updateEffortBoxLogInfo(effortBoxList, lang);
        return new EffortBoxLogResponse(effortBoxList, page, (secretBoxLogCount - 1) / limit + 1, limit, secretBoxLogCount);
    }

    @Override
    public BoxStatResponse effortBoxStat(Integer uid, Integer limited) {

        if (!EFFORT_BOX_STAT_MAP.containsKey(uid)) {
            EFFORT_BOX_STAT_MAP.put(uid, new EffortBoxStat());
        }
        EffortBoxStat effortBoxStat = EFFORT_BOX_STAT_MAP.get(uid);

        // 这里注意同步问题，只有拿到锁之后才允许更新数据
        synchronized (EFFORT_BOX_STAT_MAP.get(uid)) {
            while (true) {
                List<EffortBox> effortBoxList = llProxyMapper.searchEffortBoxLog(
                        uid, 0, 10000, null, "cn", effortBoxStat.getLastOpenTime());
                log.info("effortBoxStat update count: {}", effortBoxList.size());
                if (!effortBoxList.isEmpty()) {

                    // 更新箱子数据
                    for (EffortBox effortBox : effortBoxList) {
                        BoxTypeStat boxTypeStat = effortBox.getLimitedEffortEventId() == null ?
                                effortBoxStat.getUsualBoxTypeStatList().get(5 - effortBox.getLiveEffortPointBoxSpecId()) :
                                effortBoxStat.getLimitedBoxTypeStatList().get(5 - effortBox.getLiveEffortPointBoxSpecId());
                        for (Reward reward : JSON.parseObject(effortBox.getRewardsJson(), new TypeReference<List<Reward>>() {})) {
                            boxTypeStat.addItem(getTypeKey(reward));
                        }
                        boxTypeStat.addCount();
                    }

                    // 更新本轮次数据
                    effortBoxStat.setLastOpenTime(effortBoxList.get(effortBoxList.size() - 1).getOpenTime());
                    effortBoxStat.updateData();

                } else {
                    break;
                }
            }
        }
        log.info("effortBoxStat last open time: {}", effortBoxStat.getLastOpenTime());
        return (limited == null || limited == 0) ?
                new BoxStatResponse(effortBoxStat.getUsualBoxTypeStatList(), effortBoxStat.getLastOpenTime()) :
                new BoxStatResponse(effortBoxStat.getLimitedBoxTypeStatList(), effortBoxStat.getLastOpenTime());
    }

    @Override
    public DuelLiveBoxLogResponse duelLiveBoxLog(int uid, int page, int limit, String lang) {
        int start = limit * (page - 1);
        List<DuelLiveBox> duelLiveBoxList = llProxyMapper.searchDuelLiveBoxLog(uid, start, limit, lang, null);
        int duelLiveBoxLogCount = llProxyMapper.countDuelLiveBoxLog(uid, lang);
        updateDuelLiveBoxLogInfo(duelLiveBoxList, lang);
        return new DuelLiveBoxLogResponse(duelLiveBoxList, page, (duelLiveBoxLogCount - 1) / limit + 1, limit, duelLiveBoxLogCount);
    }

    @Override
    public BoxStatResponse duelLiveBoxStat(Integer uid) {

        if (!DUEL_LIVE_BOX_STAT_MAP.containsKey(uid)) {
            DUEL_LIVE_BOX_STAT_MAP.put(uid, new DuelLiveBoxStat());
        }
        DuelLiveBoxStat duelLiveBoxStat = DUEL_LIVE_BOX_STAT_MAP.get(uid);
        List<BoxTypeStat> boxTypeStatList = duelLiveBoxStat.getBoxTypeStatList();

        // 这里注意同步问题，只有拿到锁之后才允许更新数据
        synchronized (DUEL_LIVE_BOX_STAT_MAP.get(uid)) {
            while (true) {
                List<DuelLiveBox> duelLiveBoxList = llProxyMapper.searchDuelLiveBoxLog(
                        uid, 0, 10000, null, duelLiveBoxStat.getLastOpenTime());
                log.info("duelLiveBoxStat update count: {}", duelLiveBoxList.size());
                if (!duelLiveBoxList.isEmpty()) {

                    // 更新箱子数据
                    for (DuelLiveBox duelLiveBox : duelLiveBoxList) {
                        // 完成箱子
                        int clearRankIndex = 0;
                        for (Reward reward : JSON.parseObject(duelLiveBox.getLiveClearJson(), new TypeReference<List<Reward>>() {})) {
                            boxTypeStatList.get(clearRankIndex).addItem(getTypeKey(reward));
                            boxTypeStatList.get(clearRankIndex).addCount();
                        }
                        // 分数箱子
                        int scoreRankIndex = duelLiveBox.getScoreRank() > 5 ? 12 - duelLiveBox.getScoreRank() : 7;
                        for (Reward reward : JSON.parseObject(duelLiveBox.getLiveRankJson(), new TypeReference<List<Reward>>() {})) {
                            boxTypeStatList.get(scoreRankIndex).addItem(getTypeKey(reward));
                            boxTypeStatList.get(scoreRankIndex).addCount();
                        }
                        // 连击箱子
                        int comboRankIndex = 7 + duelLiveBox.getComboRank();
                        for (Reward reward : JSON.parseObject(duelLiveBox.getLiveComboJson(), new TypeReference<List<Reward>>() {})) {
                            boxTypeStatList.get(comboRankIndex).addItem(getTypeKey(reward));
                            boxTypeStatList.get(comboRankIndex).addCount();
                        }
                    }

                    // 更新本轮次数据
                    duelLiveBoxStat.setLastOpenTime(duelLiveBoxList.get(duelLiveBoxList.size() - 1).getOpenTime());
                    duelLiveBoxStat.updateData();

                } else {
                    break;
                }
            }
        }
        log.info("duelLiveBoxStat last open time: {}", duelLiveBoxStat.getLastOpenTime());
        return new BoxStatResponse(boxTypeStatList, duelLiveBoxStat.getLastOpenTime());
    }

    /**
     * 更新用户信息
     * @param userList 用户信息列表
     * @param lang 数据语音
     */
    private void updateUserInfo(List<User> userList, String lang) {

        // 获取成员ID和称号ID信息，进行批量查询
        List<Integer> unitIdList = new ArrayList<>();
        List<Integer> awardIdList = new ArrayList<>();
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
            if (user.getSettingAwardId() != null) {
                awardIdList.add(user.getSettingAwardId());
            }
        }

        // 查询数据库并更新用户成员信息
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

        // 查询数据库并更新用户称号信息
        if (!awardIdList.isEmpty()) {
            Map<Integer, Award> awardMap = llProxyMapper.findAwards(awardIdList, lang);
            for (User user : userList) {
                if (awardMap.containsKey(user.getSettingAwardId())) {
                    user.setAwardAsset(awardMap.get(user.getSettingAwardId()).getImgAsset());
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
     * 更新招募信息
     * @param secretBoxLog 招募
     */
    private void updateSecretBoxLogInfo(SecretBoxLog secretBoxLog) {
        secretBoxLog.setUnits(JSON.parseObject(secretBoxLog.getUnitsJson(), new TypeReference<List<Unit>>() {}));
        secretBoxLog.setUnitsJson(null);
    }

    /**
     * 更新开箱信息
     * @param effortBoxList 箱子信息列表
     * @param lang 数据语言
     */
    private void updateEffortBoxLogInfo(List<EffortBox> effortBoxList, String lang) {

        List<Reward> rewardList = new ArrayList<>();

        for (EffortBox effortBox : effortBoxList) {
            // 更新箱子asset
            effortBox.setRewards(JSON.parseObject(effortBox.getRewardsJson(), new TypeReference<List<Reward>>() {}));
            effortBox.setRewardsJson(null);
            if (effortBox.getLimitedEffortEventId() == null) {
                effortBox.setAsset(String.format(EFFORT_BOX_ASSET, effortBox.getLiveEffortPointBoxSpecId() < 10 ?
                        "0" + effortBox.getLiveEffortPointBoxSpecId() : effortBox.getLiveEffortPointBoxSpecId()));
            } else {
                effortBox.setAsset(llProxyMapper.getLimitedBoxAsset(effortBox.getLimitedEffortEventId(),
                        effortBox.getLiveEffortPointBoxSpecId(), lang));
            }

            rewardList.addAll(effortBox.getRewards());
        }

        updateRewardInfo(rewardList, lang);

    }

    /**
     * 更新百协开箱信息
     * @param duelLiveBoxList 百协开箱信息列表
     * @param lang 数据语言
     */
    private void updateDuelLiveBoxLogInfo(List<DuelLiveBox> duelLiveBoxList, String lang) {

        List<Reward> rewardList = new ArrayList<>();
        List<Reward> rewards;

        for (DuelLiveBox duelLiveBox : duelLiveBoxList) {

            rewards = JSON.parseObject(duelLiveBox.getLiveClearJson(), new TypeReference<List<Reward>>() {});
            if (rewards != null && !rewards.isEmpty()) {
                duelLiveBox.setLiveClear(rewards.get(0));
                rewardList.add(duelLiveBox.getLiveClear());
            }
            rewards = JSON.parseObject(duelLiveBox.getLiveRankJson(), new TypeReference<List<Reward>>() {});
            if (rewards != null && !rewards.isEmpty()) {
                duelLiveBox.setLiveRank(rewards.get(0));
                rewardList.add(duelLiveBox.getLiveRank());
            }
            rewards = JSON.parseObject(duelLiveBox.getLiveComboJson(), new TypeReference<List<Reward>>() {});
            if (rewards != null && !rewards.isEmpty()) {
                duelLiveBox.setLiveCombo(rewards.get(0));
                rewardList.add(duelLiveBox.getLiveCombo());
            }

            duelLiveBox.setLiveClearJson(null);
            duelLiveBox.setLiveRankJson(null);
            duelLiveBox.setLiveComboJson(null);

        }

        updateRewardInfo(rewardList, lang);

    }

    /**
     * 更新reward信息
     * @param rewardList reward列表
     * @param lang 数据语言
     */
    private void updateRewardInfo(List<Reward> rewardList, String lang) {

        Set<Integer> addTypeSet = new HashSet<>();
        List<Integer> unitIdList = new ArrayList<>();
        List<Integer> removableIdList = new ArrayList<>();

        // 更新各奖励asset
        for (Reward reward : rewardList) {
            switch (reward.getAddType()) {
                case ADD_TYPE_UNIT:
                    unitIdList.add(reward.getUnitId());
                    break;
                case ADD_TYPE_REMOVABLE:
                    removableIdList.add(reward.getItemId());
                    break;
                case ADD_TYPE_ITEM:
                case ADD_TYPE_STICKER:
                case ADD_TYPE_AWARD:
                case ADD_TYPE_BACKGROUND:
                    break;
                default:
                    addTypeSet.add(reward.getAddType());
                    break;
            }

        }

        // 从数据库中查询资源
        Map<Integer, AddType> addTypesMap = null;
        Map<Integer, Unit> unitsMap = null;
        Map<Integer, UnitRemovableSkill> removablesMap = null;
        if (!addTypeSet.isEmpty()) {
            addTypesMap = llProxyMapper.findAddTypes(addTypeSet, lang);
        }
        if (!unitIdList.isEmpty()) {
            unitsMap = llProxyMapper.findUnits(unitIdList, lang);
        }
        if (!removableIdList.isEmpty()) {
            removablesMap = llProxyMapper.findUnitRemovableSkills(removableIdList, lang);
        }

        // 利用查询到的资源更新
        for (Reward reward : rewardList) {
            switch (reward.getAddType()) {
                case ADD_TYPE_ITEM:
                    if (reward.getItemId() == ITEM_ID_LOVECA_PIECE) {
                        reward.setName(NAME_LOVECA_PIECE);
                    }
                    reward.setAsset(String.format(ITEM_ASSET, reward.getItemId() < 10 ?
                            "0" + reward.getItemId() : reward.getItemId()));
                    break;
                case ADD_TYPE_UNIT:
                    unitIdList.add(reward.getUnitId());
                    if (unitsMap != null && unitsMap.containsKey(reward.getUnitId())) {
                        Unit unit = unitsMap.get(reward.getUnitId());
                        reward.setName(unit.getName());
                        reward.setAsset(unit.getNormalIconAsset());
                    }
                    break;
                case ADD_TYPE_STICKER:
                    if (reward.getItemId() == ITEM_ID_STICKER) {
                        reward.setName(NAME_STICKER);
                        reward.setAsset(STICKER_ASSET);
                    }
                    break;
                case ADD_TYPE_AWARD:
                    Award award = llProxyMapper.findAward(reward.getItemId(), lang);
                    reward.setName(award.getName());
                    reward.setAsset(award.getImgAsset());
                    break;
                case ADD_TYPE_BACKGROUND:
                    Background background = llProxyMapper.findBackground(reward.getItemId(), lang);
                    reward.setName(background.getName());
                    reward.setAsset(background.getThumbnailAsset());
                    break;
                case ADD_TYPE_REMOVABLE:
                    removableIdList.add(reward.getItemId());
                    if (removablesMap != null && removablesMap.containsKey(reward.getItemId())) {
                        UnitRemovableSkill unitRemovableSkill = removablesMap.get(reward.getItemId());
                        reward.setName(unitRemovableSkill.getName());
                        reward.setAsset(unitRemovableSkill.getIconAsset());
                    }
                    break;
                case ADD_TYPE_LP:
                    if (reward.getItemId() == ITEM_ID_SYRUP) {
                        log.info("Found lp!!!");
                        reward.setName(NAME_SYRUP);
                        reward.setAsset(SYRUP_ASSET);
                    }
                    break;
                default:
                    if (addTypesMap != null && addTypesMap.containsKey(reward.getAddType())) {
                        AddType addType = addTypesMap.get(reward.getAddType());
                        reward.setName(addType.getName());
                        reward.setAsset(addType.getSmallAsset());
                    }
                    break;
            }
        }

    }

    /**
     * 获取奖励对应的统计key
     * @param reward 奖励
     * @return 统计key
     */
    private String getTypeKey(Reward reward) {
        switch (reward.getAddType()) {
            case ADD_TYPE_ITEM:
                if (reward.getItemId() == ITEM_ID_LOVECA_PIECE) {
                    return "道具 - 爱心碎片";
                } else {
                    return "道具 - 其它";
                }
            case ADD_TYPE_UNIT:
                if (reward.getUnitId() >= 379 && reward.getUnitId() <= 382) {
                    return "辅助社员 - R技能卡";
                } else if (reward.getUnitId() >= 383 && reward.getUnitId() <= 386
                        || reward.getUnitId() == 1050
                        || reward.getUnitId() >= 1354 && reward.getUnitId() <= 1356) {
                    return "辅助社员 - SR技能卡";
                } else {
                    return "辅助社员 - 其它";
                }
            case ADD_TYPE_G:
                return "金币";
            case ADD_TYPE_FRIEND:
                return "友情点";
            case ADD_TYPE_STICKER:
                if (reward.getItemId() == ITEM_ID_STICKER) {
                    return "贴纸";
                } else {
                    return "贴纸 - 其它";
                }
            case ADD_TYPE_AWARD:
                return "称号";
            case ADD_TYPE_BACKGROUND:
                return "背景";
            case ADD_TYPE_REMOVABLE:
                if (reward.getItemId() >= 1 && reward.getItemId() <= 3) {
                    return "学园偶像技能 - 吻(C1)";
                } else if (reward.getItemId() >= 4 && reward.getItemId() <= 6) {
                    return "学园偶像技能 - 香水(C2)";
                } else if(reward.getItemId() >= 7 && reward.getItemId() <= 15) {
                    return "学园偶像技能 - 指环(C2)";
                } else if(reward.getItemId() >= 16 && reward.getItemId() <= 24) {
                    return "学园偶像技能 - 十字(C3)";
                } else if(reward.getItemId() >= 25 && reward.getItemId() <= 27) {
                    return "学园偶像技能 - 光环(C3)";
                } else if(reward.getItemId() >= 28 && reward.getItemId() <= 30) {
                    return "学园偶像技能 - 面纱(C4)";
                } else if(reward.getItemId() == 31 || reward.getItemId() == 34 || reward.getItemId() == 37) {
                    return "学园偶像技能 - 魅力(C4)";
                } else if(reward.getItemId() == 32 || reward.getItemId() == 35 || reward.getItemId() == 38) {
                    return "学园偶像技能 - 治愈(C4)";
                } else if(reward.getItemId() == 33 || reward.getItemId() == 36 || reward.getItemId() == 39) {
                    return "学园偶像技能 - 诡计(C4)";
                } else if(reward.getItemId() >= 40 && reward.getItemId() <= 42) {
                    return "学园偶像技能 - 眼神(C5)";
                } else if(reward.getItemId() >= 43 && reward.getItemId() <= 51) {
                    return "学园偶像技能 - 颤音(C5)";
                } else if(reward.getItemId() >= 52 && reward.getItemId() <= 54) {
                    return "学园偶像技能 - 绽放(C6)";
                } else if(reward.getItemId() >= 73 && reward.getItemId() <= 78) {
                    return "学园偶像技能 - 九重奏(C4)";
                } else {
                    return "学园偶像技能 - 个宝(C4)";
                }
            case ADD_TYPE_LP:
                if (reward.getItemId() == ITEM_ID_SYRUP) {
                    return "糖浆";
                } else {
                    return "耐力恢复道具 - 其它";
                }
            default:
                return "其它";
        }
    }

}
