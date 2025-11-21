package com.mindcare.mindcareapi.DataLoader;

import com.mindcare.mindcareapi.model.User;
import com.mindcare.mindcareapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0) {
            User u = new User();
            u.setName("Teste");
            u.setEmail("teste@empresa.com");
            u.setConsent(true);
            userRepository.save(u);
            System.out.println("DataLoader: usuário de teste criado com id=" + u.getId());
        } else {
            System.out.println("DataLoader: usuários já existem, count=" + userRepository.count());
        }
    }
}
