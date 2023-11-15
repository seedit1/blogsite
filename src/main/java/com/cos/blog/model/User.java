package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Data //@Getter + @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert //insert시 null 인 필드를 지원
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //auto_increment

    @Column(nullable = false, length = 30, unique = true)
    private String username; //아이디

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)  //123456 => 해쉬 (비밀번호 암호화)
    private String password;

    //@ColumnDefault("user")
    //DB는 Roll Type이 없다.
    @Enumerated(EnumType.STRING)
    private RollType roll; //Enum을 쓰는게 좋다. //ADMIN, USER

    @CreationTimestamp
    private Timestamp createDate;
}
