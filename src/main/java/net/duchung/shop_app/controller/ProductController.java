package net.duchung.shop_app.controller;

import jakarta.validation.Valid;
import net.duchung.shop_app.dto.ProductDto;
import net.duchung.shop_app.dto.ProductImageDto;
import net.duchung.shop_app.response.ListProductResponse;
import net.duchung.shop_app.service.FileService;
import net.duchung.shop_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @Autowired
    private ProductService  productService;
    @Autowired
    private FileService fileService;

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(productService.createProduct(productDto));
    }
    @PostMapping("uploads/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long id,
                                        @ModelAttribute("files") List<MultipartFile> files)     {
        List<String> fileNames = fileService.uploadProductImages(files);
        List<ProductImageDto> productImageDtos = fileNames.stream().map(fileName -> productService.createProductImage(id, ProductImageDto.builder().imageUrl(fileName).build())).toList();
        return ResponseEntity.ok(productImageDtos);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        return ResponseEntity.ok(new ListProductResponse(productService.getAllProducts(pageRequest).getContent(), productService.getAllProducts(pageRequest).getTotalElements()));
    }

}
