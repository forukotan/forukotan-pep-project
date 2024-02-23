package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {
    public boolean usernameExists(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
        String sql = "SELECT COUNT(*) FROM accounts WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }


        
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }



    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            //we do insert to add to the username and password column, but we need to get the user id which is auto-generated
            // in the prepared statement in addition to the sql variable

            String sql ="INSERT INTO Account (username, password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }



        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        

        }
        return null;
    }

    
}

