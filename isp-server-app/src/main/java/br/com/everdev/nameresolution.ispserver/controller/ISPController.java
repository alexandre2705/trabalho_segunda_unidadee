package br.com.everdev.nameresolution.ispserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/perfil")
public class ISPController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private String getServiceUrl(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            return instances.get(0).getUri().toString();
        }
        return null;
    }

    @GetMapping("/obterArquivo/{filename}")
    public byte[] obterArquivo(@PathVariable String filename) {
        String profileAppUrl = getServiceUrl("profile-app");
        if (profileAppUrl != null) {
            return restTemplate.getForObject(profileAppUrl + "/perfil/obterArquivo/" + filename, byte[].class);
        }
        throw new RuntimeException("Serviço profile-app não encontrado");
    }

    @PostMapping("/salvarArquivo/{filename}")
    public String salvarArquivo(@RequestParam("file") MultipartFile file, @PathVariable String filename) {
        String profileAppUrl = getServiceUrl("profile-app");
        if (profileAppUrl != null) {
            return restTemplate.postForObject(profileAppUrl + "/perfil/salvarArquivo/" + filename, file, String.class);
        }
        return "Falha ao salvar o arquivo!";
    }
}