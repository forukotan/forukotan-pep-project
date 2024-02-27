package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

// maybe try to change return tye from boolean to Account
public class AccountDAO {
    
    public boolean usernameExists(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
        String sql = "SELECT COUNT(*) FROM account WHERE username = ?";
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



    public Account addAccount(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        Account account = new Account();
        try
        {
            //we do insert to add to the username and password column, but we need to get the user id which is auto-generated
            // in the prepared statement in addition to the sql variable

            String sql ="INSERT INTO Account (username, password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, username, password);
            }



        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        

        }
        return null;
    }


//     As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.
// The request body will contain a JSON representation of an Account, not containing an account_id. 
//In the future, this action may generate a Session token to allow the user to securely use 
//the site. We will not worry about this for now.

// - The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. If successful, the response body should contain a JSON of the account in the response body, including its account_id. The response status should be 200 OK, which is the default.
// - If the login is not successful, the response status should be 401. (Unauthorized)
    public Account loginUser(String username, String password)
    {
       Connection connection = ConnectionUtil.getConnection();

      try{
        String sql ="Select * from account where username=? and password=?";
        PreparedStatement ps = connection.prepareStatement(sql);
 
        ps.setString(1, username);
        ps.setString(2, password);
 
        ResultSet rs= ps.executeQuery();
 
        if(rs.next()){
           int accountid =rs.getInt("account_id");
            String user=rs.getString("username");
            String pass =rs.getString("password");
         return new Account(accountid,user,pass);
     }
      }
      catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;   
       
        
    }
}

