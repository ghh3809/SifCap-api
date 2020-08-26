package com.kotoumi.sifcapapi.model.vo.service;

import com.kotoumi.sifcapapi.model.vo.response.BoxItemStat;
import com.kotoumi.sifcapapi.model.vo.response.BoxTypeStat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百协箱子统计类
 * @author guohaohao
 */
@Data
public class DuelLiveBoxStat {

    private static final Map<String, String> ASSET_MAP = new HashMap<>();
    /**
     * 数据更新时间
     */
    private String lastOpenTime;
    /**
     * 箱子统计结果，0存放完成箱子，1-7存放分数箱子，8-11存放combo箱子
     */
    List<BoxTypeStat> boxTypeStatList;

    static {
        ASSET_MAP.put("糖浆", "assets/image/recovery_item/recovery_07_m.png");
        ASSET_MAP.put("辅助社员 - R技能卡", "git/SIFStatic/icon/normal/379.png");
        ASSET_MAP.put("辅助社员 - 其它", "git/SIFStatic/icon/normal/146.png");
        ASSET_MAP.put("金币", "assets/image/ui/common/com_icon_03.png");
        ASSET_MAP.put("友情点", "assets/image/ui/item/com_icon_32.png");
    }

    public DuelLiveBoxStat() {

        this.lastOpenTime = "";
        this.boxTypeStatList = new ArrayList<>(12);

        // 初始化统计数据
        String[] scoreDesc = new String[] {"SSS Ⅳ(600~999%)", "SSS Ⅲ(450~599%)", "SSS Ⅱ(300~449%)", "SSS Ⅰ(200~299%)", "SSS(150~199%)", "SS(120~149%)", "S(100~119%)"};
        String[] comboDesc = new String[] {"S", "A", "B", "C"};
        this.boxTypeStatList.add(new BoxTypeStat(
                "",
                0,
                new ArrayList<>(),
                new HashMap<>()
        ));
        for (String score : scoreDesc) {
            this.boxTypeStatList.add(new BoxTypeStat(
                    score,
                    0,
                    new ArrayList<>(),
                    new HashMap<>()
            ));
        }
        for (String combo : comboDesc) {
            this.boxTypeStatList.add(new BoxTypeStat(
                    combo,
                    0,
                    new ArrayList<>(),
                    new HashMap<>()
            ));
        }

    }

    public void updateData() {
        updateData(this.boxTypeStatList);
    }

    public void updateData(List<BoxTypeStat> boxTypeStatList) {
        for (BoxTypeStat boxTypeStat : boxTypeStatList) {
            boxTypeStat.setItems(new ArrayList<>());
            for (Map.Entry<String, Integer> entry : boxTypeStat.getItemsMap().entrySet()) {
                boxTypeStat.getItems().add(new BoxItemStat(
                        entry.getKey(),
                        ASSET_MAP.getOrDefault(entry.getKey(), null),
                        entry.getValue(),
                        String.format("%.2f%%", 100.0 * entry.getValue() / boxTypeStat.getCount())
                ));
            }
            boxTypeStat.getItems().sort(Comparator.comparing(BoxItemStat::getAmount).reversed());
        }
    }

}
