package Controller;

import Model.Account;
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

    public SocialMediaController(){
        this.accountService = new AccountService();
    }

    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
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


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}

