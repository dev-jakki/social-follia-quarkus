package oi.github.dev.jakki.socialfollia.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.dev.jakki.socialfollia.domain.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> { }
