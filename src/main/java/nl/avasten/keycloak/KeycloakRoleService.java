package nl.avasten.keycloak;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface KeycloakRoleService {

    void assignRole(String userId,String roleName);

    List<RoleRepresentation> getRoles();
}
