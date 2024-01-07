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
class UsersResourceTest {

    @Test
    void testUsersEndpointWithRoleUser() {
        given().auth().oauth2(getAccessToken("alice", Set.of("user")))
                .when().get("/api/users/me")
                .then()
                .statusCode(200).body(equalTo("Access for name alice is granted."));
    }

    @Test
    void testUsersEndpointWithRoleHenk() {
        given().auth().oauth2(getAccessToken("alice", Set.of("henk")))
                .when().get("/api/users/me")
                .then()
                .statusCode(200).body(equalTo("Access for name alice is granted."));
    }

    @Test
    void testSubjectEndpoint() {
        given().auth().oauth2(getAccessToken("alice", Set.of("henk")))
                .when().get("/api/users/subject")
                .then()
                .statusCode(200).body(equalTo("Access for subject 123456 is granted."));
    }

    @Test
    void testUsersEndpointWithoutAuth() {
        given()
                .when().get("/api/users/me")
                .then()
                .statusCode(401);
    }

}