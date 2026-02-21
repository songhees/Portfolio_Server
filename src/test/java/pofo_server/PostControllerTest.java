package pofo_server;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pofo_server.vo.SongPost;

class PostControllerTest extends ApiDocumentationTest {

  @Test
  void testSelectSongPost() throws Exception {
    mockMvc
        .perform(get("/api").param("page", "1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-song-posts",
                resource(
                    ResourceSnippetParameters.builder()
                        .summary("곡 게시글 목록 조회 API")
                        .description("노래 게시글을 조회합니다.")
                        .queryParameters(
                            parameterWithName("page").description("페이지 번호 (1부터 시작)").optional(),
                            parameterWithName("size").description("페이지당 항목 수 (기본값: 10)").optional(),
                            parameterWithName("sortBy").description("정렬 기준 필드").optional(),
                            parameterWithName("sortDirection")
                                .description("정렬 방향 (asc 또는 desc)")
                                .optional(),
                            parameterWithName("search").description("검색어").optional())
                        .responseFields(
                            fieldWithPath("[]").description("노래 게시글 목록"),
                            fieldWithPath("[].postId").description("게시글 ID"),
                            fieldWithPath("[].songId").description("노래 ID"),
                            fieldWithPath("[].userId").description("작성자 UUID"),
                            fieldWithPath("[].title").description("게시글 제목"),
                            fieldWithPath("[].content").description("게시글 내용"),
                            fieldWithPath("[].likeCount").description("좋아요 수"),
                            fieldWithPath("[].createdAt").description("생성 일시"),
                            fieldWithPath("[].updatedAt").description("수정 일시").optional(),
                            fieldWithPath("[].deletedAt").description("삭제 일시").optional())
                        .build())));
  }

  @Test
  void testCreateSongPost() throws Exception {
    SongPost request =
        SongPost.builder()
            .title("New Post Title")
            .content("This is a new song post content.")
            .songId(10L)
            .userId(UUID.randomUUID())
            .build();

    mockMvc
        .perform(
            post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(
            document(
                "add-song-post",
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Song")
                        .summary("노래 게시글 추가")
                        .description("새로운 노래 게시글을 등록하고, 연결된 노래 정보를 반환받습니다.")
                        .requestFields(
                            fieldWithPath("title").description("게시글 제목"),
                            fieldWithPath("content").description("게시글 내용"),
                            fieldWithPath("songId").description("연결할 노래 ID"),
                            fieldWithPath("userId").description("작성자 UUID"),
                            fieldWithPath("postId").description("게시글 ID").optional(),
                            fieldWithPath("likeCount").description("좋아요 수").optional(),
                            fieldWithPath("createdAt").description("생성일").optional(),
                            fieldWithPath("updatedAt").description("수정일").optional(),
                            fieldWithPath("deletedAt").description("삭제일").optional())
                        .responseFields(
                            fieldWithPath("songId").description("노래 ID"),
                            fieldWithPath("trackId").description("트랙 ID"),
                            fieldWithPath("title").description("노래 제목"),
                            fieldWithPath("artistName").description("아티스트 명"),
                            fieldWithPath("albumName").description("앨범 명"),
                            fieldWithPath("releaseDate").description("발매일 (yyyy-MM-dd)"),
                            fieldWithPath("artworkUrl").description("앨범 아트워크 URL"),
                            fieldWithPath("postCount").description("이 노래에 달린 게시글 수"),
                            fieldWithPath("createdAt").description("노래 정보 생성 시간"),
                            fieldWithPath("updatedAt").description("노래 정보 수정 시간").optional(),
                            fieldWithPath("songPosts").description("이 노래에 달린 게시글 목록"),
                            fieldWithPath("songPosts[].postId").description("게시글 ID"),
                            fieldWithPath("songPosts[].songId").description("연결된 노래 ID"),
                            fieldWithPath("songPosts[].userId").description("작성자 UUID"),
                            fieldWithPath("songPosts[].title").description("게시글 제목"),
                            fieldWithPath("songPosts[].content").description("게시글 내용"),
                            fieldWithPath("songPosts[].likeCount").description("좋아요 수"),
                            fieldWithPath("songPosts[].createdAt").description("게시글 생성 시간"),
                            fieldWithPath("songPosts[].updatedAt")
                                .description("게시글 수정 시간")
                                .optional(),
                            fieldWithPath("songPosts[].deletedAt")
                                .description("게시글 삭제 시간")
                                .optional())
                        .build())));
  }
}
