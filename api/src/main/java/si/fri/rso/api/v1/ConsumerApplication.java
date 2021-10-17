package si.fri.rso.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Boris Radovic
 */

@ApplicationPath("/v1")
@OpenAPIDefinition(info = @Info(title = "CustomerApplication", version = "1.0.0", contact = @Contact()))
public class ConsumerApplication extends Application {
}
