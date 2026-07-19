package oi.github.dev.jakki.socialfollia.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.domain.repository.FollowerRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository repository;

    @Transactional
    public void followUser(Follower follower) {
        repository.persist(follower);
    }

    public Optional<Follower> findByFollowerIdAndUserId(Long followerId, Long userId) {
        return repository.findByFollowerIdAndUserId(followerId, userId);
    }

    public List<Follower> findByUserId(Long userId) {
        return repository.findByUser(userId);
    }

    @Transactional
    public void unfollowUser(Follower follower) {
        repository.delete(follower);
    }
}
