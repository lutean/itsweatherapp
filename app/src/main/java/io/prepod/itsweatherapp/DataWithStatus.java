package io.prepod.itsweatherapp;

public class DataWithStatus<R> {

    public enum Status{
        SUCCESS,
        ERROR
    }

    private Status status;
    private String message;
    private R data;

    public static <R> DataWithStatus<R> success(R data){
        return new DataWithStatus<R>(Status.SUCCESS,  null, data);
    }

    public static <R> DataWithStatus<R> error(R data, String message){
        return new DataWithStatus<R>(Status.ERROR,  message, data);
    }

    public DataWithStatus(Status status, String message, R data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }
}
