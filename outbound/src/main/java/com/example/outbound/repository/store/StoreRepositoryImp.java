package com.example.outbound.repository.store;


import com.example.domain.store.Store;
import com.example.domain.store.StoreRepositoryI;
import com.example.outbound.mappers.StoreMappers;
import lombok.Data;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Repository
public class StoreRepositoryImp implements StoreRepositoryI {

    private final JpaStoreRepository jpaStoreRepository;
    private final StoreMappers storeMappers;

    @Override
    public Mono<Store> findById(String id) {
        return jpaStoreRepository.findById( id )
                .map( storeMappers::EntityToDomain );
    }

    @Override
    public Mono<Store> save( Store store ) {
        return jpaStoreRepository.save( storeMappers.DomainToEntity( store ) )
                .map( storeMappers::EntityToDomain );
    }

    @Override
    public Flux<Store> findAll() {
        return jpaStoreRepository.findAll()
                .map( storeMappers::EntityToDomain );
    }

    @Override
    public Mono<Void> deleteById( String storeId ) {
        return jpaStoreRepository.deleteById( storeId );
    }
}
