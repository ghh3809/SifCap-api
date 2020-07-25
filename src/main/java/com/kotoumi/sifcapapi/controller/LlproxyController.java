package com.kotoumi.sifcapapi.controller;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
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
                                      @RequestParam(name = "eventid", required = false) Integer eventId,
                                      @RequestParam(name = "keyword", required = false) String keyword) {

        log.info("liveInfo uid: {}, page: {}, limit: {}, setId: {}, eventId: {}, keyword: {}",
                uid, page, limit, setId, eventId, keyword);
        LiveInfoResponse liveInfoResponse = llproxyService.liveInfo(uid, page, limit, setId, eventId, keyword);
        return ResponseEntity.ok(finish(liveInfoResponse));

    }

    @GetMapping("secretBoxLog")
    public ResponseEntity<?> secretBoxLog(@Min(value = 1, message = "uid") int uid,
                                          @Min(value = 1, message = "page") int page,
                                          @Min(value = 1, message = "limit") int limit,
                                          @RequestParam(name = "type", required = false) Integer type) {

        log.info("secretBoxLog uid: {}, page: {}, limit: {}, type: {}", uid, page, limit, type);
        SecretBoxLogResponse secretBoxLogResponse = llproxyService.secretBoxLog(uid, page, limit, type);
        return ResponseEntity.ok(finish(secretBoxLogResponse));

    }

    @GetMapping("unitsInfo")
    public ResponseEntity<?> unitsInfo(@Min(value = 1, message = "uid") int uid,
                                       @Min(value = 1, message = "page") int page,
                                       @Min(value = 1, message = "limit") int limit) {

        log.info("unitsInfo uid: {}, page: {}, limit: {}", uid, page, limit);
        UnitsInfoResponse unitsInfoResponse = llproxyService.unitsInfo(uid, page, limit);
        return ResponseEntity.ok(finish(unitsInfoResponse));

    }

    @GetMapping("unitsExport")
    public ResponseEntity<?> unitsExport(@Min(value = 1, message = "uid") int uid) {

        log.info("unitsExport uid: {}", uid);
        List<LLHelperUnit> llHelperUnitList = llproxyService.unitsExport(uid);
        String members = String.format("{\"version\":103,\"team\":[],\"gemstock\":{},\"submember\":%s}",
                JSON.toJSONString(llHelperUnitList));
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(members.getBytes()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + "submembers-" + uid + ".sd")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(members.getBytes().length)
                .body(resource);

    }

    @GetMapping("deckInfo")
    public ResponseEntity<?> deckInfo(@Min(value = 1, message = "uid") int uid) {

        log.info("deckInfo uid: {}", uid);
        DeckInfoResponse deckInfoResponse = llproxyService.deckInfo(uid);
        return ResponseEntity.ok(finish(deckInfoResponse));

    }

    @GetMapping("deckExport")
    public ResponseEntity<?> deckExport(@Min(value = 1, message = "uid") int uid,
                                         @Range(min = 1, max = 18) int unitDeckId) {

        log.info("deckExport uid: {}, unitDeckId: {}", uid, unitDeckId);
        List<LLHelperUnit> llHelperUnitList = llproxyService.deckExport(uid, unitDeckId);
        String members = JSON.toJSONString(llHelperUnitList);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(members.getBytes()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + "unit-" + uid + "-" + unitDeckId + ".sd")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(members.getBytes().length)
                .body(resource);

    }

}
