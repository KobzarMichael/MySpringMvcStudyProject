
package ru.kobzar.springcourse.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MySpringMVCDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    //тут указываем спригнконфиг
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }


    //все запромы юзера посылаем на диспатчерсервлет по указанному адресу
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}