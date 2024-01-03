package nl.avasten;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.oidc.server.OidcWiremockTestResource;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.quarkus.test.oidc.server.OidcWiremockTestResource.getAccessToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@QuarkusTestResource(OidcWiremockTestResource.class)
class AdminResourceTest {

    @Test
    void getAdminResourceWithCorrectRole() {
        given().auth().oauth2(getAccessToken("admin", Set.of("admin")))
                .when().get("/api/admin")
                .then()
                .statusCode(200).body(equalTo("granted"));
    }

    @Test
    void getAdminResourceWithWrongRole() {
        given().auth().oauth2(getAccessToken("admin", Set.of("wrong")))
                .when().get("/api/admin")
                .then()
                .statusCode(403);
    }

    @Test
    void getAdminResourceWithoutAuth() {
        given()
                .when().get("/api/admin")
                .then()
                .statusCode(401);
    }
}