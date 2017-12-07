package org.giogt.commons.core.collections;

import java.util.List;

public class Chunk<T> {

    private int minIndex;
    private int maxIndex;
    private T minValue;
    private T maxValue;
    private List<T> chunk;

    Chunk() {
    }

    public int getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    public List<T> getChunk() {
        return chunk;
    }

    public void setChunk(List<T> chunk) {
        this.chunk = chunk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chunk<?> chunk1 = (Chunk<?>) o;

        if (minIndex != chunk1.minIndex) return false;
        if (maxIndex != chunk1.maxIndex) return false;
        if (minValue != null ? !minValue.equals(chunk1.minValue) : chunk1.minValue != null) return false;
        if (maxValue != null ? !maxValue.equals(chunk1.maxValue) : chunk1.maxValue != null) return false;
        return chunk != null ? chunk.equals(chunk1.chunk) : chunk1.chunk == null;

    }

    @Override
    public int hashCode() {
        int result = minIndex;
        result = 31 * result + maxIndex;
        result = 31 * result + (minValue != null ? minValue.hashCode() : 0);
        result = 31 * result + (maxValue != null ? maxValue.hashCode() : 0);
        result = 31 * result + (chunk != null ? chunk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "minIndex=" + minIndex +
                ", maxIndex=" + maxIndex +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", chunk=" + chunk +
                '}';
    }

    static <T> ChunkBuilder<T> builder() {
        return new ChunkBuilder<>();
    }

    static class ChunkBuilder<T> {

        private Integer minIndex;
        private Integer maxIndex;
        private T minValue;
        private T maxValue;
        private List<T> chunk;

        public ChunkBuilder<T> withMinIndex(int minIndex) {
            this.minIndex = minIndex;
            return this;
        }

        public ChunkBuilder<T> withMaxIndex(int maxIndex) {
            this.maxIndex = maxIndex;
            return this;
        }

        public ChunkBuilder<T> withMinValue(T minValue) {
            this.minValue = minValue;
            return this;
        }

        public ChunkBuilder<T> withMaxValue(T maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public ChunkBuilder<T> withChunk(List<T> chunk) {
            this.chunk = chunk;
            return this;
        }

        public Chunk<T> build() {
            Chunk<T> newChunk = new Chunk<>();
            newChunk.setMinIndex(minIndex);
            newChunk.setMaxIndex(maxIndex);
            newChunk.setMinValue(minValue);
            newChunk.setMaxValue(maxValue);
            newChunk.setChunk(chunk);

            return newChunk;
        }

    }

}
