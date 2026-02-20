package pofo_server;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.epages.restdocs.apispec.ResourceSnippetParameters;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

class PostControllerTest extends ApiDocumentationTest {
    
    @Test
    void testCreatePost() throws Exception {
        mockMvc.perform(
            get("/api")
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(
            document(
                "cart-get",
                resource(ResourceSnippetParameters.builder()
                    .description("Get a cart by id")
                    .pathParameters(
                        parameterWithName("id").description("the cart id"))
                    .responseFields(
                        fieldWithPath("total").description("Total amount of the cart."),
                        fieldWithPath("products").description("The product line item of the cart."),
                        subsectionWithPath("products[]._links.product").description("Link to the product."),
                        fieldWithPath("products[].quantity").description("The quantity of the line item."),
                        subsectionWithPath("products[].product").description("The product the line item relates to."),
                        subsectionWithPath("_links").description("Links section."))
                    .build()
                )
            )
        );
    }    
}
