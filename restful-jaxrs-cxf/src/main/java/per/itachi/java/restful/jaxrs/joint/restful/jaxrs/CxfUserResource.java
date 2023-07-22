package per.itachi.java.restful.jaxrs.joint.restful.jaxrs;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Path("/v1/users")
public class CxfUserResource {

    @PostConstruct
    public void init() {
        log.info("Initialized CxfUserResource. ");
    }

    @Path("{userId}")
    @GET // no param
    public String getUserById(@PathParam("userId") String userId,
                              @Context Request request, @Context HttpHeaders httpHeaders) {
        log.info("Getting user, userId={}, request={}, httpHeaders={}. ", userId, request, httpHeaders);
        return userId;
    }

    @POST // no param
    @Produces("application/json") // matches Accept?
    public String addUserViaJson() {
        String userId = UUID.randomUUID().toString();
        log.info("Adding user, userId={}", userId);
        return userId;
    }
}
