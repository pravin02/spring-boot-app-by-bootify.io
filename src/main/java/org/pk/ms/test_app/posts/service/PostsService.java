package org.pk.ms.test_app.posts.service;

import java.util.List;
import org.pk.ms.test_app.posts.domain.Posts;
import org.pk.ms.test_app.posts.model.PostsDTO;
import org.pk.ms.test_app.posts.repos.PostsRepository;
import org.pk.ms.test_app.users.domain.Users;
import org.pk.ms.test_app.users.repos.UsersRepository;
import org.pk.ms.test_app.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

    public PostsService(final PostsRepository postsRepository,
            final UsersRepository usersRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }

    public List<PostsDTO> findAll() {
        final List<Posts> postses = postsRepository.findAll(Sort.by("id"));
        return postses.stream()
                .map(posts -> mapToDTO(posts, new PostsDTO()))
                .toList();
    }

    public PostsDTO get(final Long id) {
        return postsRepository.findById(id)
                .map(posts -> mapToDTO(posts, new PostsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PostsDTO postsDTO) {
        final Posts posts = new Posts();
        mapToEntity(postsDTO, posts);
        return postsRepository.save(posts).getId();
    }

    public void update(final Long id, final PostsDTO postsDTO) {
        final Posts posts = postsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(postsDTO, posts);
        postsRepository.save(posts);
    }

    public void delete(final Long id) {
        postsRepository.deleteById(id);
    }

    private PostsDTO mapToDTO(final Posts posts, final PostsDTO postsDTO) {
        postsDTO.setId(posts.getId());
        postsDTO.setPost(posts.getPost());
        postsDTO.setUserPosts(posts.getUserPosts() == null ? null : posts.getUserPosts().getId());
        return postsDTO;
    }

    private Posts mapToEntity(final PostsDTO postsDTO, final Posts posts) {
        posts.setPost(postsDTO.getPost());
        final Users userPosts = postsDTO.getUserPosts() == null ? null : usersRepository.findById(postsDTO.getUserPosts())
                .orElseThrow(() -> new NotFoundException("userPosts not found"));
        posts.setUserPosts(userPosts);
        return posts;
    }

}
