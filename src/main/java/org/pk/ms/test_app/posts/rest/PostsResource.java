package org.pk.ms.test_app.posts.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.pk.ms.test_app.posts.model.PostsDTO;
import org.pk.ms.test_app.posts.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/postss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostsResource {

    private final PostsService postsService;

    public PostsResource(final PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public ResponseEntity<List<PostsDTO>> getAllPostss() {
        return ResponseEntity.ok(postsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostsDTO> getPosts(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(postsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPosts(@RequestBody @Valid final PostsDTO postsDTO) {
        final Long createdId = postsService.create(postsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePosts(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PostsDTO postsDTO) {
        postsService.update(id, postsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePosts(@PathVariable(name = "id") final Long id) {
        postsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
