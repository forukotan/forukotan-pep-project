package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
public boolean posterExists(int posted_by){
    
    Connection connection = ConnectionUtil.getConnection();
        try
        {
        String sql = "SELECT COUNT(*) FROM message WHERE posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setInt(1, posted_by);

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


public Message createMessage(int posted_by,String message_text, Long time_posted_epoch){
    Connection connection = ConnectionUtil.getConnection();
    
    try
    {
        String sql = "insert into message (posted_by,message_text,time_posted_epoch ) values(?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, posted_by);
        preparedStatement.setString(2, message_text);
        preparedStatement.setLong(3, time_posted_epoch);
       
        preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next())
        {
            int messageID = resultSet.getInt(1);
            Message message=new Message(messageID,posted_by,message_text,time_posted_epoch);
            //message.setMessage_id(messageID);
            return message;
        }
    }catch (SQLException e) {
        e.printStackTrace();
    }
    
    return null;



}

public List<Message> allMessage()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try
        {
            String sql = "Select * from Message";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
               rs.getInt("posted_by"),
               //rs.getInt("message_id"),
               rs.getString("message_text"),
               rs.getLong("time_posted_epoch") );
               
            
            message.setMessage_id(rs.getInt("message_id")); // Set the message_id here
            messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        
    }

    return messages;
}
public Message getMessageByID(int messageID)
{
    Connection connection = ConnectionUtil.getConnection();
    Message messagebyID = null;
    try
    {
        String sql = " Select * from message where message_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,messageID);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            messagebyID = new Message(
                rs.getInt("posted_by"),
                rs.getInt("message_id"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return messagebyID;
}

public boolean deleteMessageById(int messageId) {
    Connection connection = ConnectionUtil.getConnection();
    boolean isDeleted = false;

    try {
        String deleteSql = "DELETE FROM message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        preparedStatement.setInt(1, messageId);
        //google why quickfix said make this an int instead of result
        int rowsAffected = preparedStatement.executeUpdate();

        isDeleted = rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return isDeleted;
}

public Message updateMessage(int messageId, String newMessageText){
    Connection connection = ConnectionUtil.getConnection();
    Message updatedMessage = null;
    try
    {
        String sql="Update message Set message_text = ? where message_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(2,messageId);
        preparedStatement.setString(1, newMessageText);

        int rows = preparedStatement.executeUpdate();
        
        if (rows > 0) {
            
            updatedMessage = getMessageByID(messageId);
            
        }
    }catch (SQLException e) {
        System.out.println(e.getMessage());
}
return updatedMessage;


}


public List<Message>retieveByUser(int accountId)
{
    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();
    try
    {
        String sql = "Select * FROM message WHERE posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,accountId);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next())
        {
            Message message = new Message(
                rs.getInt("posted_by"),
                rs.getInt("message_id"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            
        }
        

    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return messages;

    
    
    
}

}