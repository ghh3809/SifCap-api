package com.kotoumi.sifcapapi.model.vo.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guohaohao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UnitRemovableSkill {

    /**
     * 宝石ID
     */
    private Integer unitRemovableSkillId;
    /**
     * 宝石名称
     */
    private String name;
    /**
     * 图标asset
     */
    private String iconAsset;
    /**
     * 装配asset
     */
    private String bondAsset;
    /**
     * 容量
     */
    private Integer size;
    /**
     * 描述
     */
    private String description;

}
