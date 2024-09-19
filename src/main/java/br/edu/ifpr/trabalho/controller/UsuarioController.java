package br.edu.ifpr.trabalho.controller;

import br.edu.ifpr.trabalho.model.Usuario;
import br.edu.ifpr.trabalho.repository.UsuarioRepository;
import br.edu.ifpr.trabalho.security.JwtResponse;
import br.edu.ifpr.trabalho.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para realizar o login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente != null && passwordEncoder.matches(usuario.getSenha(), usuarioExistente.getSenha())) {
            final String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(usuarioExistente.getEmail(), usuarioExistente.getSenha(), new ArrayList<>()));
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }

    // Exemplo de endpoint para cadastro de usuários
    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(novoUsuario);
    }
}
