package org.pk.ms.test_app.users.service;

import java.util.List;
import org.pk.ms.test_app.posts.domain.Posts;
import org.pk.ms.test_app.posts.repos.PostsRepository;
import org.pk.ms.test_app.users.domain.Users;
import org.pk.ms.test_app.users.model.UsersDTO;
import org.pk.ms.test_app.users.repos.UsersRepository;
import org.pk.ms.test_app.util.NotFoundException;
import org.pk.ms.test_app.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    public UsersService(final UsersRepository usersRepository,
            final PostsRepository postsRepository) {
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userses = usersRepository.findAll(Sort.by("id"));
        return userses.stream()
                .map(users -> mapToDTO(users, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final Long id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        return usersRepository.save(users).getId();
    }

    public void update(final Long id, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final Long id) {
        usersRepository.deleteById(id);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setEmail(users.getEmail());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setUsername(usersDTO.getUsername());
        users.setPassword(usersDTO.getPassword());
        users.setEmail(usersDTO.getEmail());
        return users;
    }

    public boolean usernameExists(final String username) {
        return usersRepository.existsByUsernameIgnoreCase(username);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Users users = usersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Posts userPostsPosts = postsRepository.findFirstByUserPosts(users);
        if (userPostsPosts != null) {
            referencedWarning.setKey("users.posts.userPosts.referenced");
            referencedWarning.addParam(userPostsPosts.getId());
            return referencedWarning;
        }
        return null;
    }

}
