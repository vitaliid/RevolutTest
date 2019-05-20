import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccountService {

  private AccountDAO accountDAO;

  private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public AccountService() {
    this.accountDAO = new AccountDAO();
  }

  public Collection<Account> getAll() {
    return accountDAO.getAll();
  }

  public Optional<Account> get(String accountNumber) {
    return accountDAO.get(accountNumber);
  }

  public Account create(String userId, long balance) {
    Account newAccount = new Account(AccountNumberGenerator.generate(), userId, balance);
    return accountDAO.create(newAccount);
  }

  /**
   * This method perform transfer between to existing accounts. Of course it can be improved by
   * different validations and by providing more information in return statement about cause of
   * failure and by having transaction things and using BigDecimal and etc. But I was asked to avoid
   * over-engineering of solution
   *
   * @param accountNumberFrom
   * @param accountNumberTo
   * @param amount of money to be transferred
   * @return result of operation
   */
  public boolean transfer(String accountNumberFrom, String accountNumberTo, long amount) {

    Account accountFrom = accountDAO.get(accountNumberFrom).orElse(null);
    Account accountTo = accountDAO.get(accountNumberTo).orElse(null);

    // all accounts must be real
    if (accountFrom == null || accountTo == null) {
      return false;
    }

    // getting ids from JVM pool for locking purposes
    String accountFromId = accountFrom.getNumber().intern();
    String accountToId = accountTo.getNumber().intern();
    String lock1, lock2;

    // ensure that order of locks is always the same
    if (accountFromId.compareTo(accountToId) < 0) {
      lock1 = accountFromId;
      lock2 = accountToId;
    } else {
      lock1 = accountToId;
      lock2 = accountFromId;
    }

    synchronized (lock1) {
      synchronized (lock2) {
        long balanceOfSource = accountFrom.getBalance();

        // check if user has enough money for this operation
        if (balanceOfSource < amount) return false;

        // updating user who sends money first
        accountFrom.setBalance(balanceOfSource - amount);
        accountDAO.update(accountFrom);

        // updating user who receives money first
        long balanceOfDestination = accountTo.getBalance();
        accountTo.setBalance(balanceOfDestination + amount);
        accountDAO.update(accountTo);
      }
    }

    return true;
  }
}
