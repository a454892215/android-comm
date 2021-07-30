package com.database;

import org.litepal.crud.LitePalSupport;

/**
 * Author: Pan
 * 2021/7/30
 * Description:
 */
public class LitePalTestEntity extends LitePalSupport {
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
