package com.droppler.notification.domain;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CassandraRepository<Notification, String> {
}
