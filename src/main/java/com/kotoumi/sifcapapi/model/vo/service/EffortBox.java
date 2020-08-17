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
public class EffortBox {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 箱子类型1-5，按顺序对应10/40/100/200/400箱子
     */
    private Integer liveEffortPointBoxSpecId;
    /**
     * 蛋的类型（箱子没有）
     */
    private Integer limitedEffortEventId;
    /**
     * 容量
     */
    private Integer capacity;
    /**
     * 奖励JSON，要求不为空
     */
    private String rewardsJson;
    /**
     * 奖励列表
     */
    @Transient
    private List<Reward> rewards;
    /**
     * 开箱时间
     */
    private String openTime;
    /**
     * 对应箱子图示
     */
    @Transient
    private String asset;

}
