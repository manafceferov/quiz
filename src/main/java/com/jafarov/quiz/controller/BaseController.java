package com.jafarov.quiz.controller;

import com.jafarov.quiz.service.BaseService;
import com.jafarov.quiz.util.session.AuthSessionData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BaseController implements ApplicationContextAware {

    public AuthSessionData authSessionData;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        this.authSessionData = applicationContext.getBean(AuthSessionData.class);
        BaseService baseService = applicationContext.getBean("baseService",BaseService.class);
    }
}
