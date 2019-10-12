package com.freshomer.thememagic.theme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class ObserverList<E> implements Iterable<E> {

    public interface RewindableIterator<E> extends Iterator<E> {
        void rewind();
    }

    public final List<E> mObservers = new ArrayList<E>();
    private int mIterationDepth;
    private int mCount;
    private boolean mNeedsCompact;

    public ObserverList() {}

    public boolean addObserver(E obs) {
        if (obs == null || mObservers.contains(obs)) {
            return false;
        }

        boolean result = mObservers.add(obs);
        assert result;

        ++mCount;
        return true;
    }

    public boolean removeObserver(E obs) {
        if (obs == null) {
            return false;
        }

        int index = mObservers.indexOf(obs);
        if (index == -1) {
            return false;
        }

        if (mIterationDepth == 0) {
            // No one is iterating over the list.
            mObservers.remove(index);
        } else {
            mNeedsCompact = true;
            mObservers.set(index, null);
        }
        --mCount;
        assert mCount >= 0;

        return true;
    }

    public boolean hasObserver(E obs) {
        return mObservers.contains(obs);
    }

    public void clear() {
        mCount = 0;

        if (mIterationDepth == 0) {
            mObservers.clear();
            return;
        }

        int size = mObservers.size();
        mNeedsCompact |= size != 0;
        for (int i = 0; i < size; i++) {
            mObservers.set(i, null);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ObserverListIterator();
    }

    public RewindableIterator<E> rewindableIterator() {
        return new ObserverListIterator();
    }

    public int size() {
        return mCount;
    }


    public boolean isEmpty() {
        return mCount == 0;
    }

    private void compact() {
        assert mIterationDepth == 0;
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            if (mObservers.get(i) == null) {
                mObservers.remove(i);
            }
        }
    }

    private void incrementIterationDepth() {
        mIterationDepth++;
    }

    private void decrementIterationDepthAndCompactIfNeeded() {
        mIterationDepth--;
        assert mIterationDepth >= 0;
        if (mIterationDepth > 0) return;
        if (!mNeedsCompact) return;
        mNeedsCompact = false;
        compact();
    }

    private int capacity() {
        return mObservers.size();
    }

    private E getObserverAt(int index) {
        return mObservers.get(index);
    }

    private class ObserverListIterator implements RewindableIterator<E> {
        private int mListEndMarker;
        private int mIndex;
        private boolean mIsExhausted;

        private ObserverListIterator() {
            ObserverList.this.incrementIterationDepth();
            mListEndMarker = ObserverList.this.capacity();
        }

        @Override
        public void rewind() {
            compactListIfNeeded();
            ObserverList.this.incrementIterationDepth();
            mListEndMarker = ObserverList.this.capacity();
            mIsExhausted = false;
            mIndex = 0;
        }

        @Override
        public boolean hasNext() {
            int lookupIndex = mIndex;
            while (lookupIndex < mListEndMarker
                    && ObserverList.this.getObserverAt(lookupIndex) == null) {
                lookupIndex++;
            }
            if (lookupIndex < mListEndMarker) return true;

            // We have reached the end of the list, allow for compaction.
            compactListIfNeeded();
            return false;
        }

        @Override
        public E next() {
            while (mIndex < mListEndMarker && ObserverList.this.getObserverAt(mIndex) == null) {
                mIndex++;
            }
            if (mIndex < mListEndMarker) return ObserverList.this.getObserverAt(mIndex++);

            compactListIfNeeded();
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void compactListIfNeeded() {
            if (!mIsExhausted) {
                mIsExhausted = true;
                ObserverList.this.decrementIterationDepthAndCompactIfNeeded();
            }
        }
    }
}
