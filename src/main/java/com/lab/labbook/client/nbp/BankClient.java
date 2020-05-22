package com.lab.labbook.client.nbp;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BankClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankClient.class);

    private final RestTemplate restTemplate;
    private final BankConfig bankConfig;

    public List<BankDto> getBankRates() {
        try {
            HttpEntity entity = createHttpEntity("Accept", "application/json");
            ResponseEntity<BankDto[]> response = restTemplate.exchange(getBankUrl(), HttpMethod.GET, entity, BankDto[].class);
            BankDto[] bankDto = response.getBody();
            return Arrays.asList(Optional.ofNullable(bankDto).orElseGet(() -> new BankDto[0]));
        } catch (RestClientException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }

    private URI getBankUrl() {
        return UriComponentsBuilder.fromHttpUrl(bankConfig.getBaseNbpUrl())
                .build().encode().toUri();
    }

    private HttpEntity createHttpEntity(String title, String content)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(title, content);
        return new HttpEntity(headers);
    }
}
