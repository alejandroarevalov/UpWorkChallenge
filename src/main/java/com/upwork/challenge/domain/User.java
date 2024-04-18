package com.upwork.challenge.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "Users_Activities",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> checkedInActivities;

    public void setActivity(Activity activity) {
        if (checkedInActivities == null) {
            checkedInActivities = new ArrayList<>();
        } else if (!checkedInActivities.contains(activity)) {
            checkedInActivities.add(activity);
        }
    }

    @Override
    public String toString() {
        return "Id: " + id + ", username: " + userName + ", password: [PROTECTED]"
                + "checkedInActivities: " + checkedInActivities.size();
    }
}
