package com.example.account.configure;

import com.example.common.configure.StaffjoyConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(StaffjoyConfig.class)
@Configuration
public class AppConfig {
}
