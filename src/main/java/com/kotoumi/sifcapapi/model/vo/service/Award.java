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
public class Award {

    /**
     * 称号ID
     */
    private Integer awardId;
    /**
     * 称号名称
     */
    private String name;
    /**
     * 称号描述
     */
    private String description;
    /**
     * 称号asset
     */
    private String imgAsset;

}
