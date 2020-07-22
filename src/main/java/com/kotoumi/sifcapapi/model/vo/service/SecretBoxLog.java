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
public class SecretBoxLog {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 招募ID
     */
    private Integer secretBoxId;
    /**
     * 招募名称
     */
    private String name;
    /**
     * UR数量
     */
    private Integer urCnt;
    /**
     * SSR数量
     */
    private Integer ssrCnt;
    /**
     * SR数量
     */
    private Integer srCnt;
    /**
     * R数量
     */
    private Integer rareCnt;
    /**
     * N数量
     */
    private Integer normalCnt;
    /**
     * 招募时间
     */
    private String ponTime;
    /**
     * 对外输出用：成员信息列表
     */
    @Transient
    private List<Unit> units;
    /**
     * 成员列表JSON
     */
    private String unitsJson;

}
