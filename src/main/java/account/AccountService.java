package account;

import dao.AccountDAO;

import java.util.Collection;

public class AccountService {

  private AccountDAO accountDAO;

  public AccountService() {
    this.accountDAO = new AccountDAO();
  }

  public Collection<Account> getAll() {
    return accountDAO.getAll();
  }

  public Account create(String userId, long balance) {

    return accountDAO.create(userId, balance);
  }

  public Account transfer(String fromAccount, String toAccount) {

    return null;
  }
}
