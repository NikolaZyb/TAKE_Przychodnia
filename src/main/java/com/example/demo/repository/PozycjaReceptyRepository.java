package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.PozycjaRecepty;

@Repository
public interface PozycjaReceptyRepository extends JpaRepository<PozycjaRecepty, Long>{

}
