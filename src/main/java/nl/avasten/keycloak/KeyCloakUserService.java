package nl.avasten.keycloak;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeyCloakUserService {

    UserRepresentation getUserRepresentationById(String userId);

    UserResource getUserResourceById(String userId);

    List<UserRepresentation> getUsers();
}
