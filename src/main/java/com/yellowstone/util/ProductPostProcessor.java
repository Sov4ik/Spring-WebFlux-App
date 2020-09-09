package com.yellowstone.util;

import com.yellowstone.annotation.AutoCreate;
import com.yellowstone.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.UUID;

@Component
@Slf4j
public class ProductPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Product) {

            log.info("Bean '[{}]' before initialization", beanName);
            if (bean.getClass().isAnnotationPresent(AutoCreate.class)) {

                try {
                    for (Field field: bean.getClass().getDeclaredFields()){

                        if (field.getType().isAssignableFrom(UUID.class)){
                            field.setAccessible(true);
                            field.set(bean, UUID.randomUUID());
                        } else if (field.getType().isAssignableFrom(String.class)) {
                            field.setAccessible(true);
                            field.set(bean, RandomStringUtils.random(5, true, true));
                        } else if (field.getType().isAssignableFrom(Integer.class)){
                            field.setAccessible(true);
                            field.set(bean, (int) (1+Math.random()*100));
                        } else if (field.getType().isAssignableFrom(Double.class)){
                            field.setAccessible(true);
                            field.set(bean, 1+Math.random()*100);
                        }

                    }
                }catch (IllegalAccessException e){
                    log.info("Injection Error");
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
