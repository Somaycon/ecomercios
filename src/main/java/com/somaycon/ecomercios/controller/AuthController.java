package com.somaycon.ecomercios.controller;

import com.somaycon.ecomercios.dto.TokenResponseDTO;
import com.somaycon.ecomercios.dto.auth.LoginRequestDTO;
import com.somaycon.ecomercios.dto.auth.RegistroRequestDTO;
import com.somaycon.ecomercios.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realiza autenticação do usuário", description = "Retorna um token JWT válido para acessos subsequentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastra um novo usuário", description = "Cria uma conta de cliente ou admin e retorna o token de acesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Conflito: Email já cadastrado")
    })
    public ResponseEntity<TokenResponseDTO> registro(@Valid @RequestBody RegistroRequestDTO registroRequestDTO){
        return ResponseEntity.ok(authService.registro(registroRequestDTO));
    }

}
