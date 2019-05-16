import account.Account;
import account.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {

  private static AccountService accountService = new AccountService();
  private static ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    get("/all", (req, res) -> accountService.getAll());
    post(
        "/transfer",
        (request, response) -> {
          Collection<Account> accounts = accountService.getAll();
          String value = mapper.writeValueAsString(accounts);

          return value;
        });
  }
}
