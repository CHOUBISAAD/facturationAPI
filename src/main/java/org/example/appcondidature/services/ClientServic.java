package org.example.appcondidature.services;

import org.example.appcondidature.Repos.ClientRepo;
import org.example.appcondidature.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServic {

    @Autowired
    public ClientRepo clientRepo;

    public List<Client> getClients() {
        return clientRepo.findAll();
    }

    public Client addClient(Client client) {
        return clientRepo.save(client);
    }
    public Client getClientById(Long id) {
        return clientRepo.findById(id).get();
    }

}
