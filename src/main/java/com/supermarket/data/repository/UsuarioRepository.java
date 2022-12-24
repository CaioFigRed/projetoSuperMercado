package com.supermarket.data.repository;

import com.supermarket.data.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
