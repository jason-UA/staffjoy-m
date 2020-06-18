package com.example.company.config;

import com.example.common.configure.StaffjoyAuthRestConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(StaffjoyAuthRestConfig.class)
@Configuration
public class AppConfig {
}
