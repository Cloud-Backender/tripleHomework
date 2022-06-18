package com.triple.club.review.model.entity;

import com.triple.club.common.entitiy.CreateDtEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "POINT_LOG")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointLogEntity extends CreateDtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "TOTAL_POINT")
    private Long totalPoint;

    @Column(name = "REVIEW_ID")
    private String reviewId;

    public void updateTotalPoint(Long totalPoint) {
        this.totalPoint = totalPoint;
    }
}
