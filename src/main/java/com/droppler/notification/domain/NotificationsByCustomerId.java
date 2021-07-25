package com.droppler.notification.domain;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;

@Table("notificationsbycustomerid")
public class NotificationsByCustomerId {

    @PrimaryKey("customerid")
    private long customerId;
    @Column("notificationids")
    private Set<String> notificationIds;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Set<String> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(Set<String> notificationIds) {
        this.notificationIds = notificationIds;
    }
}
