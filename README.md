# Realfood-Restaurants

Servicio de restaurantes del TFM del Master CloudApps de la URJC

### Autores

- Juan Antonio Ávila Catalán, [@juanaviladev](https://github.com/juanaviladev)
- Cristo Fernando López Cabañas, [@cristoflop](https://github.com/cristoflop)

Este servicio contiene toda la funcionalidad asociada a los clientes de la aplicación RealFood, esta aplicación consta
de los siguientes componentes:

- [GitHub - Realfood-Clients](https://github.com/MasterCloudApps-Projects/realfood-clients)
- [GitHub - Realfood-Restaurants](https://github.com/MasterCloudApps-Projects/realfood-restaurants)
- [GitHub - Realfood-Payments](https://github.com/MasterCloudApps-Projects/realfood-payments)
- [GitHub - Realfood-Shipping](https://github.com/MasterCloudApps-Projects/realfood-shipping)

Estos servicios se ha desarrollado siguiendo el estilo
de [Arquitectura Hexagonal](https://es.wikipedia.org/wiki/Arquitectura_hexagonal_(software))

Operaciones disponibles en el servicio:

    Operaciones disponibles al iniciar sesion
        - GET       /api/restaurants/{restaurantId}/menu                    Ver carta del restaurante
        - GET       /api/restaurants/                                       Ver restaurantes

    Operaciones disponibles sin iniciar sesión (perfil de restaurante)
        - GET       /api/orders/{orderId}                                   Ver carta del restaurante
        - POST      /api/restaurants/{restaurantId}/menu/items              Ver carta del restaurante
        - DELETE    /api/restaurants/{restaurantId}/menu/items/{itemId}     Ver carta del restaurante
        - PATCH     /api/orders/{orderId}                                   Actualizar estado del pedido
        - DELETE    /api/restaurants/{restaurantId}                         Dar de baja restaurante
        - PATCH     /api/restaurants/{restaurantId}                         Actualizar datos de un restaurante
        - POST      /api/restaurants                                        Dar de alta restaurante

Operaciones de Publicación/Suscripción que ejecuta el servicio:

        - [Consume] Shipment order update                  Actualización del estado de envío de un pedido

Diagrama de clases del dominio de la aplicación:

```mermaid
classDiagram
class Restaurant
class Menu
class Item
class Address
class Category
class BusinessHours
class OpenHour
class CloseHour

Menu --> "*" Item
Restaurant o--> BusinessHours
Restaurant o--> Menu
Restaurant o--> Address
Restaurant o--> Category
Restaurant o--> BusinessHours
BusinessHours o--> OpenHour
BusinessHours o--> CloseHour
```

Ejemplo de diagrama de clases para el caso de uso New Restaurant:
![use-case-diagram](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/MasterCloudApps-Projects/realfood-restaurants/main/docs/new-restaurant-usecase.puml)

## Despliegue

### Docker

- Despliegue de recursos (Solo BD y broker de RabbitMq)

```
$ docker-compose -f deploy/docker-compose.yml up --build
```

- Despliegue completo (Recursos y servicio de restaurants)

```
$ docker-compose -f deploy/docker-compose-prod.yml up --build
```

- Para observar que se han creado los contenedores:

```
$ docker ps
```

Software recomendado: [Docker desktop](https://www.docker.com/) / [Rancher desktop](https://rancherdesktop.io/)

### Kubernetes

En la carpeta de /deploy están los manifiestos para desplegar los recursos y el servicio

- Arrancar el servicio de minikube

```
$ minikube start
```

- Arrancar broker de RabbitMQ

```
$ kubectl apply -f rabbitmq-pv.yaml

$ kubectl apply -f rabbitmq-pv-claim.yaml

$ kubectl apply -f rabbitmq-deployment.yaml

$ kubectl apply -f rabbitmq-service.yaml
```

- Arrancar BD de clientes

```
$ kubectl apply -f postgres-pv.yaml

$ kubectl apply -f postgres-pv-claim.yaml

$ kubectl apply -f postgres-deployment.yaml

$ kubectl apply -f postgres-service.yaml
```

- Arrancar Servicio de envíos

```
$ kubectl apply -f app-deployment.yaml

$ kubectl apply -f app-service.yaml
```

- Para observar que se han desplegado los servicios:

```
$ kubectl get deployments

$ kubectl get services
```

- Si se quiere levantar todo directamente:
```
$ kubectl apply -f .
```

Software recomendado: [k8sLens](https://k8slens.dev/)