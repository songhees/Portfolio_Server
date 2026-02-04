package pofo_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PostController {

    @GetMapping("")
    public ResponseEntity<String> getBestSongList() {
        log.info("Retrieving best song list");
        return ResponseEntity.ok().body("Best Song List");
    }

    @PostMapping("/posts")
    public ResponseEntity<String> addSongPosts() {
        log.info("Adding song post");
        return ResponseEntity.ok().body("Song Post Added");
    }
}
