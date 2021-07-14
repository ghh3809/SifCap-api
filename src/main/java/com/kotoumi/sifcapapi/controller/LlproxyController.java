package com.kotoumi.sifcapapi.controller;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.model.vo.response.DeckInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.DuelLiveBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.EffortBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.BoxStatResponse;
import com.kotoumi.sifcapapi.model.vo.response.LLHelperUnit;
import com.kotoumi.sifcapapi.model.vo.response.LiveDetailResponse;
import com.kotoumi.sifcapapi.model.vo.response.LiveInfoResponse;
import com.kotoumi.sifcapapi.model.vo.response.LpRecoveryLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.SecretBoxLogResponse;
import com.kotoumi.sifcapapi.model.vo.response.UnitsInfoResponse;
import com.kotoumi.sifcapapi.model.vo.service.LpRecovery;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
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
                                      @RequestParam(name = "isduel", required = false) Integer isDuel,
                                      @RequestParam(name = "lang", required = false) String lang) {

        log.info("liveInfo uid: {}, page: {}, limit: {}, setId: {}, eventId: {}, keyword: {}, isduel: {}, lang: {}",
                uid, page, limit, setId, eventId, keyword, isDuel, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        LiveInfoResponse liveInfoResponse = llproxyService.liveInfo(uid, page, limit, setId, eventId, keyword, isDuel, lang.toLowerCase());
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
                                       @RequestParam(name = "islive", required = false) Integer islive,
                                       @RequestParam(name = "lang", required = false) String lang) {

        log.info("unitsInfo uid: {}, page: {}, limit: {}, ssr: {}, sr: {}, back: {}, islive: {}, lang: {}",
                uid, page, limit, ssr, sr, back, islive, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        UnitsInfoResponse unitsInfoResponse = llproxyService.unitsInfo(uid, page, limit, ssr, sr, back, islive, lang.toLowerCase());
        return ResponseEntity.ok(finish(unitsInfoResponse));

    }

    @GetMapping("unitsExport")
    public ResponseEntity<?> unitsExport(@Min(value = 1, message = "uid") int uid,
                                         @RequestParam(name = "ssr", required = false) Integer ssr,
                                         @RequestParam(name = "sr", required = false) Integer sr,
                                         @RequestParam(name = "back", required = false) Integer back,
                                         @RequestParam(name = "islive", required = false) Integer islive,
                                         @RequestParam(name = "lang", required = false) String lang) {

        log.info("unitsExport uid: {}, ssr: {}, sr: {}, back: {}, islive: {}, lang: {}", uid, ssr, sr, back, islive, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        List<LLHelperUnit> llHelperUnitList = llproxyService.unitsExport(uid, ssr, sr, back, islive, lang.toLowerCase());
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
                                      @RequestParam(name = "islive", required = false) Integer islive,
                                      @RequestParam(name = "lang", required = false) String lang) {

        log.info("deckInfo uid: {}, islive: {}, lang: {}", uid, islive, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        DeckInfoResponse deckInfoResponse = llproxyService.deckInfo(uid, islive, lang.toLowerCase());
        return ResponseEntity.ok(finish(deckInfoResponse));

    }

    @GetMapping("deckExport")
    public ResponseEntity<?> deckExport(@Min(value = 1, message = "uid") int uid,
                                        @Range(min = 1, max = 18) int unitDeckId,
                                        @RequestParam(name = "islive", required = false) Integer islive,
                                        @RequestParam(name = "lang", required = false) String lang) {

        log.info("deckExport uid: {}, unitDeckId: {}, islive: {}, lang: {}", uid, unitDeckId, islive, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        List<LLHelperUnit> llHelperUnitList = llproxyService.deckExport(uid, unitDeckId, islive, lang.toLowerCase());
        String members = JSON.toJSONString(llHelperUnitList);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(members.getBytes()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + "unit-" + uid + "-" + unitDeckId + ".sd")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(members.getBytes().length)
                .body(resource);

    }

    @GetMapping("effortBoxLog")
    public ResponseEntity<?> effortBoxLog(@Min(value = 1, message = "uid") int uid,
                                          @Min(value = 1, message = "page") int page,
                                          @Min(value = 1, message = "limit") int limit,
                                          @RequestParam(name = "limited", required = false) Integer limited,
                                          @RequestParam(name = "lang", required = false) String lang) {

        log.info("effortBoxLog uid: {}, page: {}, limit: {}, limited: {}, lang: {}", uid, page, limit, limited, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        EffortBoxLogResponse effortBoxLogResponse = llproxyService.effortBoxLog(uid, page, limit, limited, lang.toLowerCase());
        return ResponseEntity.ok(finish(effortBoxLogResponse));

    }

    @GetMapping("effortBoxStat")
    public ResponseEntity<?> effortBoxStat(@Min(value = 0, message = "uid") int uid,
                                           @RequestParam(name = "limited", required = false) Integer limited) {

        log.info("effortBoxStat uid: {}, limited: {}", uid, limited);
        BoxStatResponse boxStatResponse = llproxyService.effortBoxStat(uid, limited);
        return ResponseEntity.ok(finish(boxStatResponse));

    }

    @GetMapping("duelLiveBoxLog")
    public ResponseEntity<?> duelLiveBoxLog(@Min(value = 1, message = "uid") int uid,
                                            @Min(value = 1, message = "page") int page,
                                            @Min(value = 1, message = "limit") int limit,
                                            @RequestParam(name = "lang", required = false) String lang) {

        log.info("duelLiveBoxLog uid: {}, page: {}, limit: {}, lang: {}", uid, page, limit, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        DuelLiveBoxLogResponse duelLiveBoxLogResponse = llproxyService.duelLiveBoxLog(uid, page, limit, lang.toLowerCase());
        return ResponseEntity.ok(finish(duelLiveBoxLogResponse));

    }

    @GetMapping("duelLiveBoxStat")
    public ResponseEntity<?> duelLiveBoxStat(@Min(value = 0, message = "uid") int uid) {

        log.info("duelLiveBoxStat uid: {}", uid);
        BoxStatResponse boxStatResponse = llproxyService.duelLiveBoxStat(uid);
        return ResponseEntity.ok(finish(boxStatResponse));

    }

    @GetMapping("lpRecoveryLog")
    public ResponseEntity<?> lpRecoveryLog(@Min(value = 1, message = "uid") int uid,
                                           @Min(value = 1, message = "page") int page,
                                           @Min(value = 1, message = "limit") int limit,
                                           @RequestParam(name = "loveca", required = false) Integer loveca,
                                           @RequestParam(name = "lang", required = false) String lang) {

        log.info("lpRecovery uid: {}, page: {}, limit: {}, loveca: {}, lang: {}", uid, page, limit, loveca, lang);
        if (StringUtils.isBlank(lang)) {
            lang = "CN";
        }
        LpRecoveryLogResponse lpRecoveryLogResponse = llproxyService.lpRecoveryLog(uid, page, limit, loveca, lang.toLowerCase());
        return ResponseEntity.ok(finish(lpRecoveryLogResponse));

    }

}
