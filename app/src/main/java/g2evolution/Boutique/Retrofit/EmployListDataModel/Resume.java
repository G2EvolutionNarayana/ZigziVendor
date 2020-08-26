package g2evolution.Boutique.Retrofit.EmployListDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resume {

    @SerializedName("resume_id")
    @Expose
    private Integer resumeId;
    @SerializedName("services")
    @Expose
    private String services;

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
