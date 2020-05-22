package com.lab.labbook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(length = 20, nullable = false)
    private String level;

    @Column(length = 4096, nullable = false)
    private String log;

    @Column(length = 2048)
    private String comments;

    public Log(String level, String log, String comments) {
        this.level = level;
        this.log = log;
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Log)) return false;
        Log log1 = (Log) o;
        return Objects.equals(id, log1.id) &&
                Objects.equals(date, log1.date) &&
                Objects.equals(log, log1.log) &&
                Objects.equals(comments, log1.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, log, comments);
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", date=" + date +
                ", log='" + log + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
