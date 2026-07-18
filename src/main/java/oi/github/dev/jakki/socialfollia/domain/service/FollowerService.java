package oi.github.dev.jakki.socialfollia.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.domain.repository.FollowerRepository;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository repository;

    @Transactional
    public void followerUser(Follower follower) {
        repository.persist(follower);
    }

    public Optional<Follower> findById(Long id) {
        return repository.findByIdOptional(id);
    }

    public Optional<Follower> findByFollowerIdAndUserId(Long followerId, Long userId) {
        return repository.findByFollowerIdAndUserId(followerId, userId);
    }
}
