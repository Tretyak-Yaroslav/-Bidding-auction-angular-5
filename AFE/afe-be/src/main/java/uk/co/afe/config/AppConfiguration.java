package uk.co.afe.config;

import com.fasterxml.classmate.types.ResolvedObjectType;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.response.ErrorResponse;
import uk.co.afe.model.role.Actions;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Sergey Teryoshin
 * 04.03.2018 19:39
 */
@Configuration
@EnableSwagger2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
public class AppConfiguration implements WebMvcConfigurer {

    @Value("${server.http.port}")
    private int httpPort;
    @Value("${server.port}")
    private int httpsPort;

    public AppConfiguration() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Etc/GMT")));
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public TomcatServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        factory.addAdditionalTomcatConnectors(createConnection());
        return factory;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("file:./message");
        return messageSource;
    }

    private Connector createConnection() {
        final String protocol = "org.apache.coyote.http11.Http11NioProtocol";
        final Connector connector = new Connector(protocol);
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setRedirectPort(httpsPort);
        return connector;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, messages())
                .globalResponseMessage(RequestMethod.POST, messages())
                .globalResponseMessage(RequestMethod.PUT, messages())
                .globalResponseMessage(RequestMethod.DELETE, messages())
                .additionalModels(ResolvedObjectType.create(ErrorResponse.class, null, null, Collections.emptyList()))
                .additionalModels(ResolvedObjectType.create(ErrorCode.class, null, null, Collections.emptyList()))
                .additionalModels(ResolvedObjectType.create(Actions.class, null, null, Collections.emptyList()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("uk.co.afe.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Autowired
    public void converter (MappingJackson2HttpMessageConverter converter) {
        converter.setObjectMapper(new AfeObjectMapper());
    }

    private List<ResponseMessage> messages() {
        return Arrays.asList(
                forStatus(400),
                forStatus(401),
                forStatus(403),
                forStatus(404),
                forStatus(500)
        );
    }

    private ResponseMessage forStatus(int val) {
        HttpStatus s = HttpStatus.valueOf(val);
        return new ResponseMessage(s.value(), s.getReasonPhrase(), new ModelRef(ErrorResponse.class.getSimpleName()), Collections.emptyMap(), Collections.emptyList());
    }

}