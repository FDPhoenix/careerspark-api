package com.khanghn.careerspark_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CandidateSkillId.class)
@Table(name = "candidate_skills")
public class CandidateSkill {
    @Id
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    public enum SkillLevel { BEGINNER, INTERMEDIATE, ADVANCED, EXPERT }
}

@Data
class CandidateSkillId implements Serializable {
    private UUID candidate;
    private UUID skill;
}

