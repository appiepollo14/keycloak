package nl.avasten.keycloak;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class KeyCloakUserServiceImpl implements KeyCloakUserService {

    @Inject
    Keycloak keycloak;

    @Override
    public UserRepresentation getUserRepresentationById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public UserResource getUserResourceById(String userId) {
        return getUsersResource().get(userId);
    }

    @Override
    public List<UserRepresentation> getUsers() {
        return getUsersResource().list();
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm("quarkus");
        return realm1.users();
    }

}
