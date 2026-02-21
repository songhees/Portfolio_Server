package pofo_server.vo;

import jakarta.validation.constraints.Min;

public record Pageable(
    @Min(1) Integer page,
    @Min(1) Integer size,
    String sortBy,
    String sortDirection,
    String search) {

  public Pageable {
    if (page == null) page = 1;
    if (size == null) size = 10;
    if (sortBy == null) sortBy = "postCount";
    if (sortDirection == null) sortDirection = "desc";
  }
}
