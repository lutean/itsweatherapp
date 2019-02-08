package io.prepod.itsweatherapp;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import retrofit2.Response;

public abstract class DataHelper<T, R> {

    protected abstract Response<T> makeRequest() throws IOException;

    protected abstract void onFail(String message);

    protected abstract void storeData(R data);

    protected abstract boolean isFreshData(R data);

    protected abstract R transformData(T data);

    protected abstract LiveData<R> loadFromStore();

    private MediatorLiveData<DataWithStatus<R>> result = new MediatorLiveData<>();
    private DispatchThread dispatchThread;

    public DataHelper(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
        LiveData<R> restoredLiveData = loadFromStore();
        result.addSource(restoredLiveData, data -> {
            result.removeSource(restoredLiveData);
            if (!isFreshData(data)){
                fetchData(restoredLiveData);
            }
        });
    }

    private void fetchData(LiveData<R> restoredData) {
        dispatchThread.execute(() -> {
            try {
                Response<T> response = makeRequest();
                if (response.isSuccessful()) {
                    storeData(transformData(response.body()));
                    dispatchThread.executeInMainThread(() -> result.addSource(loadFromStore(), r -> {
                        if (r != null)
                            result.setValue(new DataWithStatus<>(DataWithStatus.Status.SUCCESS, "", r));
                    }));
                } else {
                    result.addSource(restoredData, r -> result.setValue(new DataWithStatus<>(DataWithStatus.Status.ERROR, "", r)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public LiveData<DataWithStatus<R>> asLiveData() {
        return result;
    }
}
