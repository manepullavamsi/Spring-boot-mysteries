package com.testcontainer.example.testcontainermysteries;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {
//final static MySQLContainer<?> mySQLContainer=new MySQLContainer<>("mysql");
static PostgreSQLContainer<?> mySQLContainer = new PostgreSQLContainer<>(
        "postgres:15-alpine"
);
CustomerService customerService;

    @BeforeAll
    static void beforeAll(){
    mySQLContainer.start();
}
    @AfterAll
    static void afterAll(){
    mySQLContainer.stop();
}

    @BeforeEach
    void setup(){
    DBConnectionProvider connectionProvider=new DBConnectionProvider(mySQLContainer.getJdbcUrl(),mySQLContainer.getUsername(),
            mySQLContainer.getPassword());
    customerService=new CustomerService(connectionProvider);
}

    @Test
    void getCustomerTest()
{
    customerService.createCustomer(new Customer(1L,"Venkat"));
    customerService.createCustomer(new Customer(2L,"Venkatesh"));

    var customerList=customerService.getCustomers();
    assertEquals(2,customerList.size());
}


}
