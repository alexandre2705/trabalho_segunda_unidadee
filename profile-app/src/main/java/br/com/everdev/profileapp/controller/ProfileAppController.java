package br.com.everdev.profileapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/perfil")
public class ProfileAppController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DFS_APP_A_URL = "http://dfs-app-a:8183/files/";

    @GetMapping("/obterArquivo/{filename}")
    public byte[] obterArquivo(@PathVariable String filename) {
        return restTemplate.getForObject(DFS_APP_A_URL + "obterArquivo/" + filename, byte[].class);
    }

    @PostMapping("/salvarArquivo/{filename}")
    public String salvarArquivo(@RequestParam("file") MultipartFile file, @PathVariable String filename) {
        return restTemplate.postForObject(DFS_APP_A_URL + "salvarArquivo/" + filename, file, String.class);
    }
}