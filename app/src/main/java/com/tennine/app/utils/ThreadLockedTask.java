package com.tennine.app.utils;

import java.util.concurrent.atomic.AtomicReference;

public class ThreadLockedTask<T>{
    private AtomicReference<ResultWrapper<T>> mReference;

    public ThreadLockedTask() {
        mReference = new AtomicReference<>(new ResultWrapper<T>());
    }

    public T execute(Runnable runnable) {
        runnable.run();
        if (!mReference.get().mIsSet)
            lockUntilSet();
        return mReference.get().mResult;
    }

    private void lockUntilSet() {
        synchronized (this) {
            while (!mReference.get().isSet()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setResult(T result) {
        synchronized (this) {
            ResultWrapper<T> wrapper = mReference.get();
            wrapper.setResult(result);
            wrapper.setIsSet(true);
            notify();
        }
    }

    public static class ResultWrapper<T> {
        private boolean mIsSet;
        private T mResult;

        public boolean isSet() {
            return mIsSet;
        }

        public T getResult() {
            return mResult;
        }

        void setIsSet(boolean isCompleted) {
            this.mIsSet = isCompleted;
        }

        void setResult(T result) {
            this.mResult = result;
        }
    }



}
