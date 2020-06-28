package com.lab.labbook.client.nbp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BankConfig {

    @Value("${nbp.base.url}")
    private String baseNbpUrl;
}
