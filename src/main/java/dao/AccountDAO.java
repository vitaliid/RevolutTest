package dao;

import account.Account;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AccountDAO {
  private final ConcurrentMap<String, Account> inMemoryDb;

  public AccountDAO() {
    this.inMemoryDb = new ConcurrentHashMap<>();

    Account account1 = new Account("12345678", "testUser1", 2000);
    Account account2 = new Account("12345679", "testUser2", 3000);

    this.inMemoryDb.put(account1.getNumber(), account1);
    this.inMemoryDb.put(account2.getNumber(), account2);
  }

  public Collection<Account> getAll() {
    return inMemoryDb.values();
  }

  public Account create(String userId, long balance) {

    return null;
  }

  public Account transfer(String fromAccount, String toAccount) {

    return null;
  }
}
