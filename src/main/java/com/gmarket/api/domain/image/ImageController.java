package com.gmarket.api.domain.image;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> createImage(@RequestPart MultipartFile file) throws Exception {

        return ResponseEntity.ok(imageService.create(file));
    }

    @GetMapping(value = "{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) throws Exception {

        return ResponseEntity.ok(imageService.getImage(imageName));
    }
}
