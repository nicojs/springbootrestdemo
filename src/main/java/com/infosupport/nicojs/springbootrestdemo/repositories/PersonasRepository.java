package com.infosupport.nicojs.springbootrestdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.infosupport.nicojs.springbootrestdemo.entities.Persona;

public interface PersonasRepository extends JpaRepository<Persona, Integer> {
}
