package info.hfdb.hfdbapi.Controller;

public class Status {

    private StatusCode status;

    public Status(StatusCode status) {
        this.status = status;
    }

    public StatusCode getStatus() {
        return status;
    }

    public enum StatusCode {
        OK, Error
    }
}
