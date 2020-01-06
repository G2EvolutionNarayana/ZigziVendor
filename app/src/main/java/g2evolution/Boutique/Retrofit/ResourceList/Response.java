package g2evolution.Boutique.Retrofit.ResourceList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("message")
    @Expose
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}