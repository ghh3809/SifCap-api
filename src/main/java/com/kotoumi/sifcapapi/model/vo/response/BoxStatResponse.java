package com.kotoumi.sifcapapi.model.vo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 查询统计时的最终返回
 * @author guohaohao
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BoxStatResponse {

    private List<BoxTypeStat> stats;
    private String updateTime;

}
