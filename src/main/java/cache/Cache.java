package cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) {
        var stored = memory.computeIfPresent(model.id(),
                (id, base) -> {
                    if (model.version() != base.version()) {
                        try {
                            throw new OptimisticException("Different cache versions.");
                        } catch (OptimisticException e) {
                            e.printStackTrace();
                        }
                    }
                    return new Base(model.id(), model.name(), model.version() + 1);
                });
        return stored != null;
    }

    public boolean delete(int id) {
        return memory.remove(id) != null;
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
