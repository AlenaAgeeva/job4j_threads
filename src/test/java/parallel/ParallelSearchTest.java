package parallel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {
    @Test
    public void testLinearSearchInteger() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertThat(ParallelSearch.search(array, 3)).isEqualTo(2);
        assertThat(ParallelSearch.search(array, 1)).isEqualTo(0);
        assertThat(ParallelSearch.search(array, 5)).isEqualTo(4);
        assertThat(ParallelSearch.search(array, 2)).isEqualTo(1);
    }

    @Test
    public void testLinearSearchCharacter() {
        Character[] array = {'a', 'b', 'c', 'd', 'f'};
        assertThat(ParallelSearch.search(array, 'b')).isEqualTo(1);
        assertThat(ParallelSearch.search(array, 'f')).isEqualTo(4);
        assertThat(ParallelSearch.search(array, 'c')).isEqualTo(2);
        assertThat(ParallelSearch.search(array, 'a')).isEqualTo(0);
    }

    @Test
    public void testRecursiveSearchDouble() {
        Double[] array = {10.6, 34.6, 89.9, 24.5, 13.2, 45.6, 23.8, 86.5, 345.6, 334.6, 23.5, 345.3, 111.3};
        assertThat(ParallelSearch.search(array, 89.9)).isEqualTo(2);
        assertThat(ParallelSearch.search(array, 345.6)).isEqualTo(8);
        assertThat(ParallelSearch.search(array, 23.5)).isEqualTo(10);
    }

    @Test
    public void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(ParallelSearch.search(array, 16)).isEqualTo(-1);
        assertThat(ParallelSearch.search(array, 20)).isEqualTo(-1);
        assertThat(ParallelSearch.search(array, 0)).isEqualTo(-1);
    }

}