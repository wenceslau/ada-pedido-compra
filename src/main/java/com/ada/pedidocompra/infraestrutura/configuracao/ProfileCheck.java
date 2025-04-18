package com.ada.pedidocompra.infraestrutura.configuracao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ProfileCheck implements ApplicationRunner {

    private final Logger logger = Logger.getLogger(ProfileCheck.class.getName());

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Override
    public void run(ApplicationArguments args) {
        if (activeProfile.isBlank()) {
            throw new IllegalStateException("❌ No active Spring profile set! Use --spring.profiles.active=dev or prod.");
        }
        logger.info("✅ Active Spring profile: " + activeProfile);
    }
}
