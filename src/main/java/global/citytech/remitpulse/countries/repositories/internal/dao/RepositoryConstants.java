package global.citytech.remitpulse.countries.repositories.internal.dao;

/**
 * @author bipin on 5/20/19 5:10 PM.
 */
public class RepositoryConstants {
    public static final String MAPPER_SEPARATOR = ".";

    private RepositoryConstants(){

    }

    public static <T> String getMapperNameSpace(Class<T> clazz){
        return clazz.getSimpleName();
    }

    public static <T> String getFullMapperMethodId(Class<T> clazz,String mapperMethod){
        return getMapperNameSpace(clazz)+MAPPER_SEPARATOR+mapperMethod;
    }
}
