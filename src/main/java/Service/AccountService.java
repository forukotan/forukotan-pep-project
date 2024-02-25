package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;
import io.javalin.http.Context;
import io.javalin.Javalin;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
 
public Account newUser(String username,String password) 
{
    if (username == null || username.trim().isEmpty())
    {
        return null;
    }
    else if(password == null || password.length() < 4)
    {
        return null;
    }
    else if(accountDAO.usernameExists(username))
    {
        return null;
    }
    
    return accountDAO.addAccount(username,password);
    
}
}
