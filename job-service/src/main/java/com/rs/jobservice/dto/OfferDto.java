package com.rs.jobservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rs.jobservice.enums.OfferStatus;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDto {
    private String id;
    private String userId;
    private String advertId;
    private int offeredPrice;
    private OfferStatus status;
}
