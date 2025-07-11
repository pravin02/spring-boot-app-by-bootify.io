package org.pk.ms.test_app.users.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.pk.ms.test_app.users.model.UsersDTO;
import org.pk.ms.test_app.users.service.UsersService;
import org.pk.ms.test_app.util.ReferencedException;
import org.pk.ms.test_app.util.ReferencedWarning;
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
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersResource {

    private final UsersService usersService;

    public UsersResource(final UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUsers(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(usersService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUsers(@RequestBody @Valid final UsersDTO usersDTO) {
        final Long createdId = usersService.create(usersDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUsers(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UsersDTO usersDTO) {
        usersService.update(id, usersDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsers(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = usersService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
