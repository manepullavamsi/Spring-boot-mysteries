package com.testcontainer.example.testcontainermysteries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private final DBConnectionProvider dbConnectionProvider;

    CustomerService(DBConnectionProvider dbConnectionProvider){
        this.dbConnectionProvider=dbConnectionProvider;
        createTableIfDoesnotExist();

    }
    public void createCustomer(Customer customer){
        try(Connection connection=this.dbConnectionProvider.getConnection())
        {
            PreparedStatement stmt=connection.prepareStatement("insert into customers(id,name) values(?,?)");
            stmt.setLong(1,customer.id());
            stmt.setString(2,customer.name());
            stmt.execute();
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public List<Customer> getCustomers(){
    List<Customer> customerList=new ArrayList<>();
        try(Connection connection=this.dbConnectionProvider.getConnection())
        {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from customers");
            var resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                customerList.add(new Customer(id, name));
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    void createTableIfDoesnotExist()  {
        try(Connection connection=this.dbConnectionProvider.getConnection()){
          PreparedStatement preparedstmt=connection.prepareStatement(        """
                    create table if not exists customers (
                        id bigint not null,
                        name varchar(255) not null,
                        primary key (id)
                    )
                    """
          );
          preparedstmt.execute();
        }
        catch (Exception ex)
        {
            throw new RuntimeException();
        }

    }
}
