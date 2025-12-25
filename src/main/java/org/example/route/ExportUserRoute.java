package org.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.example.dto.ExportUser;
import org.example.model.User;
import org.example.route.converter.UserConverter;

@Component
public class ExportUserRoute extends RouteBuilder {

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Autowired
    private UserConverter userConverter;

    @Override
    public void configure() throws Exception {
        JacksonDataFormat jsonFormat = new JacksonDataFormat();
        jsonFormat.setPrettyPrint(true);

        from("timer://userExport?period=50000&delay=10000")
                .id("exportUser")
                .log("=== Starting user export ===")

                // Get users that haven't been exported yet
                .to("jpa:org.example.model.User?"
                        + "query=SELECT u FROM User u WHERE u.exported=false"
                        + "&resultClass=org.example.model.User")

                .choice()
                // If the list is empty or null
                .when(simple("${body} == null || ${body.size()} == 0"))
                .log("Nothing to export")
                .stop() // Stop the route

                .otherwise()
                .log("Found ${body.size()} users to export")

                // Split the list of users
                .split(body()).streaming()
                .id("splitUser")

                // Save the original user to the property
                .process(exchange -> {
                    User user = exchange.getIn().getBody(User.class);
                    exchange.setProperty("originalUser", user);
                    exchange.setProperty("userId", user.getId());

                    ExportUser exportUser = userConverter.toExportUser(user);
                    exchange.getIn().setBody(exportUser);
                })

                // Convert to JSON
                .marshal(jsonFormat)

                // Set file name
                .setHeader("CamelFileName", simple("user_${exchangeProperty.userId}_${date:now:yyyyMMddHHmmss}.json"))

                // Send file to FTP
                .toD("ftp://" + ftpUsername + ":" + ftpPassword + "@" + ftpHost + ":21"
                        + "?passiveMode=true"
                        + "&binary=true"
                        + "&connectTimeout=10000"
                        + "&autoCreate=true"
                        + "&stepwise=false"
                        + "&throwExceptionOnConnectFailed=true"
                        + "&fileName=${header.CamelFileName}")


                //Update user as exported
                .process(exchange -> {
                    User user = exchange.getProperty("originalUser", User.class);
                    user.setExported(true);
                    exchange.getIn().setBody(user);
                })

                // Save in the Database
                .to("jpa:org.example.model.User?usePersist=false")

                .log("User ${exchangeProperty.userId} exported to file ${header.CamelFileName}")
                .end() // end split
                .end() // end choice

                .log("=== User export completed ===");
    }
}