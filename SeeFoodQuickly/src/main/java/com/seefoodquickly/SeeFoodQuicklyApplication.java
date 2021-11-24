package com.seefoodquickly;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SeeFoodQuicklyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeeFoodQuicklyApplication.class, args);
	}
//    @Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//        @Override
//        protected void postProcessContext(Context context) {
//        	SecurityConstraint securityConstraint = new SecurityConstraint();
//        	securityConstraint.setUserConstraint("CONFIDENTIAL");
//        	SecurityCollection collection = new SecurityCollection();
//        	collection.addPattern("/*");
//        	securityConstraint.addCollection(collection);
//        	context.addConstraint(securityConstraint);
//        }
//    };
//    
//    // Add HTTP to HTTPS redirect
//    tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//    return tomcat;
//    }
//    
//    private Connector httpToHttpsRedirectConnector() {
//    	Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//    	connector.setScheme("http");
//        connector.setPort(9090);
//        connector.setSecure(false);
//        connector.setRedirectPort(443);
//       return connector;
//    }
//}

@Bean
public TomcatServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    Connector ajpConnector = new Connector("AJP/1.3");
    ajpConnector.setPort(9090);
    ajpConnector.setSecure(false);
    ajpConnector.setAllowTrace(false);
    ajpConnector.setScheme("http");
    ajpConnector.setRedirectPort(443);
   ((AbstractAjpProtocol<?>)ajpConnector.getProtocolHandler()).setSecretRequired(false);
tomcat.addAdditionalTomcatConnectors(ajpConnector);
return tomcat;
}
}