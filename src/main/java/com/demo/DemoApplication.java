package com.demo;

import com.demo.sessionListener.SessionListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.demo")
//@Import(BaseFilter.class)//把类放进Bean中
@Import(SessionListener.class)
@MapperScan("com.demo.dao")//配置映射接口
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    //把方法的返回值对象放进Bean中
//    @Bean
//    public FilterRegistrationBean getFilter(){
//        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new BaseFilter());
//        filterRegistrationBean.addUrlPatterns("/");
//        return filterRegistrationBean;
//    }
//    @Bean
//    public HttpSessionListener getSessionListener(){
//        return new SessionListener();
//    }

}
