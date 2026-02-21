package pofo_server.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pofo_server.dto.SongDTO;
import pofo_server.vo.Pageable;
import pofo_server.vo.SongPost;

@Slf4j
@RestController
public class PostController {

  @GetMapping("")
  public ResponseEntity<List<SongPost>> getBestSongList(@Valid @ModelAttribute Pageable pageable) {
    log.info("Retrieving best song list");
    return ResponseEntity.ok()
        .body(
            List.of(
                SongPost.builder()
                    .postId(1L)
                    .songId(1L)
                    .userId(UUID.randomUUID())
                    .title("Best Song Post")
                    .content("This is the best song post content.")
                    .likeCount(100)
                    .createdAt(OffsetDateTime.now())
                    .build()));
  }

  @PostMapping("/posts")
  public ResponseEntity<SongDTO> addSongPosts(@RequestBody SongPost songPost) {
    log.info("Adding song post");
    return ResponseEntity.ok()
        .body(
            SongDTO.builder()
                .songId(1L)
                .trackId("track123")
                .title("Song Title")
                .artistName("Artist Name")
                .albumName("Album Name")
                .releaseDate(LocalDate.now())
                .artworkUrl("http://example.com/artwork.jpg")
                .postCount(1)
                .createdAt(OffsetDateTime.now())
                .songPosts(
                    List.of(
                        SongPost.builder()
                            .postId(1L)
                            .songId(1L)
                            .userId(UUID.randomUUID())
                            .title("Best Song Post")
                            .content("This is the best song post content.")
                            .likeCount(100)
                            .createdAt(OffsetDateTime.now())
                            .updatedAt(null)
                            .deletedAt(null)
                            .build()))
                .build());
  }
}
