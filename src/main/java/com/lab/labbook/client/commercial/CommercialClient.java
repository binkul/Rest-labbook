package com.lab.labbook.client.commercial;

import com.lab.labbook.client.nbp.BankClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Component
public class CommercialClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankClient.class);

    private final RestTemplate restTemplate;
    private final CommercialConfig commercialConfig;

    public CommercialDto getCommercialRates() {
        try {
            CommercialDto response = restTemplate.getForObject(getCommercialUrl(), CommercialDto.class);
            return ofNullable(response).orElseGet(CommercialDto::new);
        } catch (RestClientException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new CommercialDto();
        }
    }

    private URI getCommercialUrl() {
        return UriComponentsBuilder.fromHttpUrl(commercialConfig.getBaseCommercialUrl())
                .queryParam("base", commercialConfig.getNationalCurrency())
                .build().encode().toUri();
    }
}
