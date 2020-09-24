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
public class RecoveryItem {

    /**
     * 恢复道具ID
     */
    private Integer recoveryItemId;
    /**
     * 道具名称
     */
    private String name;
    /**
     * 恢复类型
     */
    private Integer recoveryType;
    /**
     * 恢复数量
     */
    private Integer recoveryValue;
    /**
     * 道具asset
     */
    private String middleAsset;

}
