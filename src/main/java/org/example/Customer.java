package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Customer {
    private static final Logger logger = LogManager.getLogger(Customer.class);
    private String name;
    private String id;

    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        logger.info("Logging from Customer class");
        return "Customer [name=" + name + ", id=" + id + "]";
    }
}