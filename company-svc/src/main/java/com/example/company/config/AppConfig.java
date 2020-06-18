package com.example.company.config;

import com.example.common.configure.StaffjoyRestConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(StaffjoyRestConfig.class)
@Configuration
public class AppConfig {
}
