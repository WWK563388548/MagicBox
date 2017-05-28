package com.example.wwk.functiondemo.entity;

/**
 * Created by wwk on 17/5/28.
 */

public class LogisticsData {

    // Time
    private String datetime;
    // state of logistics
    private String remark;
    // location of logistics
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

   // Print information (This method is may or may not be needed)
    @Override
    public String toString() {
        return "LogisticsData{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }

}
