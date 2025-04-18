package com.ada.pedidocompra.infraestrutura.configuracao.seguranca;

import com.ada.pedidocompra.infraestrutura.repositorios.entidades.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserContext implements UserDetails {

    private final Usuario usuario;

    public UserContext(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var perfil = usuario.getTipoUsuario().name();
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

}
