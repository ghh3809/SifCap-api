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
public class AddType {

    /**
     * 物品类型
     */
    private Integer addType;
    /**
     * 物品名
     */
    private String name;
    /**
     * 小资源
     */
    private String smallAsset;
    /**
     * 中资源
     */
    private String middleAsset;
    /**
     * 大资源
     */
    private String largeAsset;
    /**
     * 量词
     */
    private String numberSuffix;

}
