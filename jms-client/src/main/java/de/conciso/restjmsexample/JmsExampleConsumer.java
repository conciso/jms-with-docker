package de.conciso.restjmsexample;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsExampleConsumer {

	private final static String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private final static String QUEUE_NAME = "java:/jms/queue/testQueue";
	private static final String WILDFLY_URL = "http-remoting://localhost:8080";
	private static final String WILDFLY_USER = "jboss";
	private static final String WILDFLY_PASSWORD = "jboss";
	private Context jndiContext = null;
	private JMSContext jmsContext = null;

	public static void main(String[] args) {
		JmsExampleConsumer consumer = new JmsExampleConsumer();

		consumer.receiveMessages();
	}

	private void receiveMessages() {
		System.out.println("Press q to exit...");
		try {
			final Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			env.put(Context.PROVIDER_URL, WILDFLY_URL);
			env.put(Context.SECURITY_PRINCIPAL, WILDFLY_USER);
			env.put(Context.SECURITY_CREDENTIALS, WILDFLY_PASSWORD);

			jndiContext = new InitialContext(env);

			ConnectionFactory cf = (ConnectionFactory) jndiContext.lookup(CONNECTION_FACTORY);
			System.out.println("Found Connection Factory...");
			Destination queue = (Destination) jndiContext.lookup(QUEUE_NAME);
			System.out.println("Found Queue...");

			jmsContext = cf.createContext(WILDFLY_USER, WILDFLY_PASSWORD);
			System.out.println("Created jms context...");
			TextMessage message = null;
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			System.out.println("Connected to queue...");
			while (System.in.read() != 'q') {
				message = (TextMessage) consumer.receive(1000);
				if (message != null) {
					System.out.println("Received message: '" + message.getText() + "'");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jmsContext != null) {
				jmsContext.close();
			}

			if (jndiContext != null) {
				try {
					jndiContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
