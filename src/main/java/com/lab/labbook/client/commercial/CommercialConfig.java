package com.lab.labbook.client.commercial;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CommercialConfig {

    @Value("${com.base.url}")
    private String baseCommercialUrl;

    @Value("${nbp.national.currency}")
    private String nationalCurrency;
}
