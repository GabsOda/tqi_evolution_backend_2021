package com.github.GabsOda.creditAnalysisAPI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanStatus {

    RECEIVED("received"),
    IN_ANALYSIS("inAnalysis"),
    ACCEPTED("accepted"),
    DENIED("denied");

    private final String status;

}
