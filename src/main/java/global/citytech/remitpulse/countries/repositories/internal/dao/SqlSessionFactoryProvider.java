package global.citytech.remitpulse.countries.repositories.internal.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author bipin on 5/20/19 4:29 PM.
 */
public class SqlSessionFactoryProvider {
    @Inject
    @ConfigProperty(name = "DB_DRIVER",defaultValue = "org.postgresql.Driver")
    private String dbDriver;

    @Inject
    @ConfigProperty(name = "DB_URL",defaultValue = "jdbc:mariadb://10.20.30.80:3306/city_remit?stringtype=unspecified")
    private String dbUrl;

    @Inject
    @ConfigProperty(name = "DB_USER_NAME",defaultValue = "root")
    private String dbUserName;

    @Inject
    @ConfigProperty(name = "DB_PASSWORD",defaultValue = "test@123")
    private String dbPassWord;

    private static final Logger LOG = Logger.getLogger(SqlSessionFactoryProvider.class.getName());

    @ApplicationScoped
    @Produces
    @SessionFactoryProvider
    public SqlSessionFactory produce() throws Exception {
        Properties properties = this.configDbProperties();
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            SqlSessionFactory manager = new SqlSessionFactoryBuilder().build(reader, properties);
            return manager;
        }
    }

    private Properties configDbProperties() {
        Properties properties = new Properties();
        properties.put("db.driver", dbDriver);
        properties.put("db.url", dbUrl);
        properties.put("db.username", dbUserName);
        properties.put("db.password",dbPassWord);
        return properties;
    }
}
