package converters;

public class ResponseOrders {
    private String code;
    private String id;
    private String message;

    public ResponseOrders(String code, String id, String message) {
        this.code = code;
        this.id = id;
        this.message = message;
    }

    public ResponseOrders() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
