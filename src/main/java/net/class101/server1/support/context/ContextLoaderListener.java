package net.class101.server1.support.context;

import net.class101.server1.support.db.ConnectionManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class ContextLoaderListener {

    public void contextInitialized() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
}
