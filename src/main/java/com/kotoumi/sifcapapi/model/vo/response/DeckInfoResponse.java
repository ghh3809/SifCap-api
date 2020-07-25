package com.kotoumi.sifcapapi.model.vo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kotoumi.sifcapapi.model.vo.service.Deck;
import com.kotoumi.sifcapapi.model.vo.service.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author guohaohao
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DeckInfoResponse {

    private List<Deck> deckInfo;

}
