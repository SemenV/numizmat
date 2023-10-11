package source.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.springframework.boot.actuate.endpoint.web.servlet.AdditionalHealthEndpointPathsWebMvcHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import jakarta.servlet.Servlet;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;
import org.springframework.web.socket.server.support.WebSocketHandlerMapping;
@Configuration
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

	
	
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public WebSocketHandlerMapping WebSocketHandlerMapping() {
    	WebSocketHandlerMapping wsgm = new WebSocketHandlerMapping();
    	Map<String, Object> m = new HashMap<String,Object>();
    	m.put("/", webSocketHttpRequestHandler() );
    	
    	wsgm.setUrlMap(m);
    	

		return wsgm;
    }
    
  @Bean
  public HttpRequestHandler  webSocketHttpRequestHandler() {
	  return new WebSocketHttpRequestHandler(myHandler());
  }
  
//  @Bean
//  public HttpRequestHandlerAdapter  qwehttpRequestHandlerAdapter() {
//	  return new HttpRequestHandlerAdapter();
//  }
  
  @Bean
  public MyHandler myHandler() {
	  
	  return new MyHandler();
  }
  
    
    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet servlet=new DispatcherServlet();
        servlet.setDetectAllHandlerMappings(true);
        servlet.setDetectAllHandlerAdapters(true);
        return  servlet; 
    }

    @Bean
    public ServletRegistrationBean<Servlet> dispatcherServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(dispatcherServlet(), "/*");
        registrationBean
                .setName("hellowDispServ");

        return registrationBean;
    }

    @Bean
    public DispatcherServletPath DispatcherServletPath() {
    	
    	
    	return new DispatcherServletPath() {
			
			@Override
			public String getPath() {
				
				return "/*";
			}
		}; 
    	
    }
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}