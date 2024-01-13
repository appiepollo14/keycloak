package nl.avasten;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import nl.avasten.keycloak.KeyCloakUserService;
import nl.avasten.keycloak.KeycloakRoleService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Path("/api/v1/keycloak")
public class KeyCloakAdminResource {

    @Inject
    KeyCloakUserService keyCloakUserService;

    @Inject
    KeycloakRoleService keycloakRoleService;

    @GET
    @Path("/roles")
    public List<RoleRepresentation> getRoles() {
        return keycloakRoleService.getRoles();
    }

    @GET
    @Path("/users")
    public List<UserRepresentation> getUsers() {
        return keyCloakUserService.getUsers();
    }

    @GET
    @Path("/administerRole")
    public Response addRoleToUser(@QueryParam("roleName") String roleName, @QueryParam("userId") String userId) {
        keycloakRoleService.assignRole(userId, roleName);
        return Response.accepted().build();
    }

}
