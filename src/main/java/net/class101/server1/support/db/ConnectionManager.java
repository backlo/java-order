package net.class101.server1.support.db;

import net.class101.server1.support.exception.CanNotReadFileException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class ConnectionManager {
    private static final String LOCAL_DATABASE_PATH = "src/main/resources/db.properties";
    private static final String DRIVER_CLASS = "jdbc.driverClass";
    private static final String USER_NAME = "jdbc.username";
    private static final String URL = "jdbc.url";
    private static final String PASSWORD = "jdbc.password";

    public static DataSource getDataSource() {
        return getBasicDataSource();
    }

    private static BasicDataSource getBasicDataSource() {
        Properties properties = setProperties(ConnectionManager.LOCAL_DATABASE_PATH);

        String dbDriver = properties.getProperty(DRIVER_CLASS);
        String dbUrl = properties.getProperty(URL);
        String dbUserName = properties.getProperty(USER_NAME);
        String dbPassword = properties.getProperty(PASSWORD);

        return setBasicDataSource(dbDriver, dbUrl, dbUserName, dbPassword);
    }

    private static Properties setProperties(String path) {
        Properties properties = new Properties();
        try {
            Reader reader = new FileReader(path);
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            throw new CanNotReadFileException(e);
        }
    }

    private static BasicDataSource setBasicDataSource(String dbDriver, String dbUrl, String dbUserName, String dbPassword) {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName(dbDriver);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(dbUserName);
        basicDataSource.setPassword(dbPassword);

        return basicDataSource;
    }
}
