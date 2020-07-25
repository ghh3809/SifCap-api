package com.kotoumi.sifcapapi.model.vo.response;

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
public class LLHelperUnit {

    /**
     * 卡ID
     */
    private Integer cardid;
    /**
     * 是否觉醒（0/1）
     */
    private Integer mezame;
    /**
     * 技能等级
     */
    private Integer skilllevel;
    /**
     * 孔数
     */
    private Integer maxcost;
    /**
     * 甜美属性
     */
    private Integer smile;
    /**
     * 清纯属性
     */
    private Integer pure;
    /**
     * 洒脱属性
     */
    private Integer cool;

    public static LLHelperUnit getBlankUnit() {
        return new LLHelperUnit(1, 0, 1, 0, 0, 0, 0);
    }

}
