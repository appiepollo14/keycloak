package nl.avasten.keycloak;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.client.api.WebClientApplicationException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class KeyCloakRoleServiceImpl implements KeycloakRoleService {

    @Inject
    Keycloak keycloak;

    @Inject
    KeyCloakUserService keyCloakUserService;

    //    @ConfigProperty
    String realm = "quarkus";

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource userResource = keyCloakUserService.getUserResourceById(userId);
        RolesResource rolesResource = getRolesResource();
        try {
            RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
            RoleMappingResource currentRoles = userResource.roles();
            if (currentRoles.getAll().getRealmMappings().contains(representation)) {
                System.out.println("User has role: " + roleName + " already!");
            } else {
                userResource.roles().realmLevel().add(Collections.singletonList(representation));
            }
        } catch (WebClientApplicationException e) {
            System.out.println("Role not found!");
        }

    }


    @Override
    public List<RoleRepresentation> getRoles() {
        return getRolesResource().list();
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }

}
