package com.agustosoftware.storechecker.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "checklist")
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="checklist_id")
    private long checklistId;

    @Column(name="dateTime")
    private LocalDateTime dateTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "checklist_item", joinColumns = @JoinColumn(name = "checklist_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items;
}
