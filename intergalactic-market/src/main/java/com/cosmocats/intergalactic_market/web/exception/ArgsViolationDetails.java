package com.cosmocats.intergalactic_market.web.exception;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ArgsViolationDetails {
    String fieldName;
    String reason;
}
