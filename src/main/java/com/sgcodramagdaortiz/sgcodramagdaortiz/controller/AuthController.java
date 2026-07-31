package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.LoginRequest;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.LoginResponse;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.AuthService;

import jakarta.validation.Valid;

/**
 * Controlador REST encargado de la autenticación
 * de los usuarios.
 *
 * Endpoint principal:
 *
 * POST /api/auth/login
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Servicio encargado del proceso de autenticación.
     */
    private final AuthService authService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param authService servicio de autenticación
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Realiza el inicio de sesión.
     *
     * POST /api/auth/login
     *
     * Ejemplo de petición:
     *
     * {
     *     "username": "usuario",
     *     "password": "contraseña"
     * }
     *
     * @param request credenciales del usuario
     * @return token JWT y datos básicos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.autenticar(request);

        return ResponseEntity.ok(response);
    }
}