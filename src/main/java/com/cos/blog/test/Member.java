package com.cos.blog.test;

import lombok.*;


//@RequiredArgsConstructor //final이 븥어 있는 변수들에 대해서 생성자를 만들어준다.
//@AllArgsConstructor //전체 생성자
@NoArgsConstructor //빈 생성자
@Data  // @Getter + @Setter
public class Member {
    //final은 데이터베이스로 부터 가져온 데이터를 변경되지 않게 하기위해서 붙여준다.
    private int id;
    private String username;
    private String password;
    private  String email;

   @Builder
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
   }

}
