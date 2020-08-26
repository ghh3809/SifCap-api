package com.kotoumi.sifcapapi.model.vo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kotoumi.sifcapapi.model.vo.service.DuelLiveBox;
import com.kotoumi.sifcapapi.model.vo.service.EffortBox;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author guohaohao
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DuelLiveBoxLogResponse {

    private List<DuelLiveBox> boxes;
    private Integer currPage;
    private Integer allPage;
    private Integer limit;
    private Integer count;

}
