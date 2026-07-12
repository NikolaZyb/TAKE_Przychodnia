package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Wizyta;

@Repository
public interface WizytaRepository  extends JpaRepository<Wizyta, Long>{

}
