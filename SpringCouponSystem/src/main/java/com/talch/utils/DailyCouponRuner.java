package com.talch.utils;

import com.talch.DailyCouponExpirationTask;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DailyCouponRuner implements CommandLineRunner {
    private final DailyCouponExpirationTask daily;

    @Override
    public void run(String... args) throws Exception {
        daily.start();

    }
}
