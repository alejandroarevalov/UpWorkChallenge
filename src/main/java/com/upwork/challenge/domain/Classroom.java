package com.upwork.challenge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="Classrooms")
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "qr_code")
    private String qrCode;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom")
    private List<Activity> activities;

    @Override
    public String toString() {
        return "Id: " + id + ", qrCode: " + qrCode + ", activities: " + activities.size();
    }
}
