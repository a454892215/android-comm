package com.common.x5_web.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class HistoryRecordEntity extends LitePalSupport {

    private String title;
    private String url;
    @Column(unique = true)
    private long time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HistoryRecordEntity{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time=" + time +
                '}';
    }

}
