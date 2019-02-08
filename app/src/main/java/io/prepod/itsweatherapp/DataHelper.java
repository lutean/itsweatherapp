package io.prepod.itsweatherapp;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import retrofit2.Response;

public abstract class DataHelper<T, R> {

    protected abstract Response<T> makeRequest() throws IOException;

    protected abstract void onFail(String message);

    protected abstract void storeDate(R data);

    protected abstract boolean isFreshData(R data);

    protected abstract R transformData(T data);

    protected abstract LiveData<R> loadFromStore();

    private MediatorLiveData<DataWithStatus<R>> result = new MediatorLiveData<>();
    private DispatchThread dispatchThread;

    public DataHelper(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
        dispatchThread.execute(() -> fetchData());
    }

    private void fetchData() {
        LiveData<R> restoredLiveData = loadFromStore();
        dispatchThread.executeInMainThread(() -> result.addSource(restoredLiveData, data -> {
            if (!isFreshData(data)) {
                result.removeSource(restoredLiveData);
                dispatchThread.execute(() -> {
                    try {
                        Response<T> response = makeRequest();
                        if (response.isSuccessful()) {
                            storeDate(transformData(response.body()));
                            dispatchThread.executeInMainThread(() ->
                                    result.addSource(restoredLiveData, newData -> result.setValue(DataWithStatus.success(newData))));
                        } else {
                            onError(restoredLiveData, response.message());
                        }
                    } catch (IOException e) {
                        onError(restoredLiveData, "");
                        e.printStackTrace();
                    }
                });
            }
        }));
    }

    private void onError(LiveData<R> data, String message) {
        dispatchThread.executeInMainThread(() -> result.addSource(data, newData -> result.setValue(DataWithStatus.error(newData, message))));
    }

    public LiveData<DataWithStatus<R>> asLiveData() {
        return result;
    }

}
