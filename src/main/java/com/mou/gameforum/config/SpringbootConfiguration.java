package com.mou.gameforum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyBatisPlusConfiguration.class)
public class SpringbootConfiguration {
}
