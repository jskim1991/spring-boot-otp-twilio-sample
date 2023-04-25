package io.jay.otpapp.repository;


import io.jay.otpapp.repository.entity.OneTimePasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePasswordEntity, String> {
}
