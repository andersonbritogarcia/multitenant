package tech.andersonbrito.app.infrastructure.multitenancy.db;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Component
public class ConnectionProvider implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    private final DataSource dataSource;

    public ConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection("admin");
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantSchema) throws SQLException {
        var connection = dataSource.getConnection();
        connection.setSchema(tenantSchema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantSchema, Connection connection) throws SQLException {
        connection.setSchema("admin");
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        throw new UnsupportedOperationException("Unimplemented method 'unwrap'.");
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
