package com.henriquenascimento.demo.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.profiles.ProfileFile;

import java.nio.file.Paths;

@Configuration
@Data
public class AwsConfig {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public ProfileFile getConfig() {
        return ProfileFile.builder()
                .type(ProfileFile.Type.CONFIGURATION)
                .content(Paths.get(System.getProperty("user.home"), ".aws", "config"))
                .build();
    }

    public String getRegion() {
        return getConfig().profile(activeProfile)
                .flatMap(profile -> profile.property("region"))
                .orElseThrow(() -> new RuntimeException("Region not found for profile: " + activeProfile));
    }

}
