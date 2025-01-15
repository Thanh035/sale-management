package com.example.demo.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "groups")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdBy", "createdDate"}, allowGetters = true)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "group_id")
    private Integer groupId;
    @NotNull
    @Size(max = 50)
    @Column(name = "group_name", length = 50)
    @Getter
    private String groupName;
    @NotNull
    @Size(max = 50)
    @Column(name = "group_code", length = 50)
    @Getter
    private String groupCode;
    @Column(name = "note")
    private String note;
    @CreatedBy
    @Getter
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;
    @CreatedDate
    @Getter
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

}