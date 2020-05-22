package com.lab.labbook.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DataConfig {
    @Value("${api.default.user}")
    private String defaultUser;

    @Value("${api.default.user.password}")
    private String defaultPassword;

    @Value("${api.default.unbound}")
    private String defaultUnbound;

    @Value("${nbp.national.currency}")
    private String nationalCurrency;
}
