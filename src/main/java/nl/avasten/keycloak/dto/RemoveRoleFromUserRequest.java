package nl.avasten.keycloak.dto;

public record RemoveRoleFromUserRequest(String userId, String roleName) {
}
