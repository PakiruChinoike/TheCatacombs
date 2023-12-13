package io.github.pakiruchinoike.apresentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.github.pakiruchinoike.modelo.User;
import io.github.pakiruchinoike.persistencia.UserRepository;


@SpringBootApplication
@ComponentScan(basePackages = {"io.github.pakiruchinoike.apresentacao", 
"io.github.pakiruchinoike.modelo", 
"io.github.pakiruchinoike.persistencia"})
public class CatacombsApplication {
    
    @Bean
    public CommandLineRunner init(@Autowired UserRepository userRepository) {
        return args -> {
            userRepository.save(new User(1, "Jorge", "EuSouOJorge"));
            userRepository.save(new User(2, "Douglas", "EuNaoSouOJorge"));
            userRepository.save(new User(3, "Kowawski", "Ola"));

            List<User> allUsers = userRepository.selectAll();
            allUsers.forEach(System.out::println);

            allUsers.forEach(u -> {
                u.setUsername(u.getUsername() + " (+)");
                userRepository.save(u);
            });

            allUsers = userRepository.selectAll();
            allUsers.forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CatacombsApplication.class, args);
    }
}
