package com.kotoumi.sifcapapi.model.vo.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * @author guohaohao
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    /**
     * 对外输出用：看板信息
     */
    @Transient
    private Unit naviUnitInfo;
    /**
     * 对外输出用：uid
     */
    private String uid;
    /**
     * uid
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 当前总经验
     */
    private Integer exp;
    /**
     * 上一级经验
     */
    private Integer previousExp;
    /**
     * 下一级经验
     */
    private Integer nextExp;
    /**
     * 金币
     */
    private Long gameCoin;
    /**
     * 爱心
     */
    private Integer snsCoin;
    /**
     * 免费爱心
     */
    private Integer freeSnsCoin;
    /**
     * 付费爱心
     */
    private Integer paidSnsCoin;
    /**
     * 友情点
     */
    private Long socialPoint;
    /**
     * 社员上限
     */
    private Integer unitMax;
    /**
     * 备用教室社员上限
     */
    private Integer waitingUnitMax;
    /**
     * 当前LP
     */
    private Integer currentEnergy;
    /**
     * LP上限
     */
    private Integer energyMax;
    /**
     * LP满时间
     */
    private String energyFullTime;
    /**
     * LP满所需时间
     */
    private Integer energyFullNeedTime;
    /**
     * 超出上限LP数
     */
    private Integer overMaxEnergy;
    /**
     * MA券
     */
    private Integer trainingEnergy;
    /**
     * MA券总数
     */
    private Integer trainingEnergyMax;
    /**
     * 好友上限
     */
    private Integer friendMax;
    /**
     * 用户ID
     */
    private Integer inviteCode;
    /**
     * 未知字段
     */
    private Integer unlockRandomLiveMuse;
    /**
     * 未知字段
     */
    private Integer unlockRandomLiveAqours;
    /**
     * 创建账号日期
     */
    private String insertDate;
    /**
     * 更新账号日期
     */
    private String updateDate;
    /**
     * 新手引导状态
     */
    private Integer tutorialState;
    /**
     * 某神秘货币1
     */
    private Integer diamondCoin;
    /**
     * 某神秘货币2
     */
    private Integer crystalCoin;
    /**
     * 生日：月份
     */
    private Integer birthMonth;
    /**
     * 生日：日期
     */
    private Integer birthDay;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;

}
