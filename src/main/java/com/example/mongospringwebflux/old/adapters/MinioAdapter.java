package com.example.mongospringwebflux.adapters;

import com.example.mongospringwebflux.exception.GlobalException;
import io.minio.MinioAsyncClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@Data
public class MinioAdapter {

    @Autowired
    private MinioAsyncClient minioClient;

    @Value( "${minio.bucket.name}" )
    private String bucketName;


    public Mono<Void> uploadFile(Mono<FilePart> file, String fileName) {

        return file.flatMap(filePart -> {
            Flux<DataBuffer> dataBufferFlux = filePart.content();

            return DataBufferUtils.join(dataBufferFlux)
                    .publishOn(Schedulers.boundedElastic())
                    .flatMap(dataBuffer -> {
                        InputStream inputStream = new ByteArrayInputStream(dataBuffer.toByteBuffer().array());

                        PutObjectArgs uploadObjectArgs = PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(inputStream, dataBuffer.readableByteCount(), -1)
                                .build();

                        return Mono.fromFuture(() -> {
                            try {
                                return minioClient.putObject(uploadObjectArgs);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }).then()
                    .onErrorResume(e -> Mono.error(new RuntimeException("Unexpected error during file upload", e)));
        });
    }





    public Mono<Void> deleteFile( String productId ) {
        return Mono.fromCallable( () -> {

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket( bucketName )
                            .object( productId )
                            .build()
            );
            return Mono.empty();
                })
                .subscribeOn( Schedulers.boundedElastic() )
                .onErrorMap(e -> new GlobalException( "error deleting image" ))
                .then();
    }
}
