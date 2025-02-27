package com.luiz.eventmanager.repo;

import com.luiz.eventmanager.dto.SubscriptionRankingItem;
import com.luiz.eventmanager.model.Event;
import com.luiz.eventmanager.model.Subscription;
import com.luiz.eventmanager.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = "SELECT COUNT(subscription_number), indication_user_id, user_name FROM tbl_subscription A INNER JOIN tbl_user B ON user_id = indication_user_id WHERE indication_user_id IS NOT NULL and event_id = :event_id GROUP BY indication_user_id, user_name ORDER BY count DESC", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("event_id") Integer event_id);
}
