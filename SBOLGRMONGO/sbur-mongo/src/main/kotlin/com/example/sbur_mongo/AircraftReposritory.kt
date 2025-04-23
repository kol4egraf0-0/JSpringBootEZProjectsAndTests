package com.example.sbur_mongo

import org.springframework.data.repository.CrudRepository

interface AircraftReposritory : CrudRepository<Aircraft, String> {//MongoRepository тож можно, CrudRepository достаточно
}