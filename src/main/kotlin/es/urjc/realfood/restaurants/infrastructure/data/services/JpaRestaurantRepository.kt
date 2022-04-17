package es.urjc.realfood.restaurants.infrastructure.data.services

import es.urjc.realfood.restaurants.infrastructure.data.entities.JpaRestaurant
import org.springframework.data.repository.CrudRepository

interface JpaRestaurantRepository : CrudRepository<JpaRestaurant, String>