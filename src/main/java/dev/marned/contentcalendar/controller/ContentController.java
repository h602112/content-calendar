package dev.marned.contentcalendar.controller;

import dev.marned.contentcalendar.model.Content;
import dev.marned.contentcalendar.model.Status;
import dev.marned.contentcalendar.model.Type;
import dev.marned.contentcalendar.repository.ContentCollectionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {

    private final ContentCollectionRepository repository;


    public ContentController(ContentCollectionRepository repository) {
        this.repository = repository;
    }

    // make a request and find all the pieces of the content in the system
    @GetMapping("")
    public List<Content> getAll() {
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public Content findById(@PathVariable Integer id) {
        return repository.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "content was not found"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Content content) {
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Content content, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "content was not found");
        }
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
