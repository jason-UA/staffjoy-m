package com.example.mail.config;

import com.example.common.configure.StaffjoyConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = StaffjoyConfig.class)
public class AppConfig {

}
