package ru.menkov.informsources.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.menkov.informsources.model.commerce.Score;

@Repository
public interface ScoreRepository extends CrudRepository<Score,Integer> {
}
