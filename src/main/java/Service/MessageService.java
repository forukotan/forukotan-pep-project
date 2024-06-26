package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message newMessage(int posted_by,String message_text, Long time_posted_epoch)
    {
        if (message_text== null|| message_text.trim().isEmpty())
        {
            return null;
        }
        if (message_text.length() > 255)
        {
            return null;
        }
        if(!messageDAO.posterExists(posted_by))
        {
            return null;
        }

        return messageDAO.createMessage(posted_by, message_text,time_posted_epoch);
    }

    public List<Message> allMessages(){
        return messageDAO.allMessage();
    }

    public Message messageBytheID(int messageId)
    {
        return messageDAO.getMessageByID(messageId);
    }

    public Message deleteMessage(int messageId) {
        
        Message messageToDelete = messageDAO.getMessageByID(messageId);
    
        
        if (messageToDelete != null && messageDAO.deleteMessageById(messageId)) {
            return messageToDelete;
        }
        return null;
    }

    public Message upMessage(int messageId, String newMessageText){

        
        if(newMessageText == null || newMessageText.isBlank()|| newMessageText.length() > 255)
        {
            return null;
        }
        Message checkMessageId= messageDAO.getMessageByID(messageId);
        if (checkMessageId==null)
        {
            return null;
        }
        
        return messageDAO.updateMessage(messageId, newMessageText);

    }

    public List<Message> messageByUser(int accountId)
    {
        return messageDAO.retieveByUser(accountId);
    }
    



}

