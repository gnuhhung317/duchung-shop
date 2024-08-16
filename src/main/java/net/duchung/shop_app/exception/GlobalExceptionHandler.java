package net.duchung.shop_app.exception;

import net.duchung.shop_app.response.LoginResponse;
import net.duchung.shop_app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    FileService fileService;
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(NonImageFileException.class)
    public ResponseEntity<String> handleNonImageFileException(NonImageFileException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<String> handleSizeLimitExceededException(SizeLimitExceededException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<String> handleJwtAuthenticationException(JwtAuthenticationException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<LoginResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(401).body(LoginResponse.builder()
                .message(List.of(e.getMessage()))
                .token(null).build());
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<UrlResource> handleImageNotFoundException(ImageNotFoundException e) {
        UrlResource resource = fileService.getProductImage(ImageNotFoundException.notFoundImageUrl);
        return ResponseEntity.status(404).contentType(MediaType.IMAGE_JPEG).body(resource);
    }

}
