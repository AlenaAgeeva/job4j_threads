package cache;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {

    @Test
    public void whenAddFind() {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    public void whenVersionsNotEqualThrowException() {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class)
                .hasMessage("Different cache versions.");
    }

    @Test
    void testAdd() {
        var model = new Base(1, "Model 1", 1);
        var cache = new Cache();
        assertThat(cache.add(model)).isTrue();
    }

    @Test
    void testUpdate() {
        var model = new Base(2, "Model 2", 1);
        var cache = new Cache();
        cache.add(model);
        model = new Base(2, "Updated Model 2", 1);
        assertThat(cache.update(model)).isTrue();
    }


    @Test
    void testDelete() {
        var model = new Base(4, "Model 4", 1);
        var cache = new Cache();
        cache.add(model);
        assertThat(cache.delete(4)).isTrue();
    }

    @Test
    void testFindById() {
        var model = new Base(5, "Model 5", 1);
        var cache = new Cache();
        cache.add(model);
        Optional<Base> foundModel = cache.findById(5);
        assertThat(foundModel.isPresent()).isTrue();
        assertThat("Model 5").isEqualTo(foundModel.get().name());
    }

    @Test
    void testFindByIdNotFound() {
        var cache = new Cache();
        Optional<Base> foundModel = cache.findById(100);
        assertThat(foundModel.isPresent()).isFalse();
    }
}