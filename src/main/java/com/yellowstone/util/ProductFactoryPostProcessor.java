package com.yellowstone.util;

import com.yellowstone.annotation.AutoCreate;
import com.yellowstone.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ProductFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] definitions = configurableListableBeanFactory.getBeanNamesForAnnotation(AutoCreate.class);
        for (String beanName: definitions){
            if (beanName.equals(Product.class.getSimpleName().toLowerCase())){
                configurableListableBeanFactory.getBeanDefinition(beanName).setScope("prototype");
                configurableListableBeanFactory.getBeanDefinition(beanName).setLazyInit(true);
                log.info("Success for [{}]", beanName);
            }
        }
    }
}
