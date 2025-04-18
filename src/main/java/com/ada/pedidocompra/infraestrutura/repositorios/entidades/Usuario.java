package com.ada.pedidocompra.infraestrutura.repositorios.entidades;

import com.ada.pedidocompra.infraestrutura.repositorios.entidades.enums.TipoUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String nome;

    @CPF
    @Column(length = 14, nullable = false)
    private String cpf;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuarioEnum tipoUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
