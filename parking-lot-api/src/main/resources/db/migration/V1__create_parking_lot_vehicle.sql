create table parking_lot
(
    id bigserial not null
        constraint parking_lot_pkey
            primary key,
    motorcycle_spots bigint NOT NULL,
    car_spots bigint NOT NULL,
    van_spots bigint NOT NULL,
    current_spot bigint NOT NULL
);

create table vehicle
(
    id bigserial not null
        constraint vehicle_pkey
            primary key,
    parking_lot_id bigint NOT NULL
        constraint vehicle_constraint
            references parking_lot,
    type int NOT NULL,
    spot int NOT NULL,
    parked bigint NOT NULL
);