package com.example.projeto.service;

import com.example.projeto.dto.ClienteRequestDTO;
import com.example.projeto.entity.ClienteEntity;
import com.example.projeto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    // READ - GET
    public List<ClienteRequestDTO> listarTodos () {
        return repository
                .findAll()
                .stream()
                .map(cliente -> new ClienteRequestDTO(
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getTelefone()))
                .toList();

    }

    public ClienteEntity salvar(ClienteRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent())
            throw  new IllegalArgumentException("Cliente já cadastrado.");

        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setTelefone(dto.getTelefone());

        return repository.save(novoCliente);
    }

    public ClienteEntity atualizar(Long id, ClienteRequestDTO dto) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("Cliente não encontrado. ");


        ClienteEntity novoCliente = new ClienteEntity();
        novoCliente.setId(id);
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setTelefone(dto.getTelefone());

        return repository.save(novoCliente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("Cliente não encontrado. ");

        repository.deleteById(id);
    }
}
