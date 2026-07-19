package oi.github.dev.jakki.socialfollia.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public Optional<Follower> findByFollowerIdAndUserId(Long followerId, Long userId) {
        return find(
                "follower.id = ?1 and user.id = ?2",
                followerId,
                userId
        ).firstResultOptional();
    }

    public List<Follower> findByUser(Long userId) {
        PanacheQuery<Follower> query = find("user.id", userId);
        return query.list();
    }
}
