package oi.github.dev.jakki.socialfollia.domain.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.model.Post;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.repository.PostRepository;

@ApplicationScoped
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    @Transactional
    public void create(Post post) {
        repository.persist(post);
    }

    public PanacheQuery<Post> find(User user) {
        return repository.find("user", Sort.by("createdAt", Sort.Direction.Descending), user);
    }
}
