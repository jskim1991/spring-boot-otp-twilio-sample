package io.jay.otpapp.admin;

import io.jay.otpapp.repository.OneTimePasswordRepository;
import io.jay.otpapp.repository.entity.OneTimePasswordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final OneTimePasswordRepository repository;

    @GetMapping
    public List<OneTimePasswordEntity> getAll() {
        return repository.findAll();
    }
}
