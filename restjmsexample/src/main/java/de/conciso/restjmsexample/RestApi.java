package de.conciso.restjmsexample;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Queue;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Path("/message")
public class RestApi {
	private final static Logger LOG = LoggerFactory.getLogger(RestApi.class);

	@Resource(lookup = "java:/jms/queue/testQueue")
	private Queue testQueue;
	
	@Inject
	private JMSContext context; 
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response erzeugeDokumentenContainer(String messageText) {
		try {
			LOG.info("Received message: '" + messageText + "'");
			
			context.createProducer().send(testQueue, messageText);
			
			LOG.info("Sent jms message: '" + messageText + "'");
			return Response.ok().build();
		} catch (Exception e) {
			throw new BadRequestException(e);
		}
	}
}
