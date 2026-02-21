package pofo_server;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pofo_server.config.AppConfig;
import pofo_server.config.WebConfig;

@ExtendWith({RestDocumentationExtension.class})
@SpringJUnitWebConfig(classes = {AppConfig.class, WebConfig.class})
abstract class ApiDocumentationTest {

  protected MockMvc mockMvc;
  protected ObjectMapper objectMapper;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(
                documentationConfiguration(restDocumentation)
                    .uris()
                    .withScheme("http")
                    .withHost("localhost")
                    .withPort(8080))
            .build();
  }

  @BeforeEach
  void setObjectMapper() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());
  }
}
