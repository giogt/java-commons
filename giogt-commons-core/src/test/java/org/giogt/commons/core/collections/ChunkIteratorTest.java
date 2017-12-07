package org.giogt.commons.core.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChunkIteratorTest {

    @Test
    public void chunkIterator_whenListIsNull_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                buildChunkIterator(null, 1)
        );
    }

    @Test
    public void chunkIterator_whenChunkSizeIsLessThanOne_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                buildChunkIterator(null, 0)
        );
    }

    @Test
    public void hasNext_whenListIsEmpty_mustReturnFalse() {
        List<String> list = Collections.emptyList();
        int chunkSize = 1;

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);
        boolean result = iut.hasNext();

        assertThat(result, is(false));
    }

    @Test
    public void hasNext_whenListIsNotEmpty_mustReturnTrue() {
        List<String> list = Arrays.asList("foo", "bar");
        int chunkSize = 1;

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);
        boolean result = iut.hasNext();

        assertThat(result, is(true));
    }

    @Test
    public void next_whenListIsEmpty_mustThrowArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            List<String> list = Collections.emptyList();
            int chunkSize = 1;

            ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);
            iut.next();
        });
    }

    @Test
    public void next_withOneChunkWithSize2_mustReturnTheChunk() {
        List<String> list = Arrays.asList("foo", "bar");
        int chunkSize = 2;
        Chunk<String> expected = Chunk.<String>builder()
                .withMinIndex(0)
                .withMaxIndex(1)
                .withMinValue("foo")
                .withMaxValue("bar")
                .withChunk(Arrays.asList("foo", "bar"))
                .build();

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);
        Chunk<String> result = iut.next();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void next_withOneChunkWithSize4_mustReturnTheChunk() {
        List<String> list = Arrays.asList("1", "2", "3", "4");
        int chunkSize = 4;
        Chunk<String> expected = Chunk.<String>builder()
                .withMinIndex(0)
                .withMaxIndex(3)
                .withMinValue("1")
                .withMaxValue("4")
                .withChunk(Arrays.asList("1", "2", "3", "4"))
                .build();

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);
        Chunk<String> result = iut.next();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void next_mustWorkWithListSize4AndChunkSize1() {
        List<String> list = Arrays.asList("1", "2", "3", "4");
        int chunkSize = 1;

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);

        // chunk 1
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(0)
                .withMaxIndex(0)
                .withMinValue("1")
                .withMaxValue("1")
                .withChunk(Arrays.asList("1"))
                .build()));

        // chunk 2
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(1)
                .withMaxIndex(1)
                .withMinValue("2")
                .withMaxValue("2")
                .withChunk(Arrays.asList("2"))
                .build()));

        // chunk 3
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(2)
                .withMaxIndex(2)
                .withMinValue("3")
                .withMaxValue("3")
                .withChunk(Arrays.asList("3"))
                .build()));

        // chunk 4
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(3)
                .withMaxIndex(3)
                .withMinValue("4")
                .withMaxValue("4")
                .withChunk(Arrays.asList("4"))
                .build()));

        assertThat(iut.hasNext(), is(false));
    }

    @Test
    public void next_mustWorkWithListSize7AndChunkSize3() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        int chunkSize = 3;

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);

        // chunk 1
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(0)
                .withMaxIndex(2)
                .withMinValue("1")
                .withMaxValue("3")
                .withChunk(Arrays.asList("1", "2", "3"))
                .build()));

        // chunk 2
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(3)
                .withMaxIndex(5)
                .withMinValue("4")
                .withMaxValue("6")
                .withChunk(Arrays.asList("4", "5", "6"))
                .build()));

        // chunk 3
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(6)
                .withMaxIndex(6)
                .withMinValue("7")
                .withMaxValue("7")
                .withChunk(Arrays.asList("7"))
                .build()));

        assertThat(iut.hasNext(), is(false));
    }

    @Test
    public void next_mustWorkWithListSize8AndChunkSize3() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        int chunkSize = 3;

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);

        // chunk 1
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(0)
                .withMaxIndex(2)
                .withMinValue("1")
                .withMaxValue("3")
                .withChunk(Arrays.asList("1", "2", "3"))
                .build()));

        // chunk 2
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(3)
                .withMaxIndex(5)
                .withMinValue("4")
                .withMaxValue("6")
                .withChunk(Arrays.asList("4", "5", "6"))
                .build()));

        // chunk 3
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(6)
                .withMaxIndex(7)
                .withMinValue("7")
                .withMaxValue("8")
                .withChunk(Arrays.asList("7", "8"))
                .build()));

        assertThat(iut.hasNext(), is(false));
    }

    @Test
    public void next_mustWorkWithListSize9AndChunkSize3() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        int chunkSize = 3;

        ChunkIterator<String> iut = buildChunkIterator(list, chunkSize);

        // chunk 1
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(0)
                .withMaxIndex(2)
                .withMinValue("1")
                .withMaxValue("3")
                .withChunk(Arrays.asList("1", "2", "3"))
                .build()));

        // chunk 2
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(3)
                .withMaxIndex(5)
                .withMinValue("4")
                .withMaxValue("6")
                .withChunk(Arrays.asList("4", "5", "6"))
                .build()));

        // chunk 3
        assertThat(iut.hasNext(), is(true));
        assertThat(iut.next(), is(Chunk.<String>builder()
                .withMinIndex(6)
                .withMaxIndex(8)
                .withMinValue("7")
                .withMaxValue("9")
                .withChunk(Arrays.asList("7", "8", "9"))
                .build()));

        assertThat(iut.hasNext(), is(false));
    }

    private ChunkIterator<String> buildChunkIterator(List<String> list, int chunkSize) {
        return new ChunkIterator(list, chunkSize);
    }

}