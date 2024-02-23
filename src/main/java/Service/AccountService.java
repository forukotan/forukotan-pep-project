package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
        // we need to insert he second methodi made
    public Account Newuser( String username, String password) {
        if (username == null || username.trim().isEmpty())  {
            return null;
        }
        if (password == null || password.length() < 4) {
            return null;
        }
        if (accountDAO.usernameExists(username)) {
            return null;
        }


        Account newAccount = new Account(username, password);
        
        return accountDAO.addAccount(newAccount) ;

    }


}
