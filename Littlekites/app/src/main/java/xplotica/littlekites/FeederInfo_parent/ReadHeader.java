package xplotica.littlekites.FeederInfo_parent;

import java.util.Date;

/**
 * Created by Santanu on 10-04-2017.
 */

public class ReadHeader {


    String headerName;
    String rowid;

    Date date;

    String Upload;

    String Description;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUpload() {
        return Upload;
    }

    public void setUpload(String upload) {
        Upload = upload;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
