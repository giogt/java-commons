package org.giogt.commons.core.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChunkIterator<T> implements Iterator<Chunk<T>> {

    private final List<T> list;
    private final int chunkSize;
    private final boolean onlyMinAndMax;

    private Chunk currentChunk = null;

    public ChunkIterator(List<T> list, int chunkSize) {
        this(list, chunkSize, false);
    }

    public ChunkIterator(List<T> list, int chunkSize, boolean onlyMinAndMax) {
        this.onlyMinAndMax = onlyMinAndMax;
        checkPreconditions(list, chunkSize);

        this.list = list;
        this.chunkSize = chunkSize;
    }

    void checkPreconditions(List<T> list, int chunkSize) {
        if (list == null) {
            throw new IllegalArgumentException("<list> cannot be null");
        }
        if (chunkSize < 1) {
            throw new IllegalArgumentException("<chunkSize> cannot be less than <1>");
        }
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public boolean hasNext() {
        if (list.size() == 0) {
            return false;
        }
        if (currentChunk == null) {
            // first element
            return true;
        }

        return currentChunk.getMaxIndex() < (list.size() - 1);
    }

    @Override
    public Chunk<T> next() {
        if (!hasNext()) {
            throw new ArrayIndexOutOfBoundsException("no next elements available");
        }

        int minIndex;
        int maxIndex;
        if (currentChunk == null) {
            // first element
            minIndex = 0;
            maxIndex = Math.min(chunkSize - 1, list.size() - 1);
        } else {
            // next element
            minIndex = currentChunk.getMaxIndex() + 1;
            maxIndex = Math.min(minIndex + chunkSize - 1, list.size() - 1);
        }

        currentChunk = new Chunk();
        currentChunk.setMinIndex(minIndex);
        currentChunk.setMaxIndex(maxIndex);
        currentChunk.setMinValue(list.get(minIndex));
        currentChunk.setMaxValue(list.get(maxIndex));

        if (!onlyMinAndMax) {
            List<T> chunk = new ArrayList<>(maxIndex - minIndex + 1);
            for (int i = minIndex; i <= maxIndex; i++) {
                chunk.add(list.get(i));
            }
            currentChunk.setChunk(chunk);
        }

        return currentChunk;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "remove operation not supported by this iterator [" +
                        this.getClass().getName() + "]");
    }

}
