package br.com.everdev.dfsappc.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DfsService {

    private static final String STORAGE_DIR = "storage/dfs-c/";

    public byte[] obterArquivo(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(STORAGE_DIR + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void salvarArquivo(String fileName, byte[] content) {
        try {
            Files.createDirectories(Paths.get(STORAGE_DIR));
            Files.write(Paths.get(STORAGE_DIR + fileName), content);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}
