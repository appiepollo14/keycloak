package nl.avasten.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class KeyCloakTokenService {

    private String realm = "quarkus";
    private String authServerUri = "http://localhost:60452";
    private HttpClient client = HttpClient.newHttpClient();

    @Inject
    private ObjectMapper mapper;

    public String getFUNUserToken() {

        try {
            URI authorizationURI = new URIBuilder(authServerUri + "/realms/" + realm + "/protocol/openid-connect/token").build();
            Map<String, String> formData = new HashMap<>();
            formData.put("grant_type", "password");
            formData.put("client_id", "backend-service");
            formData.put("client_secret", "secret");
            formData.put("username", "alice");
            formData.put("password", "alice");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(authorizationURI)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (status code 200)
            if (response.statusCode() == 200) {

                // Access the "access_token" value
                String accessToken = mapper.readTree(response.body()).get("access_token").toString();

                // Print or use the accessToken as needed
                System.out.println("Access Token: " + accessToken);
                return accessToken;
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (!formBodyBuilder.isEmpty()) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}
