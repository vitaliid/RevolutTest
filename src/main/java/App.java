import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static spark.Spark.*;

public class App {

  private static AccountService accountService = new AccountService();
  private static ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    System.out.println("Application will start on port 4567.");
    get(
        "/account/:number",
        (req, res) -> {
          String accountNumber = req.params(":number");
          Optional<Account> account = accountService.get(accountNumber);

          if (account.isPresent()) {
            return mapper.writeValueAsString(account.get());
          } else {
            res.status(404);
            return "Could't find account with number: " + accountNumber;
          }
        });

    put(
        "/account",
        (request, response) -> {
          String userId = request.queryParams("userId");
          long balance = Long.parseLong(request.queryParamOrDefault("balance", "0"));

          Account newAccount = accountService.create(userId, balance);
          return mapper.writeValueAsString(newAccount);
        });

    get("/accounts", (req, res) -> accountService.getAll());

    post(
        "/transfer",
        (request, response) -> {
          String accountFrom = request.queryParams("accountFrom");
          String accountTo = request.queryParams("accountTo");
          long amount = Long.parseLong(request.queryParams("amount"));

          boolean result = accountService.transfer(accountFrom, accountTo, amount);
          return mapper.writeValueAsString(result);
        });
  }
}
