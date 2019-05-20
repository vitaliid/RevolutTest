import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class AccountServiceTest {
  private AccountService accountService;
  private int accountsAmount;

  @Before
  public void initialData() {
    accountService = new AccountService();
    accountsAmount = accountService.getAll().size();
  }

  @Test
  public void testGetAllAccounts() {
    assertThat(accountService.getAll(), hasSize(2));
  }

  @Test
  public void testCreateAccount() {

    Account newAccount = accountService.create("testUser1", 0);

    assertThat(accountService.getAll(), hasItem(newAccount));
    assertThat(accountService.getAll(), hasSize(accountsAmount + 1));
  }

  @Test
  public void testGetAccount() {
    Account expectedAccount = accountService.create("testUser1", 0);
    Optional<Account> foundAccount = accountService.get(expectedAccount.getNumber());

    assertEquals(foundAccount.orElse(null), expectedAccount);
  }

  @Test
  public void testTransferToNotExistingAccountFailed() {
    Account accountFrom = accountService.create("testUser1", 20);

    boolean transferResult = accountService.transfer(accountFrom.getNumber(), "fakeAccount", 20);

    assertFalse(transferResult);
  }

  @Test
  public void testTransferFromNotExistingAccountFailed() {
    Account accountTo = accountService.create("testUser2", 10);

    boolean transferResult = accountService.transfer("fakeAccount", accountTo.getNumber(), 20);

    assertFalse(transferResult);
  }

  @Test
  public void testTransferFromBankruptFailed() {
    Account accountFrom = accountService.create("testUser1", 0);
    Account accountTo = accountService.create("testUser2", 10);

    boolean transferResult =
        accountService.transfer(accountFrom.getNumber(), accountTo.getNumber(), 1);

    assertFalse(transferResult);
  }

  @Test
  public void testTransferSucceed() {
    Account accountFrom = accountService.create("testUser1", 20);
    Account accountTo = accountService.create("testUser2", 10);

    // keep balances from account references
    final long accountFromBalance = accountFrom.getBalance();
    final long accountToBalance = accountTo.getBalance();

    final int transferAmount = 20;

    boolean transferResult =
        accountService.transfer(accountFrom.getNumber(), accountTo.getNumber(), transferAmount);
    assertTrue(transferResult);

    Account accountFromUpdated = accountService.get(accountFrom.getNumber()).orElse(null);
    assertNotNull(accountFromUpdated);
    assertEquals(accountFromUpdated.getBalance(), accountFromBalance - transferAmount);

    Account accountToUpdated = accountService.get(accountTo.getNumber()).orElse(null);
    assertNotNull(accountToUpdated);
    assertEquals(accountToUpdated.getBalance(), accountToBalance + transferAmount);
  }
}
