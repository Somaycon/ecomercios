package com.somaycon.ecomercios.service;

import com.somaycon.ecomercios.dto.TokenResponseDTO;
import com.somaycon.ecomercios.dto.auth.LoginRequestDTO;
import com.somaycon.ecomercios.dto.auth.RegistroRequestDTO;
import com.somaycon.ecomercios.exception.UsuarioAlreadyExistsException;
import com.somaycon.ecomercios.model.Usuario;
import com.somaycon.ecomercios.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO registro(RegistroRequestDTO registroRequestDTO){
        if (this.usuarioRepository.findByEmail(registroRequestDTO.email()).isPresent()){
            throw new UsuarioAlreadyExistsException("Email já cadastrado!");
        }

        String senhaCriptografada = passwordEncoder.encode(registroRequestDTO.senha());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(registroRequestDTO.nome());
        novoUsuario.setEmail(registroRequestDTO.email());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setPerfil(registroRequestDTO.perfil());

        this.usuarioRepository.save(novoUsuario);

        String token = tokenService.gerarToken(novoUsuario);

        return new TokenResponseDTO(token);
    }
}
