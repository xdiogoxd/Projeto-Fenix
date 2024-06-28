package com.Projeto.Fenix.integrationtest;

import com.Projeto.Fenix.config.TestConfig;
import com.Projeto.Fenix.integrationtest.testcontainers.AbsctractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbsctractIntegrationTest {

    @DisplayName("JUnit test for Should display Swagger UI page")
    @Test
    void testShouldDisplaySwaggerUiPage() {
        var content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfig.SERVER_PORT)
                .when()
                    .get()
                .then()
                .statusCode(200)
                    .extract()
                .body()
                    .asString();
        assertTrue(content.contains("Swagger UI"));
    }


}
