package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content;

    @ColumnDefault("0")
    private  int count; //조회수

    //private int userId;
    @ManyToOne(fetch = FetchType.EAGER) //Board = Many, User = One
    @JoinColumn(name="userId")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy가 있으면 연관관계의 주인이 아니다.
    private List<Reply> reply; //하나의 게시글은 여러개의 답변을 가질수 있다.

    @CreationTimestamp
    private Timestamp createDate;
}
