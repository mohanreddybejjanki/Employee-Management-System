package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "announcements")
@Data
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String postedBy;

    private LocalDate postedOn;

    private String priority; // LOW, MEDIUM, HIGH

    @PrePersist
    public void prePersist() {
        this.postedOn = LocalDate.now();
    }
}
