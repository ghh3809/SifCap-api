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
public class Reward {

    /**
     * 未知字段
     */
    private Boolean rewardBoxFlag;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 物品ID
     */
    private Integer itemId;
    /**
     * 物品稀有度
     */
    private Integer rarity;
    /**
     * 物品类型
     */
    private Integer addType;
    /**
     * 未知字段
     */
    private Integer itemCategoryId;
    /**
     * 物品是成员时存在，成员ID
     */
    private Integer unitId;
    /**
     * 奖励名称
     */
    private String name;
    /**
     * 对应奖励图示
     */
    private String asset;

}
