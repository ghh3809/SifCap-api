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
 * 箱子统计类
 * @author guohaohao
 */
@Data
public class EffortBoxStat {

    private static final Map<String, String> ASSET_MAP = new HashMap<>();
    /**
     * 数据更新时间
     */
    private String lastOpenTime;
    /**
     * 箱子统计结果
     */
    List<BoxTypeStat> usualBoxTypeStatList;
    /**
     * 蛋统计结果
     */
    List<BoxTypeStat> limitedBoxTypeStatList;

    static {
        ASSET_MAP.put("道具 - 爱心碎片", "assets/image/item/item_1000_m.png");
        ASSET_MAP.put("辅助社员 - R技能卡", "git/SIFStatic/icon/normal/379.png");
        ASSET_MAP.put("辅助社员 - SR技能卡", "git/SIFStatic/icon/normal/383.png");
        ASSET_MAP.put("辅助社员 - 其它", "git/SIFStatic/icon/normal/146.png");
        ASSET_MAP.put("金币", "assets/image/ui/common/com_icon_03.png");
        ASSET_MAP.put("友情点", "assets/image/ui/item/com_icon_32.png");
        ASSET_MAP.put("贴纸", "assets/image/exchange_point/exchange_point_2_x.png");
        ASSET_MAP.put("称号", "assets/image/ui/common/com_icon_44.png");
        ASSET_MAP.put("背景", "assets/image/ui/common/com_icon_46.png");
        ASSET_MAP.put("学园偶像技能 - 吻(C1)", "assets/image/idol_skill/sis001_01.png");
        ASSET_MAP.put("学园偶像技能 - 香水(C2)", "assets/image/idol_skill/sis004_02.png");
        ASSET_MAP.put("学园偶像技能 - 指环(C2)", "assets/image/idol_skill/sis007_02.png");
        ASSET_MAP.put("学园偶像技能 - 十字(C3)", "assets/image/idol_skill/sis016_03.png");
        ASSET_MAP.put("学园偶像技能 - 光环(C3)", "assets/image/idol_skill/sis025_03.png");
        ASSET_MAP.put("学园偶像技能 - 面纱(C4)", "assets/image/idol_skill/sis028_04.png");
        ASSET_MAP.put("学园偶像技能 - 魅力(C4)", "assets/image/idol_skill/sis031_04.png");
        ASSET_MAP.put("学园偶像技能 - 治愈(C4)", "assets/image/idol_skill/sis032_04.png");
        ASSET_MAP.put("学园偶像技能 - 诡计(C4)", "assets/image/idol_skill/sis033_04.png");
        ASSET_MAP.put("学园偶像技能 - 眼神(C5)", "assets/image/idol_skill/sis040_05.png");
        ASSET_MAP.put("学园偶像技能 - 颤音(C5)", "assets/image/idol_skill/sis043_05.png");
        ASSET_MAP.put("学园偶像技能 - 绽放(C6)", "assets/image/idol_skill/sis052_06.png");
        ASSET_MAP.put("学园偶像技能 - 个宝(C4)", "assets/image/idol_skill/sis055_04.png");
        ASSET_MAP.put("学园偶像技能 - 九重奏(C4)", "assets/image/idol_skill/sis073_04.png");
    }

    public EffortBoxStat() {

        this.lastOpenTime = "";
        this.usualBoxTypeStatList = new ArrayList<>(5);
        this.limitedBoxTypeStatList = new ArrayList<>(5);

        // 初始化统计数据
        String[] basicCapacity = new String[] {"4000000", "2000000", "1200000", "400000", "100000"};
        String[] limitedCapacity = new String[] {"10000000", "5000000", "3000000", "1000000", "400000"};
        for (int i = 0 ; i < basicCapacity.length; i ++) {
            this.usualBoxTypeStatList.add(new BoxTypeStat(
                    basicCapacity[i],
                    0,
                    new ArrayList<>(),
                    new HashMap<>()
            ));
            this.limitedBoxTypeStatList.add(new BoxTypeStat(
                    limitedCapacity[i],
                    0,
                    new ArrayList<>(),
                    new HashMap<>()
            ));
        }

    }

    public void updateData() {
        updateData(this.usualBoxTypeStatList);
        updateData(this.limitedBoxTypeStatList);
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
