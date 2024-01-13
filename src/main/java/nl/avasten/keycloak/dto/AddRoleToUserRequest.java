package nl.avasten.keycloak.dto;

public record AddRoleToUserRequest(String userId, String roleName) {
}
