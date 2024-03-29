package nl.avasten;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.NoCache;

@Path("/api/users")
@Authenticated
public class UsersResource {

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/me")
    public String me() {
        return "Access for name " + jsonWebToken.getName() + " is granted.";
    }

    @GET
    @Path("/subject")
    public String getSubject() {
        return "Access for subject " + jsonWebToken.getSubject() + " is granted.";
    }
}
