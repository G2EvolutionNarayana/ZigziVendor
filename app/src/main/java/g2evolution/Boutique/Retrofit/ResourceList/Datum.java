package g2evolution.Boutique.Retrofit.ResourceList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("resource_packages_id")
    @Expose
    private Integer resourcePackagesId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cv_count")
    @Expose
    private String cvCount;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("subscribed")
    @Expose
    private String subscribed;
    @SerializedName("allocated_resumes")
    @Expose
    private Integer allocatedResumes;
    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;

    public Integer getResourcePackagesId() {
        return resourcePackagesId;
    }

    public void setResourcePackagesId(Integer resourcePackagesId) {
        this.resourcePackagesId = resourcePackagesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCvCount() {
        return cvCount;
    }

    public void setCvCount(String cvCount) {
        this.cvCount = cvCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
    }

    public Integer getAllocatedResumes() {
        return allocatedResumes;
    }

    public void setAllocatedResumes(Integer allocatedResumes) {
        this.allocatedResumes = allocatedResumes;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

}