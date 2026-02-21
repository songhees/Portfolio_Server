package pofo_server.vo;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
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
}
