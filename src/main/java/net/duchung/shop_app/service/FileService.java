package net.duchung.shop_app.service;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {
    UrlResource getProductImage(String imageUrl);

    String uploadProductImage(MultipartFile file)  ;

    List<String> uploadProductImages(List<MultipartFile> files);
}
