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
public class Background {

    /**
     * 背景ID
     */
    private Integer backgroundId;
    /**
     * 背景名称
     */
    private String name;
    /**
     * 背景描述
     */
    private String description;
    /**
     * 背景asset
     */
    private String imgAsset;
    /**
     * 背景小图
     */
    private String thumbnailAsset;

}
