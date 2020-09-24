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
public class LpRecovery {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 恢复道具ID
     */
    private Integer itemId;
    /**
     * 恢复道具名称
     */
    @Transient
    private String name;
    /**
     * 使用数量
     */
    private Integer amount;
    /**
     * LP最大值
     */
    private Integer energyMax;
    /**
     * 回复LP数量
     */
    @Transient
    private Integer lpAmount;
    /**
     * 回复时间
     */
    private String recoveryTime;
    /**
     * 对应箱子图示
     */
    @Transient
    private String asset;

}
