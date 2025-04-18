package com.ada.pedidocompra.dominio.servicos;

import com.ada.pedidocompra.infraestrutura.controladores.request.CreateClienteRequest;
import com.ada.pedidocompra.infraestrutura.controladores.request.UpdateClienteRequest;
import com.ada.pedidocompra.infraestrutura.controladores.response.ClienteResponse;
import com.ada.pedidocompra.infraestrutura.controladores.response.IdResponse;
import com.ada.pedidocompra.infraestrutura.configuracao.exceptions.NegocioException;
import com.ada.pedidocompra.infraestrutura.repositorios.UsuarioRepository;
import com.ada.pedidocompra.infraestrutura.repositorios.entidades.enums.TipoUsuarioEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public IdResponse create(CreateClienteRequest createClienteRequest) {

        var usuario = createClienteRequest.toUsuario(TipoUsuarioEnum.CLIENTE);
        //usuario.setSenha((usuario.getSenha()));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        usuarioRepository.save(usuario);

        return new IdResponse(usuario.getId());
    }

    public void deleteById(Long clienteId) {
        usuarioRepository.deleteById(clienteId);
    }

    public void update(Long clienteId, UpdateClienteRequest updateClienteRequest) {

        var clienteDb = usuarioRepository.findById(clienteId);
        if (clienteDb.isEmpty()) {
            throw new NegocioException("Cliente não encontrado!!!");
        }

        clienteDb.get().setNome(updateClienteRequest.nome());

        usuarioRepository.save(clienteDb.get());
    }

    public List<ClienteResponse> findAll() {

        var clienteList = usuarioRepository.findAll();

        return clienteList
                .stream()
                .map(ClienteResponse::from)// Converte cada cliente para ClienteResponse
                .toList(); // Converte o Stream para uma lista

    }

    public ClienteResponse findById(Long id) {

        var clienteDb = usuarioRepository.findById(id);
        if (clienteDb.isEmpty()) {
            throw new NegocioException("Cliente não encontrado!!!");
        }

        return ClienteResponse.from(clienteDb.get());
    }
}
