# Establishments

Microservicio para la gestion de establecimientos.

          Para Dockerizar
------------------------------------------------------

docker compose up -d db

docker compose build

docker compose up Digapp_Establishments_ms

si se presenta error de gradle es necesario ejecutar este comando y volver a hacer el built

./gradlew clean build -x test


           Consultas
------------------------------------------------------
Respecto al MS Establishments se añadió la opción de filtrar por userID, Filtrar por tipo de establecimiento y se implementó el apartado de bookings. Comparto a continuación las URL

--para mostrar todos los establecimientos (get)
http://establishments.ddns.net:8080/api/establishments


--para filtrar por userID: (get)
http://establishments.ddns.net:8080/api/establishments/user/646ac7e1e42feb67db1fc8f5 (en donde "646ac7e1e42feb67db1fc8f5" es el userID a filtrar)


--para filtrar por tipo de establecimiento: (get)
http://establishments.ddns.net:8080/api/establishments/type/Cafe (donde "café" es el tipo de establecimiento a filtrar)


--para añadir bookings: (put)
http://establishments.ddns.net:8080/api/establishments/3627b0c6-74b7-40df-869b-5d7979b261eb/bookings (en donde "3627b0c6-74b7-40df-869b-5d7979b261eb" es el ID del establecimiento donde se añadira el booking.


El put solo debe incluir la palabra bookings y el numero correspondiente. e.g.
{
    "bookings": 3
}
En este caso se suma el valor 3 a bookings
-el valor almacenado en bookings se va sumando con el valor original. es decir, si ya habían 5 bookings el nuevo valor seria 8 en total.
-si el valor de bookings resulta ser superior a "capacity" entonces no se realiza la suma y se devuelve un mensaje indicando que excede el número de cupos disponibles.
2. para liberar espacio en bookings basta con realizar el mismo Put pero con un numero negativo:
{
    "bookings": -3
}
esta acción liberará 3 espacios de booking.
Las url de ejemplo son reales y se pueden realizar las respectivas consultas con los links tal cual.
