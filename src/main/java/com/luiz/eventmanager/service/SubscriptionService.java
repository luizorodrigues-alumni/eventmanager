package com.luiz.eventmanager.service;

import com.luiz.eventmanager.dto.SubscriptionRankingByUser;
import com.luiz.eventmanager.dto.SubscriptionRankingItem;
import com.luiz.eventmanager.dto.SubscriptionResponse;
import com.luiz.eventmanager.exception.EventNotFoundException;
import com.luiz.eventmanager.exception.SubscriptionConflictException;
import com.luiz.eventmanager.exception.UserIndicatorNotFoundException;
import com.luiz.eventmanager.model.Event;
import com.luiz.eventmanager.model.Subscription;
import com.luiz.eventmanager.model.User;
import com.luiz.eventmanager.repo.EventRepo;
import com.luiz.eventmanager.repo.SubscriptionRepo;
import com.luiz.eventmanager.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private ResourceUrlProvider resourceUrlProvider;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId){
        Subscription subs = new Subscription();

        // Get event by name
        Event evt = eventRepo.findByPrettyName(eventName);
        if (evt == null){
            throw new EventNotFoundException("Event " + eventName + " does not exist.");
        }
        User recoveredUser = userRepo.findByEmail(user.getEmail());

        // Save new User if it does not exist;
        if (recoveredUser == null) {
            recoveredUser = userRepo.save(user);
        }

        User indicator = null;
        if (userId != null) {
            indicator = userRepo.findById(userId).orElse(null);
            if (indicator == null){
                throw new UserIndicatorNotFoundException("Indicator user " + userId + " does not exist.");
            }
        }

        subs.setEvent(evt);
        subs.setSubscriber(recoveredUser);
        subs.setIndication(indicator);

        Subscription tempSub = subscriptionRepo.findByEventAndSubscriber(evt, recoveredUser);
        if (tempSub != null){
            throw new SubscriptionConflictException("There is already a subscription for user " + recoveredUser.getName() + " at event" + evt.getTitle());
        }

        Subscription res = subscriptionRepo.save(subs);
        return new SubscriptionResponse(subs.getSubscriptionId(), "http://codecraft.com/subscription/"+subs.getEvent().getPrettyName()+"/"+subs.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName){
        Event evt = eventRepo.findByPrettyName(prettyName);
        if (evt == null) {
            throw new EventNotFoundException("Ranking for the " + prettyName + " event does not exist.");
        }
        return subscriptionRepo.generateRanking(evt.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId){
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().filter(i->i.userId().equals(userId)).findFirst().orElse(null);
        if (item == null){
            throw new UserIndicatorNotFoundException("There is no subscription with indications for user: " + userId);
        }
        Integer position = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).userId().equals(userId))
                .findFirst().getAsInt();
        return new SubscriptionRankingByUser(item, position + 1);
    }
}
