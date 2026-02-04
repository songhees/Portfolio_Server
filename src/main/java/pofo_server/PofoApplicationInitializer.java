package pofo_server;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import pofo_server.config.AppConfig;
import pofo_server.config.WebConfig;

public class PofoApplicationInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
