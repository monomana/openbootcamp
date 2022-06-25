package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ArticlesTreeEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ArticlesTreeReactive extends ReactiveSortingRepository< ArticlesTreeEntity, String > {

}
