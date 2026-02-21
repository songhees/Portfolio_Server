package pofo_server.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pofo_server.vo.SongPost;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDTO {
  private Long songId;
  private String trackId;
  private String title;
  private String artistName;
  private String albumName;
  private LocalDate releaseDate;
  private String artworkUrl;
  private int postCount;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  private List<SongPost> songPosts;
}
