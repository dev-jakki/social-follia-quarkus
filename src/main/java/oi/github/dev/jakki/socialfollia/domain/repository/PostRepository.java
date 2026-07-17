package oi.github.dev.jakki.socialfollia.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.dev.jakki.socialfollia.domain.model.Post;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> { }
