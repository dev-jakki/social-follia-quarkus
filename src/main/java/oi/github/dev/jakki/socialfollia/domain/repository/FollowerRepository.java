package oi.github.dev.jakki.socialfollia.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;

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
}
