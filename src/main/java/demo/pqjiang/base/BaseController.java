package demo.pqjiang.base;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@Scope("prototype")
public class BaseController implements ServletContextAware, InitializingBean, MessageSourceAware {

    @Autowired
    protected HttpServletRequest request;

    protected ServletContext servletContext;

    protected Locale locale;

    protected MessageSource messageSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.locale = LocaleContextHolder.getLocale();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource=messageSource;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
