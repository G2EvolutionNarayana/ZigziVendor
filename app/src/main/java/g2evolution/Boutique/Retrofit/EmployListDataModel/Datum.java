package g2evolution.Boutique.Retrofit.EmployListDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("resource_category_id")
    @Expose
    private Integer resourceCategoryId;
    @SerializedName("resource_education_id")
    @Expose
    private Integer resourceEducationId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("min_salary")
    @Expose
    private String minSalary;
    @SerializedName("max_salary")
    @Expose
    private String maxSalary;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("specialisation")
    @Expose
    private String specialisation;
    @SerializedName("total_exp")
    @Expose
    private String totalExp;
    @SerializedName("services")
    @Expose
    private Object services;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("resume")
    @Expose
    private String resume;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("resumes")
    @Expose
    private List<Resume> resumes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceCategoryId() {
        return resourceCategoryId;
    }

    public void setResourceCategoryId(Integer resourceCategoryId) {
        this.resourceCategoryId = resourceCategoryId;
    }

    public Integer getResourceEducationId() {
        return resourceEducationId;
    }

    public void setResourceEducationId(Integer resourceEducationId) {
        this.resourceEducationId = resourceEducationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(String totalExp) {
        this.totalExp = totalExp;
    }

    public Object getServices() {
        return services;
    }

    public void setServices(Object services) {
        this.services = services;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
}
