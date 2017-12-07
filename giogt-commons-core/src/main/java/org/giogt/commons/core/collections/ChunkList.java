package org.giogt.commons.core.collections;

import java.util.Iterator;
import java.util.List;

/**
 * A wrapper on {@link List} that allows to iterate it in chunks, according to
 * the specified chunk size.
 * <p>
 * The iterator returned by this chunk list will return a {@link Chunk} object
 * at every iteration, containing the minimum and maximum indexes and values
 * and the chunk (a list with all the elements from the minimum to the maximum
 * index).
 * <p>
 * The <tt>onlyMinAndMax</tt> flag (default = <tt>false</tt>) can be specified.
 * If <tt>true</tt>, for every chunk only the minimum and maximum indexes and
 * values are set and the chunk will always be <tt>null</tt>. This can be used
 * for performance reasons when you are not interested in the whole chunk, but
 * only in the minimum and maximum values for every chunk.
 *
 * @param <T>
 */
public class ChunkList<T> implements Iterable<Chunk<T>> {

    private final List<T> list;
    private final int chunkSize;
    private final boolean onlyMinAndMax;

    public ChunkList(List<T> list, int chunkSize) {
        this(list, chunkSize, false);
    }

    public ChunkList(List<T> list, int chunkSize, boolean onlyMinAndMax) {
        checkPreconditions(list, chunkSize);

        this.list = list;
        this.chunkSize = chunkSize;
        this.onlyMinAndMax = onlyMinAndMax;
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

    public int getChunkSize() {
        return chunkSize;
    }

    public boolean isOnlyMinAndMax() {
        return onlyMinAndMax;
    }

    @Override
    public Iterator<Chunk<T>> iterator() {
        return new ChunkIterator<>(list, chunkSize, onlyMinAndMax);
    }

    @Override
    public String toString() {
        return "ChunkList{" +
                "list=" + list +
                ", chunkSize=" + chunkSize +
                '}';
    }
}
