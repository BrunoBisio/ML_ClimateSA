# ML_ClimateSA

Una vez deployado el JAR, se creara la base de datos vacia.
Por lo que es necesario ejecutar las siguientes sentencias en orden, para asi inicializar correctamente la base de datos.

```
INSERT INTO ml_climate_sa.position (id, x, y) VALUES (0, 500, 0);
INSERT INTO ml_climate_sa.position (id, x, y) VALUES (1, 2000, 0);
INSERT INTO ml_climate_sa.position (id, x, y) VALUES (2, 1000, 0);
INSERT INTO ml_climate_sa.position (id, x, y) VALUES (3, 0, 0);

INSERT INTO ml_climate_sa.planet (id, distance, name, velocity, position_id) VALUES (0, 500, 'farengi', 1, 0);
INSERT INTO ml_climate_sa.planet (id, distance, name, velocity, position_id) VALUES (1, 2000, 'betasoide', 3, 1);
INSERT INTO ml_climate_sa.planet (id, distance, name, velocity, position_id) VALUES (2, 1000, 'vulcano', -5, 2);
INSERT INTO ml_climate_sa.planet (id, distance, name, velocity, position_id) VALUES (3, 0, 'sun', 0, 3);

INSERT INTO ml_climate_sa.constelation (id, sun_id) VALUES (0, 3);

INSERT INTO ml_climate_sa.constelation_planets (constelation_id, planets_id, id) VALUES (0, 0, 0);
INSERT INTO ml_climate_sa.constelation_planets (constelation_id, planets_id, id) VALUES (0, 1, 1);
INSERT INTO ml_climate_sa.constelation_planets (constelation_id, planets_id, id) VALUES (0, 2, 2);
```