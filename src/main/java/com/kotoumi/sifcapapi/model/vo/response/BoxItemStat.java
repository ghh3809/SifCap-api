package com.kotoumi.sifcapapi.model.vo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 箱子中的一种物品
 * @author guohaohao
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BoxItemStat {

    private String category;
    private String asset;
    private Integer amount;
    private String ratio;

}
