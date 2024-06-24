package br.com.everdev.dfsappc.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class DfsAppCController {

    private static final String FILE_DIRECTORY = "/uploads";

    @GetMapping("/obterArquivo/{filename}")
    public ResponseEntity<?> obterArquivo(@PathVariable String filename) {
        var filePath = Paths.get(FILE_DIRECTORY, filename);

        try {
            var file = new File(filePath.toString());
            var resource = new ByteArrayResource(Files.readAllBytes(filePath));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/salvarArquivo/{filename}")
    public String salvarArquivo(@PathVariable String filename, @RequestBody byte[] fileContent) throws IOException {
        var filePath = Paths.get(FILE_DIRECTORY, filename);
        Files.write(filePath, fileContent);
        return "Arquivo salvo com sucesso!";
    }
}