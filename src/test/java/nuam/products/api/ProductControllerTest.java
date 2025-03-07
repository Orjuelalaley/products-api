package nuam.products.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nuam.products.api.dto.request.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
class ProductControllerTest {

    private final static String URL = "/products";

    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createProduct() throws Exception{
        ProductDto productDto = createAProductDto();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(mapToJson(productDto)))
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void getAllProducts() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getProductById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/2")
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void ErrorGetProductById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/100")
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void updateProduct() throws Exception {
        ProductDto productDto = updateAProductDto();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL + "/2")
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(mapToJson(productDto)))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteProduct() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/2")
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andReturn();
        assertEquals(204, result.getResponse().getStatus());
    }


    private ProductDto createAProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 1");
        productDto.setDescription("Description of product 1");
        productDto.setPrice(BigDecimal.valueOf(100.0));
        productDto.setCreatedAt(LocalDateTime.now());
        return productDto;
    }

    private ProductDto updateAProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 1");
        productDto.setDescription("Description of product updated");
        productDto.setPrice(BigDecimal.valueOf(10000.0));
        productDto.setCreatedAt(LocalDateTime.now());
        return productDto;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }

}