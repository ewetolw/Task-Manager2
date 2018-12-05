package com.pd.eweltol.taskManager2;


import com.pd.eweltol.taskManager2.configuration.MvcConfig;
import com.pd.eweltol.taskManager2.configuration.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({SecurityConfig.class, MvcConfig.class})
public class JavaConfig {
}
