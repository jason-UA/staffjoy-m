package com.example.common.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {StaffjoyConfig.class, StaffjoyAuthExceptionConfigure.class, ResourceServerConfigure.class})
public class StaffjoyAuthRestConfig {
}
