package br.com.everdev.dfsappa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/files")
public class DfsAppAController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String DFS_APP_B = "dfs-app-b";
    private static final String DFS_APP_C = "dfs-app-c";

    private String getRandomInstanceUrl(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            throw new RuntimeException("Nnenhuma instância disponível para o serviço: " + serviceId);
        }
        int randomIndex = new Random().nextInt(instances.size());
        return instances.get(randomIndex).getUri().toString();
    }

    @GetMapping("/obterArquivo/{filename}")
    public ResponseEntity<?> obterArquivo(@PathVariable String filename) {
        String serviceUrl = getRandomInstanceUrl(DFS_APP_B) + "/files/obterArquivo/" + filename;
        return restTemplate.getForEntity(serviceUrl, ByteArrayResource.class);
    }

    @PostMapping("/salvarArquivo/{filename}")
    public String salvarArquivo(@PathVariable String filename, @RequestBody byte[] fileContent) {
        String serviceUrl = getRandomInstanceUrl(new Random().nextBoolean() ? DFS_APP_B : DFS_APP_C) + "/files/salvarArquivo/" + filename;
        return restTemplate.postForObject(serviceUrl, fileContent, String.class);
    }
}