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
public class Deck {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 用户uid
     */
    private Integer userId;
    /**
     * 队伍编号
     */
    private Integer unitDeckId;
    /**
     * 是否是主力
     */
    private Integer mainFlag;
    /**
     * 队伍名称
     */
    private String deckName;
    /**
     * 成员信息
     */
    @Transient
    private List<Unit> units;
    /**
     * 队伍成员json
     */
    private String unitDeckDetailJson;

}
