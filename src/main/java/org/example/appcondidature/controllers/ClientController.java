package org.example.appcondidature.controllers;

import org.example.appcondidature.entities.Client;
import org.example.appcondidature.services.ClientServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientServic clientServic;

    @GetMapping("/")
    public ResponseEntity<?>  getClients() {

        List<Client> clients = clientServic.getClients();

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getClient(@PathVariable Long id) {
        Client client = clientServic.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }


    @PostMapping("/add")
    public ResponseEntity<?>  addClient(@RequestBody Client client) {
        Client newClient = clientServic.addClient(client);
        if(newClient != null) {
            return ResponseEntity.ok(newClient);
        }
        return ResponseEntity.noContent().build();
    }

}
