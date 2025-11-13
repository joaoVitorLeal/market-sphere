package io.github.joaoVitorLeal.marketsphere.billing.service;

import io.github.joaoVitorLeal.marketsphere.billing.bucket.BucketFile;
import io.github.joaoVitorLeal.marketsphere.billing.bucket.BucketService;
import io.github.joaoVitorLeal.marketsphere.billing.bucket.exception.StorageAccessException;
import io.github.joaoVitorLeal.marketsphere.billing.exception.InvoiceGenerationException;
import io.github.joaoVitorLeal.marketsphere.billing.exception.OrderProcessingException;
import io.github.joaoVitorLeal.marketsphere.billing.model.Order;
import io.github.joaoVitorLeal.marketsphere.billing.publisher.BillingPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class InvoiceGeneratorService {

    private final InvoiceService invoiceService;
    private final BucketService bucketService;
    private final BillingPublisher billingPublisher;

    public InvoiceGeneratorService(
            InvoiceService invoiceService,
            BucketService bucketService,
            BillingPublisher billingPublisher
    ) {
        this.invoiceService = invoiceService;
        this.bucketService = bucketService;
        this.billingPublisher = billingPublisher;
    }

    public void generate(Order order) {
        log.info("Generating invoice for order with ID {}.", order.orderId());

        try {
            // Gerar o array de bytes
            byte[] bytes = invoiceService.generateFromOrder(order);

            // Construir o bucketFile
            String fileName = String.format("invoice_order_%d.pdf", order.orderId());
            BucketFile bucketFile = new BucketFile(fileName, new ByteArrayInputStream(bytes), MediaType.APPLICATION_PDF, bytes.length);

            // Realizar upload para cloud
            bucketService.upload(bucketFile);
            log.info("Generated invoice, file name: {}.", bucketFile.name());

            // publicar evento no Kafka
            String invoiceUrl = bucketService.generatePresignedUrl(bucketFile.name());
            billingPublisher.publish(order, invoiceUrl);

        } catch (InvoiceGenerationException | StorageAccessException e) {
            log.error("Error in the invoice generation process: {}.", e.getMessage(), e);
            throw new OrderProcessingException("Failed to process invoice for order ID: " + order.orderId(), e);
        }
    }
}
