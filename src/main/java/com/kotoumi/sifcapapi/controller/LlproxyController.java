package com.kotoumi.sifcapapi.controller;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveDetailResponse;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.SecretBoxLog;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import com.kotoumi.sifcapapi.model.vo.service.User;
import com.kotoumi.sifcapapi.service.LlproxyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
                                        @Min(value = 1, message = "limit") int limit,
                                        @RequestParam(name = "lang", required = false) String lang) {

        log.info("userSearch keyword: {}, limit: {}, lang: {}", keyword, limit, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        List<User> userList = llproxyService.userSearch(keyword, limit, lang.toLowerCase());
        return ResponseEntity.ok(finish(userList));

    }

    @GetMapping("userInfo")
    public ResponseEntity<?> userInfo(@Min(value = 1, message = "uid") int uid,
                                      @RequestParam(name = "lang", required = false) String lang) {

        log.info("userInfo uid: {}, lang: {}", uid, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        User user = llproxyService.userInfo(uid, lang.toLowerCase());
        return ResponseEntity.ok(finish(user));

    }

    @GetMapping("liveInfo")
    public ResponseEntity<?> liveInfo(@Min(value = 1, message = "uid") int uid,
                                      @Min(value = 1, message = "page") int page,
                                      @Min(value = 1, message = "limit") int limit,
                                      @RequestParam(name = "setid", required = false) Integer setId,
                                      @RequestParam(name = "eventid", required = false) Integer eventId,
                                      @RequestParam(name = "keyword", required = false) String keyword,
                                      @RequestParam(name = "lang", required = false) String lang) {

        log.info("liveInfo uid: {}, page: {}, limit: {}, setId: {}, eventId: {}, keyword: {}, lang: {}",
                uid, page, limit, setId, eventId, keyword, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        LiveInfoResponse liveInfoResponse = llproxyService.liveInfo(uid, page, limit, setId, eventId, keyword, lang.toLowerCase());
        return ResponseEntity.ok(finish(liveInfoResponse));

    }

    @GetMapping("liveDetail")
    public ResponseEntity<?> liveDetail(@Min(value = 1, message = "id") long id,
                                        @RequestParam(name = "lang", required = false) String lang) {

        log.info("liveDetail id: {}, lang: {}", id, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        LiveDetailResponse liveDetailResponse = llproxyService.liveDetail(id, lang.toLowerCase());
        return ResponseEntity.ok(finish(liveDetailResponse));

    }

    @GetMapping("liveUnitsExport")
    public ResponseEntity<?> liveUnitsExport(@Min(value = 1, message = "id") long id,
                                             @RequestParam(name = "lang", required = false) String lang) {

        log.info("liveUnitsExport id: {}, lang: {}", id, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        List<LLHelperUnit> llHelperUnitList = llproxyService.liveUnitsExport(id, lang.toLowerCase());
        String members = JSON.toJSONString(llHelperUnitList);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(members.getBytes()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + "unit-" + id + ".sd")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(members.getBytes().length)
                .body(resource);

    }

    @GetMapping("secretBoxLog")
    public ResponseEntity<?> secretBoxLog(@Min(value = 1, message = "uid") int uid,
                                          @Min(value = 1, message = "page") int page,
                                          @Min(value = 1, message = "limit") int limit,
                                          @RequestParam(name = "type", required = false) Integer type,
                                          @RequestParam(name = "lang", required = false) String lang) {

        log.info("secretBoxLog uid: {}, page: {}, limit: {}, type: {}, lang: {}", uid, page, limit, type, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        SecretBoxLogResponse secretBoxLogResponse = llproxyService.secretBoxLog(uid, page, limit, type, lang.toLowerCase());
        return ResponseEntity.ok(finish(secretBoxLogResponse));

    }

    @GetMapping("unitsInfo")
    public ResponseEntity<?> unitsInfo(@Min(value = 1, message = "uid") int uid,
                                       @Min(value = 1, message = "page") int page,
                                       @Min(value = 1, message = "limit") int limit,
                                       @RequestParam(name = "ssr", required = false) Integer ssr,
                                       @RequestParam(name = "sr", required = false) Integer sr,
                                       @RequestParam(name = "back", required = false) Integer back,
                                       @RequestParam(name = "lang", required = false) String lang) {

        log.info("unitsInfo uid: {}, page: {}, limit: {}, ssr: {}, sr: {}, back: {}, lang: {}",
                uid, page, limit, ssr, sr, back, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        UnitsInfoResponse unitsInfoResponse = llproxyService.unitsInfo(uid, page, limit, ssr, sr, back, lang.toLowerCase());
        return ResponseEntity.ok(finish(unitsInfoResponse));

    }

    @GetMapping("unitsExport")
    public ResponseEntity<?> unitsExport(@Min(value = 1, message = "uid") int uid,
                                         @RequestParam(name = "ssr", required = false) Integer ssr,
                                         @RequestParam(name = "sr", required = false) Integer sr,
                                         @RequestParam(name = "back", required = false) Integer back,
                                         @RequestParam(name = "lang", required = false) String lang) {

        log.info("unitsExport uid: {}, ssr: {}, sr: {}, back: {}, lang: {}", uid, ssr, sr, back, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        List<LLHelperUnit> llHelperUnitList = llproxyService.unitsExport(uid, ssr, sr, back, lang.toLowerCase());
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
    public ResponseEntity<?> deckInfo(@Min(value = 1, message = "uid") int uid,
                                      @RequestParam(name = "lang", required = false) String lang) {

        log.info("deckInfo uid: {}, lang: {}", uid, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        DeckInfoResponse deckInfoResponse = llproxyService.deckInfo(uid, lang.toLowerCase());
        return ResponseEntity.ok(finish(deckInfoResponse));

    }

    @GetMapping("deckExport")
    public ResponseEntity<?> deckExport(@Min(value = 1, message = "uid") int uid,
                                        @Range(min = 1, max = 18) int unitDeckId,
                                        @RequestParam(name = "lang", required = false) String lang) {

        log.info("deckExport uid: {}, unitDeckId: {}, lang: {}", uid, unitDeckId, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        List<LLHelperUnit> llHelperUnitList = llproxyService.deckExport(uid, unitDeckId, lang.toLowerCase());
        String members = JSON.toJSONString(llHelperUnitList);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(members.getBytes()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + "unit-" + uid + "-" + unitDeckId + ".sd")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(members.getBytes().length)
                .body(resource);

    }

}
