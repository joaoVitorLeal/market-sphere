package io.github.joaoVitorLeal.marketsphere.billing.api;

import io.github.joaoVitorLeal.marketsphere.billing.bucket.BucketFile;
import io.github.joaoVitorLeal.marketsphere.billing.bucket.BucketService;
import io.github.joaoVitorLeal.marketsphere.billing.bucket.exception.StorageAccessException;
import io.github.joaoVitorLeal.marketsphere.billing.translator.MessageTranslator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService service;
    private final MessageTranslator translator;

    public BucketController(BucketService service, MessageTranslator translator) {
        this.service = service;
        this.translator = translator;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            MediaType mediaType = MediaType.parseMediaType(Objects.requireNonNull(multipartFile.getContentType()));
            service.upload(new BucketFile(multipartFile.getOriginalFilename(), inputStream, mediaType, multipartFile.getSize()));
            String successMessage = translator.translate("file.upload.successful");
            return ResponseEntity.ok(successMessage);
        } catch (IOException e) {
            throw new StorageAccessException("Could not read input stream from uploaded multipartFile", e);
        }
    }

    @GetMapping(value = "/{fileName:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFileUrl(@PathVariable String fileName) {
        String fileUrl = service.generatePresignedUrl(fileName);
        return ResponseEntity.ok(fileUrl);
    }
}
