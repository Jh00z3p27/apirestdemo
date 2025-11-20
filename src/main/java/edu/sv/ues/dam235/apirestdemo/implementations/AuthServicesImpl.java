package edu.sv.ues.dam235.apirestdemo.implementations;

import edu.sv.ues.dam235.apirestdemo.configs.CustomerDetailServices;
import edu.sv.ues.dam235.apirestdemo.dtos.RegisterDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.TokenDTO;
import edu.sv.ues.dam235.apirestdemo.entities.User;
import edu.sv.ues.dam235.apirestdemo.repositories.UserRepository;
import edu.sv.ues.dam235.apirestdemo.services.AuthServices;
import edu.sv.ues.dam235.apirestdemo.utilities.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private CustomerDetailServices customerDetailServices;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    // Blacklist en memoria para la demo de logout
    private final Set<String> invalidatedTokens = new HashSet<>();

    @Override
    public TokenDTO login(String user, String pass) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, pass));
            if (auth.isAuthenticated()) {
                UserDetails details = (UserDetails) auth.getPrincipal();
                if (customerDetailServices.getUserDetail().getActive()) {
                    return jwtUtil.generateToken(user, details);
                }
            }
        } catch (Exception e) {
            log.error("Login error", e);
        }
        return null;
    }

    @Override
    public boolean register(RegisterDTO dto) {
        try {
            if (userRepo.findByEmail(dto.getEmail()) != null) return false;
            User u = new User();
            u.setName(dto.getName());
            u.setLastName(dto.getLastName());
            u.setEmail(dto.getEmail());
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
            u.setActive(true);
            userRepo.save(u);
            return true;
        } catch (Exception e) {
            log.error("Register error", e);
            return false;
        }
    }

    @Override
    public boolean logout(String token) {
        if (token == null || token.isBlank()) return false;
        invalidatedTokens.add(token);
        return true;
    }

    // Exponer para JwtFilter (opcional: mover a un bean TokenBlacklistService)
    public boolean isInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}
