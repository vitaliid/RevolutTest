import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountDAO {
  private final Map<String, Account> inMemoryDb;

  public AccountDAO() {
    this.inMemoryDb = new HashMap<>();

    // having predefined
    Account account1 = new Account(AccountNumberGenerator.generate(), "testUser1", 2000);
    Account account2 = new Account(AccountNumberGenerator.generate(), "testUser2", 3000);
    this.inMemoryDb.put(account1.getNumber(), account1);
    this.inMemoryDb.put(account2.getNumber(), account2);
  }

  public Collection<Account> getAll() {
    return inMemoryDb.values();
  }

  public Optional<Account> get(String accountNumber) {
    return Optional.ofNullable(inMemoryDb.get(accountNumber));
  }

  public Account create(Account newAccount) {
    Account addedAccount = inMemoryDb.putIfAbsent(newAccount.getNumber(), newAccount);
    return addedAccount != null ? addedAccount : newAccount;
  }

  public Account update(Account account) {
    return inMemoryDb.put(account.getNumber(), account);
  }
}
