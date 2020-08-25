package com.kotoumi.sifcapapi.model.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author guohaohao
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EffortBoxTypeStat {

    private Integer capacity;
    private Integer count;
    private List<EffortBoxItemStat> items;
    @JsonIgnore
    private Map<String, Integer> itemsMap;

    public void addCount() {
        count ++;
    }

    public void addItem(String key) {
        if (!itemsMap.containsKey(key)) {
            itemsMap.put(key, 1);
        } else {
            itemsMap.put(key, itemsMap.get(key) + 1);
        }
    }

}
