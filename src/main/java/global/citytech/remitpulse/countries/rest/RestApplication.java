package global.citytech.remitpulse.countries.rest;

import global.citytech.rabbit.microprofile.exceptions.mappers.*;
import global.citytech.rabbit.microprofile.filters.CorsFilter;
import global.citytech.rabbit.microprofile.filters.SecurityInterceptor;
import global.citytech.rabbit.microprofile.resources.ModuleInfoResource;
import global.citytech.rabbit.microprofile.resources.PingResource;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bipin on 5/20/19 3:09 PM.
 */
@ApplicationScoped
@ApplicationPath("/v1/countries")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses()  {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PingResource.class);
        classes.add(CountryResource.class);
        classes.add(AuditLogResource.class);
        classes.add(CountryInternalResource.class);
        classes.add(CountryDynamicFormResource.class);
        classes.add(CorsFilter.class);
        classes.add(SecurityInterceptor.class);
        classes.add(ModuleInfoResource.class);

        classes.add(AppExceptionMapper.class);
        classes.add(IllegalArgumentException.class);
        classes.add(InternalServerExceptionMapper.class);
        classes.add(JWTExceptionMapper.class);
        classes.add(MethodNotAllowedExceptionMapper.class);
        classes.add(NoSuchElementExceptionMapper.class);
        classes.add(NotAuthorizedExceptionMapper.class);
        classes.add(NullPointerExceptionMapper.class);
        classes.add(UnSupportedMediaTypeExceptionMapper.class);
        return classes;
    }
}
