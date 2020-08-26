package com.kotoumi.sifcapapi.model.vo.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * @author guohaohao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DuelLiveBox {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 分数rank
     */
    private Integer scoreRank;
    /**
     * 连击rank
     */
    private Integer comboRank;
    /**
     * live完成奖励JSON
     */
    private String liveClearJson;
    /**
     * 奖励列表
     */
    @Transient
    private Reward liveClear;
    /**
     * 分数奖励JSON
     */
    private String liveRankJson;
    /**
     * 奖励列表
     */
    @Transient
    private Reward liveRank;
    /**
     * 连击奖励JSON
     */
    private String liveComboJson;
    /**
     * 奖励列表
     */
    @Transient
    private Reward liveCombo;
    /**
     * 开箱时间
     */
    private String openTime;

}
