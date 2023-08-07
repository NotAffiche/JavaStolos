package me.adbi.javastolos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JavaStolosApplication {

    public static void main(String[] args) {
        String connectionURL = "jdbc:mysql://affiche.me:3306/db_allphifm";
        String connectionUser = "stolos";
        String connectionPassword = "st0l0Sdb2324";

        SpringApplication.run(JavaStolosApplication.class, args);
    }

}
