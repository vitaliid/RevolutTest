import java.util.Objects;

public class Account {

    private String number;
    private String userId;
    private long balance;

    public Account() {
    }

    public Account(String number, String userId, long balance) {
        this.number = number;
        this.userId = userId;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
