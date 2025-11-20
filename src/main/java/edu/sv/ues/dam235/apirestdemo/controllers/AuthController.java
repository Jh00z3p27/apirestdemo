package edu.sv.ues.dam235.apirestdemo.controllers;

import edu.sv.ues.dam235.apirestdemo.dtos.LoginDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.RegisterDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.TokenDTO;
import edu.sv.ues.dam235.apirestdemo.services.AuthServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServices authServices;
    public AuthController(AuthServices authServices) { this.authServices = authServices; }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO body) {
        TokenDTO token = authServices.login(body.getUser(), body.getPass());
        return (token == null) ? ResponseEntity.status(401).build() : ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        boolean ok = authServices.register(dto);
        return ok ? ResponseEntity.status(201).build() : ResponseEntity.status(409).body("Usuario ya existe");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String token = (header != null && header.startsWith("Bearer ")) ? header.substring(7) : header;
        boolean ok = authServices.logout(token);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
