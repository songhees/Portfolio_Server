package pofo_server.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pofo_server.vo.SongPost;

@Slf4j
@RestController
public class PostController {

    @GetMapping("")
    public ResponseEntity<List<SongPost>> getBestSongList() {
        log.info("Retrieving best song list");
        return ResponseEntity.ok().body(List.of(SongPost.builder()
                .postId(1L)
                .songId(1L)
                .userId(UUID.randomUUID())
                .title("Best Song Post")
                .content("This is the best song post content.")
                .likeCount(100)
                .createdAt(OffsetDateTime.now())
                .updatedAt(null)
                .deletedAt(null)
                .build()));
    }

    @PostMapping("/posts")
    public ResponseEntity<String> addSongPosts() {
        log.info("Adding song post");
        return ResponseEntity.ok().body("Song Post Added");
    }
}
