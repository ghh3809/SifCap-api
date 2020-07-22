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
    private String cardid;
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

}
