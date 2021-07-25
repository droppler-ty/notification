package com.droppler.notification.config;

import com.droppler.notification.NotificationApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.contact-points:placeholder}")
    private String contactPoints;

    @Value("${cassandra.port:0000}")
    private int port;

    @Value("${cassandra.keyspace-name:placeholder}")
    private String keySpace;

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keySpace)
                        .ifNotExists()
                        .withSimpleReplication();
        return List.of(specification);
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{NotificationApplication.class.getPackage().getName()};
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
}
