package com.kotoumi.sifcapapi.model.vo.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * @author guohaohao
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Live {

    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 用户uid
     */
    private Integer userId;
    /**
     * difficulty id
     */
    private Integer liveDifficultyId;
    /**
     * 是否随机
     */
    private Boolean isRandom;
    /**
     * 是否ac
     */
    private Integer acFlag;
    /**
     * 是否滑键
     */
    private Integer swingFlag;
    /**
     * p
     */
    private Integer perfectCnt;
    /**
     * gr
     */
    private Integer greatCnt;
    /**
     * g
     */
    private Integer goodCnt;
    /**
     * b
     */
    private Integer badCnt;
    /**
     * m
     */
    private Integer missCnt;
    /**
     * 连击
     */
    private Integer maxCombo;
    /**
     * 对外输出用：是否fc
     */
    @Transient
    private Boolean fc;
    /**
     * 对外输出用：是否ap
     */
    @Transient
    private Boolean ap;
    /**
     * 甜美分数
     */
    private Integer scoreSmile;
    /**
     * 清纯分数
     */
    private Integer scoreCute;
    /**
     * 洒脱分数
     */
    private Integer scoreCool;
    /**
     * 对外输出用：总分数
     */
    @Transient
    private Integer score;
    /**
     * 活动ID
     */
    private Integer eventId;
    /**
     * 活动名
     */
    private String eventName;
    /**
     * 获得爱心
     */
    private Integer loveCnt;
    /**
     * 获得经验
     */
    private Integer expCnt;
    /**
     * 获得金币
     */
    private Integer gameCoinCnt;
    /**
     * 获得友情点
     */
    private Integer socialPointCnt;
    /**
     * 队伍详情
     */
    private String unitListJson;
    /**
     * 对外输出用：队伍详情
     */
    @Transient
    private List<Unit> unitList;
    /**
     * 完成时间
     */
    private String playTime;
    /**
     * 对外输出用：完成时间
     */
    @Transient
    private String updateTime;
    /**
     * setting id
     */
    private Integer liveSettingId;
    /**
     * 难度
     */
    private Integer difficulty;
    /**
     * 星级
     */
    private Integer stageLevel;
    /**
     * icon资源
     */
    private String liveIconAsset;
    /**
     * track id
     */
    private Integer liveTrackId;
    /**
     * 歌曲名
     */
    private String name;
    /**
     * 标题资源
     */
    private String titleAsset;
    /**
     * 音乐资源
     */
    private String soundAsset;

}
