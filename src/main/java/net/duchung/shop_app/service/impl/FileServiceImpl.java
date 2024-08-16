package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.exception.ImageNotFoundException;
import net.duchung.shop_app.exception.SizeLimitExceededException;
import net.duchung.shop_app.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public UrlResource getProductImage(String fileName)  {

        try {
            Path path = Paths.get(uploadDir, fileName);
            UrlResource resource = new UrlResource(path.toUri());

            if(resource.exists()){
                return resource;
            }
            throw new ImageNotFoundException(fileName+" not found");
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public String uploadProductImage(MultipartFile file)   {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > 10000000) { // 10MB
            throw new SizeLimitExceededException("File size limit exceeded! Maximum size is 10MB");
        }

        String contentType = file.getContentType();
        try {
            if (contentType == null || !contentType.startsWith("image/") || ImageIO.read(file.getInputStream()) == null) {
                throw new IllegalArgumentException("File is not an image");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File is not an image");
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (Files.notExists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory:", e);
            }
        }


        Path filePath = uploadPath.resolve(filename);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        return filename;
    }

    @Override
    @Transactional
    public List<String> uploadProductImages(List<MultipartFile> files) {
        List<String> fileNames = new LinkedList<>();
        int cnt=0;
        for (MultipartFile file : files) {
            try {
                String fileName = uploadProductImage(file);
                fileNames.add(fileName);
            } catch (IllegalArgumentException | SizeLimitExceededException  e) {
                cnt++;
            }
        }

        if (cnt!=0) {
            String message = cnt==1?"One file failed to upload":"Some files failed to upload: "+cnt+" files";
            throw new RuntimeException(message);
        }

        return fileNames;
    }

}
