package fr.picom.picomspring;

import fr.picom.picomspring.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class PicomSpringApplication implements CommandLineRunner {

    @Resource
    FilesStorageService filesStorageService;

    public static void main(String[] args) {
        SpringApplication.run(PicomSpringApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception{
        filesStorageService.init();
    }

}
