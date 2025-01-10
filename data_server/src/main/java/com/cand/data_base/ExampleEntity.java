package com.cand.data_base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 示例实体类
@Entity
@Table(name = "example_table")
class ExampleEntity {
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private int age;
}
