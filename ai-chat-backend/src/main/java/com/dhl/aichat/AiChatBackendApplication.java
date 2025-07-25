package com.dhl.aichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AiChatBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiChatBackendApplication.class, args);
    }

    @Component
    public static class StartupMessagePrinter implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            String cyan = "\033[36m";
            String green = "\033[32m";
            String yellow = "\033[33m";
            String reset = "\033[0m";

            String art = cyan + """
                     ____                    _          ____  _          _ _   \s
                    |  _ \\  ___  ___ ___ ___| | _____  / ___|| |__   ___| | |  \s
                    | | | |/ _ \\/ __/ __/ _ \\ |/ / _ \\ \\___ \\| '_ \\ / _ \\ | |  \s
                    | |_  __/ (__ (_|  __/   <  __/  ___) | | | |  __/ | |  \s
                    |____/ \\___|\\___\\___\\___|_|\\_\\___| |____/|_| |_|\\___|_|_|  \s
                    """ + reset;

            String message = green + """
                    \n
                    > AiChat Backend Application is running!
                    > Swagger UI: http://localhost:8080/swagger-ui.html
                    """ + yellow + """
                    \n
                    ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                    ░░           AI is now ready to serve your requests!        ░░
                    ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                    """ + reset;

            System.out.println(art + message);
        }
    }
}