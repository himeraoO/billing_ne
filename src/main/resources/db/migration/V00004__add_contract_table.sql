DROP TABLE IF EXISTS contract_tariff_option_list;
DROP TABLE IF EXISTS contract;

CREATE TABLE IF NOT EXISTS contract (
    id BIGINT NOT NULL AUTO_INCREMENT,
    number VARCHAR(45) NOT NULL,
    active TINYINT NOT NULL,
    client_id BIGINT NOT NULL,
    tariff_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_contract_client1
        FOREIGN KEY (client_id)
            REFERENCES client (id)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION,
    CONSTRAINT fk_contract_tariff1
        FOREIGN KEY (tariff_id)
            REFERENCES tariff (id)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS contract_tariff_option_list (
    contract_id BIGINT NOT NULL,
    tariff_option_list_id BIGINT NOT NULL,
    CONSTRAINT fk_contract_tariff_option_list_contract1
        FOREIGN KEY (contract_id)
            REFERENCES contract (id)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION,
    CONSTRAINT fk_contract_tariff_option_list_option1
        FOREIGN KEY (tariff_option_list_id)
            REFERENCES tariff_option (id)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION)
ENGINE = InnoDB;