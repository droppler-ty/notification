package com.droppler.notification.domain;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsByCustomerIdRepository extends CassandraRepository<NotificationsByCustomerId, Long> {
}
