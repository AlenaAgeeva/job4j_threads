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

    public boolean update(Base model) throws OptimisticException {
        if (model.version() != findById(model.id()).get().version()) {
            throw new OptimisticException("Different cache versions.");
        }
        var stored = memory.computeIfPresent(model.id(),
                (id, base) ->
                        new Base(model.id(), model.name(), model.version() + 1));
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
