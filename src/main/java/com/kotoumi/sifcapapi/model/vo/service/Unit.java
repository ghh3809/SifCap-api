package com.kotoumi.sifcapapi.model.vo.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author guohaohao
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Unit {

    /**
     * 成员ID
     */
    private Integer unitId;
    /**
     * 成员等级
     */
    private Integer level;
    /**
     * 成员rank
     */
    private Integer displayRank;
    /**
     * 未觉头像
     */
    private String normalIconAsset;
    /**
     * 觉醒头像
     */
    private String rankMaxIconAsset;

}
