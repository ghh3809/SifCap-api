package com.kotoumi.sifcapapi.controller;

import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

}
