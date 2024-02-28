package Controller;

import java.util.List;

import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public SocialMediaController(AccountService accountService,MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
       // POST localhost:8080/register
       app.post("/register", this::userRegister);
       app.post("/login",this::userLogin);
       app.post("/messages", this::messageNew);
       app.get("/messages", this::getAllMessages);
       app.get("/messages/{message_id}",this::messageWithID);
       app.delete("/messages/{message_id}", this::deleteMessage);
       app.patch("/messages/{message_id}", this::updateMessage);
       app.get("/accounts/{account_id}/messages", this::retrieveByUsers);
        return app;
    }

    private void userRegister(Context context) {
        Account accountBody = context.bodyAsClass(Account.class);
        Account registeredAccount = accountService.newUser(accountBody.username, accountBody.password);
        if(registeredAccount != null)
        {
            context.json(registeredAccount);
        }
        else{
            context.status(400);
        }

   }
   private void userLogin(Context context)
   {
    Account accountBody =context.bodyAsClass(Account.class);
    Account loginUser = accountService.loginVerify(accountBody.username, accountBody.password);
    if(loginUser!= null)
    {
        context.json(loginUser);
    }
    else
    {
        context.status(401);
    }
    

   }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void messageNew(Context context)
    {
        Message messageBody = context.bodyAsClass(Message.class);
        Message messageMade = messageService.newMessage(messageBody.getPosted_by(),messageBody.getMessage_text(),messageBody.getTime_posted_epoch());
        if(messageMade!= null)
    {
        context.json(messageMade);
    }
    else
    {
        context.status(400);
    }
    
    }
    private void getAllMessages(Context context)
    {
        List<Message> messages = messageService.allMessages();
        context.json(messages);
    }
    private void messageWithID(Context context)
    {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.messageBytheID(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.result(""); 
        }
    }

    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
    
        if (deletedMessage != null) {
            context.json(deletedMessage);
        } else {
            context.status(200).result("");
        }
    }

    private void updateMessage (Context context)
    {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
         String newMessageText = context.bodyAsClass(Message.class).getMessage_text();
        Message updatedMessage = messageService.upMessage(messageId, newMessageText);

        if ( updatedMessage!= null) {
            context.json(updatedMessage);
        } else {
            context.status(400).result("");
        }
    }

    private void retrieveByUsers(Context context)
    {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
    List<Message> messages = messageService.messageByUser(accountId);
    context.json(messages);
    }
    
}

