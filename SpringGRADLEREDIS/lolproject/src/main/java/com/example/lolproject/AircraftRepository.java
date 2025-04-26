package com.example.lolproject;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableRedisRepositories
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
