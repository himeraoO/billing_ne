DROP TABLE IF EXISTS tariff_tariff_option_list;
DROP TABLE IF EXISTS tariff;

CREATE TABLE IF NOT EXISTS tariff (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    active TINYINT NOT NULL,
    PRIMARY KEY (id))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS tariff_tariff_option_list (
    tariff_id BIGINT NOT NULL,
    tariff_option_list_id BIGINT NOT NULL,
    CONSTRAINT fk_tariff_tariff_option_list_tariff1
        FOREIGN KEY (tariff_id)
            REFERENCES tariff (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_tariff_tariff_option_list_option1
        FOREIGN KEY (tariff_option_list_id)
            REFERENCES tariff_option (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION)
ENGINE = InnoDB;