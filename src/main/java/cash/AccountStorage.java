package cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean result = false;
            Account accountFrom = getById(fromId).orElse(null);
            Account accountTo = getById(toId).orElse(null);
            if (accountFrom != null
                    && accountTo != null
                    && accountFrom.amount() >= amount) {
                result = update(new Account(accountFrom.id(), accountFrom.amount() - amount))
                        && update(new Account(accountTo.id(), accountTo.amount() + amount));
            }
            return result;
        }
    }
}
