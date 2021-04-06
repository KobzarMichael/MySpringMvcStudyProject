
package ru.kobzar.springcourse.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


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


    //все запросы юзера посылаем на диспатчерсервлет по указанному адресу
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    //так как html 5 не работает с patch & delete запросами, то мы будем обходить это ограничение с пом. фильтра
    // если в кратце, то th сам создает скрытое поле в html отображении, в котором указывает, что данный запрос
    // по факту будет являться патчем или делитом, но в изначально будет подаваться как пост запрос
    //фильтр с спринге уже есть, ниже два метода его представляющие
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}