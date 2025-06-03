package org.pk.ms.test_app.posts.repos;

import org.pk.ms.test_app.posts.domain.Posts;
import org.pk.ms.test_app.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostsRepository extends JpaRepository<Posts, Long> {

    Posts findFirstByUserPosts(Users users);

}
