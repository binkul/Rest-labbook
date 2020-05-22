package com.lab.labbook.config;

import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.User;
import com.lab.labbook.repository.CurrencyRateRepository;
import com.lab.labbook.repository.SeriesRepository;
import com.lab.labbook.repository.UserRepository;
import com.lab.labbook.service.CurrencyRateService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class ApplicationConfig {

    private final DataConfig dataConfig;
    private final UserRepository userRepository;
    private final SeriesRepository seriesRepository;
    private final CurrencyRateService currencyRateService;
    private final CurrencyRateRepository currencyRateRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void fillConstantData() {
        addDefaultUser();
        addDefaultSeries();
        updateExchange();
    }

    private void addDefaultUser() {
        if(!userRepository.existsByLogin(dataConfig.getDefaultUser())) {
            User user = new User.UserBuilder()
                    .name(dataConfig.getDefaultUser())
                    .lastName(dataConfig.getDefaultUser())
                    .login(dataConfig.getDefaultUser())
                    .email(dataConfig.getDefaultUser())
                    .password(dataConfig.getDefaultPassword())
                    .blocked(false)
                    .observer(false)
                    .role(Role.ADMIN.name())
                    .build();
            userRepository.save(user);
        }
    }

    private void addDefaultSeries() {
        if (!seriesRepository.existsByTitle(dataConfig.getDefaultUnbound())) {
            Series series = new Series(dataConfig.getDefaultUnbound());
            seriesRepository.save(series);
        }
    }

    public void updateExchange() {
        currencyRateService.updateExchange();
        currencyRateService.updateCommercial();
        currencyRateService.updateNationalCurrency();
    }
}
