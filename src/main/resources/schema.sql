
    alter table TemperatureHistory
        drop
        foreign key FK_tuhu4m9xhcesggcb9kmamek6

    alter table TemperatureHistory
        drop
        foreign key FK_44w7dfp6sjhwse4wjpajjwng8

    alter table WeatherHistory
        drop
        foreign key FK_ipmokhoklnyf57g3khvrw7kik

    alter table WeatherHistory
        drop
        foreign key FK_1ikwegn2yewtt7p0yy2dhjijg

    drop table if exists City

    drop table if exists TemperatureHistory

    drop table if exists WeatherHistory

    create table City (
        id integer not null auto_increment,
        name varchar(255),
        population integer not null,
        province varchar(255),
        region varchar(255),
        primary key (id)
    )

    create table TemperatureHistory (
        id integer not null auto_increment,
        date date,
        temperature integer not null,
        time time,
        cityId integer not null,
        primary key (id)
    )

    create table WeatherHistory (
        id integer not null auto_increment,
        date date,
        time time,
        weather varchar(255),
        cityId integer not null,
        primary key (id)
    )

    alter table TemperatureHistory
        add constraint FK_tuhu4m9xhcesggcb9kmamek6
        foreign key (cityId)
        references City (id)

    alter table TemperatureHistory
        add constraint FK_44w7dfp6sjhwse4wjpajjwng8
        foreign key (id)
        references City (id)

    alter table WeatherHistory
        add constraint FK_ipmokhoklnyf57g3khvrw7kik
        foreign key (cityId)
        references City (id)

    alter table WeatherHistory
        add constraint FK_1ikwegn2yewtt7p0yy2dhjijg
        foreign key (id)
        references City (id)
