package com.kotoumi;

import com.kotoumi.sifcapapi.filter.LoggerFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 应用程序的入口
 *
 * @author java-cmc
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.kotoumi.sifcapapi.dao.mapper")
public class App {

    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        int timeout = (int) TimeUnit.SECONDS.toMillis(10);
        factory.setConnectTimeout(timeout);
        factory.setReadTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    @Bean
    public FilterRegistrationBean<LoggerFilter> loggerFilterRegistration() {
        LoggerFilter loggerFilter = new LoggerFilter();
        FilterRegistrationBean<LoggerFilter> registration = new FilterRegistrationBean<>(loggerFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(App.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}
