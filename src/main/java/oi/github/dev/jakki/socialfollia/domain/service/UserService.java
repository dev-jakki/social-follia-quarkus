package oi.github.dev.jakki.socialfollia.domain.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.repository.UserRepository;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.UserDTO;

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

    @Transactional
    public void update(Long id, UserDTO dto) {
        Optional<User> userOptional = repository.findByIdOptional(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        User user = userOptional.get();
        user.setName(dto.name());
        user.setAge(dto.age());

        /*
          - Como o metodo está anotado com @Transactional, o Hibernate detecta que a entidade user
          - foi modificada (dirty checking) e executa automaticamente um UPDATE no final da transação
        */
    }
}
