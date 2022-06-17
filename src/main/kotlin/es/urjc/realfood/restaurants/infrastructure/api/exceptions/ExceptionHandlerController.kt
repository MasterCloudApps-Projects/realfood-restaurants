package es.urjc.realfood.restaurants.infrastructure.api.exceptions

import es.urjc.realfood.restaurants.domain.exceptions.CategoryNotFoundException
import es.urjc.realfood.restaurants.domain.exceptions.ItemNotFoundException
import es.urjc.realfood.restaurants.domain.exceptions.OrderNotFoundException
import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerController {

    private val exceptionsMap = mapOf(
        IllegalArgumentException::class.java to 400,
        CategoryNotFoundException::class.java to 404,
        ItemNotFoundException::class.java to 404,
        OrderNotFoundException::class.java to 404,
        RestaurantNotFoundException::class.java to 404
    )

    @ExceptionHandler
    fun handle(exception: Exception): ResponseEntity<ApiError> {
        return ResponseEntity.status(exceptionsMap.getOrDefault(exception::class.java, 500))
            .body(ApiError(exception.message ?: "No message available"))

    }
}