package com.kotoumi.sifcapapi.controller;

import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.Live;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author guohaohao
 */
@Slf4j
@RestController
@RequestMapping("llproxy")
@Validated
public class LlproxyController extends BaseController {

    @Resource
    private LlProxyMapper llProxyMapper;

    @GetMapping("userSearch")
    public ResponseEntity<?> userSearch(@NotBlank(message = "keyword") String keyword,
                                      @Min(value = 1, message = "limit") int limit) {

        log.info("userSearch keyword: {}, limit: {}", keyword, limit);
        List<User> userList = llProxyMapper.searchUser(keyword, limit);
        for (User user: userList) {
            user.setUid(user.getUserId());
            user.setNaviUnitInfo(new Unit(6, 100, 0));
        }
        return ResponseEntity.ok(finish(userList));

    }

    @GetMapping("userInfo")
    public ResponseEntity<?> userInfo(@Min(value = 1, message = "uid") int uid) {

        log.info("userInfo uid: {}", uid);
        User user = llProxyMapper.findUser(uid);
        user.setUid(user.getUserId());
        user.setNaviUnitInfo(new Unit(6, 100, 0));
        return ResponseEntity.ok(finish(user));

    }

    @GetMapping("liveInfo")
    public ResponseEntity<?> liveInfo(@Min(value = 1, message = "uid") int uid,
                                      @Min(value = 1, message = "page") int page,
                                      @Min(value = 1, message = "limit") int limit,
                                      @RequestParam(name = "setid", required = false) Integer setid) {

        log.info("liveInfo uid: {}, page: {}, limit: {}, setid: {}", uid, page, limit, setid);

        List<Live> liveList = new ArrayList<>();
        liveList.add(new Live(
                1,
                6669728,
                11,
                12,
                3,
                6,
                "assets/image/live/live_icon/l_jacket_002.png",
                13,
                "僕らのLIVE 君とのLIFE",
                "assets/image/live/title/m_title_001.png",
                "assets/sound/music/m_001.mp3",
                false,
                0,
                0,
                208,
                12,
                0,
                0,
                0,
                220,
                true,
                987304,
                0,
                0,
                987304,
                "2020-07-09 12:22:25",
                "2020-07-09 12:22:25"
        ));

        LiveInfoResponse liveInfoResponse = new LiveInfoResponse(
                liveList,
                1,
                1,
                1,
                1
        );
        return ResponseEntity.ok(finish(liveInfoResponse));

    }

}
