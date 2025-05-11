package com.ada.pedidocompra.infraestrutura.controladores.request;

import com.ada.pedidocompra.infraestrutura.configuracao.validacao.UniqueEmail;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Usuario;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.enums.TipoUsuarioEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateClienteRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "CPF é obrigatório") @CPF(message = "CPF inválido") String cpf,
        @UniqueEmail @NotBlank @Email(message = "Email inválido") String email,
        @NotBlank @Size(min = 8, max = 20) String senha) {

    public Usuario toUsuario(TipoUsuarioEnum tipoUsuario) {
        var usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTipoUsuario(tipoUsuario);
        return usuario;
    }

}
