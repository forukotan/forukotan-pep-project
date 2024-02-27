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
}

