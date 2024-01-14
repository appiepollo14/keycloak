package nl.avasten.keycloak;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import nl.avasten.keycloak.dto.AddRoleToUserRequest;
import nl.avasten.keycloak.dto.RemoveRoleFromUserRequest;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Path("/api/v1/keycloak")
@RolesAllowed("MRK_ADMIN")
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

    @POST
    @Path("/administerRole")
    public Response addRoleToUser(AddRoleToUserRequest request) {
        keycloakRoleService.assignRole(request.userId(), request.roleName());
        return Response.accepted().build();
    }

    @POST
    @Path("/removeRole")
    public Response removeRoleFromUser(RemoveRoleFromUserRequest request) {
        keycloakRoleService.removeRole(request.userId(), request.roleName());
        return Response.accepted().build();
    }

}
