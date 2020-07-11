package com.kotoumi.sifcapapi.controller;

import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private LlproxyService llproxyService;

    @GetMapping("userSearch")
    public ResponseEntity<?> userSearch(@NotBlank(message = "keyword") String keyword,
                                      @Min(value = 1, message = "limit") int limit) {

        log.info("userSearch keyword: {}, limit: {}", keyword, limit);
        List<User> userList = llproxyService.userSearch(keyword, limit);
        return ResponseEntity.ok(finish(userList));

    }

    @GetMapping("userInfo")
    public ResponseEntity<?> userInfo(@Min(value = 1, message = "uid") int uid) {

        log.info("userInfo uid: {}", uid);
        User user = llproxyService.userInfo(uid);
        return ResponseEntity.ok(finish(user));

    }


    @GetMapping("liveInfo")
    public ResponseEntity<?> liveInfo(@Min(value = 1, message = "uid") int uid,
                                      @Min(value = 1, message = "page") int page,
                                      @Min(value = 1, message = "limit") int limit,
                                      @RequestParam(name = "setid", required = false) Integer setId,
                                      @RequestParam(name = "eventid", required = false) Integer eventId) {

        log.info("liveInfo uid: {}, page: {}, limit: {}, setId: {}, eventId: {}", uid, page, limit, setId, eventId);
        LiveInfoResponse liveInfoResponse = llproxyService.liveInfo(uid, page, limit, setId, eventId);
        return ResponseEntity.ok(finish(liveInfoResponse));

    }



}
