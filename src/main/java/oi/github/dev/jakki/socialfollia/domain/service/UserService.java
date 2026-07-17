package oi.github.dev.jakki.socialfollia.domain.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.repository.UserRepository;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional
    public void create(User user) {
        repository.persist(user);
    }

    public Optional<User> findById(Long id) {
        return repository.findByIdOptional(id);
    }

    public PanacheQuery<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
