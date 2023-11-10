package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//사용자가 요청 ->  응답(HTML 파일)
//@Controller
//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
    private static final String TAG = "HttpControllerTest :" ;
    @GetMapping("/http/lombok")
    public String lombokTest(){
        //Member m = new Member(1, "park", "1234", "park@naver.com");
        //buider패턴 사용 (순서 상관없이 넣을 수 있다.)
        Member m = Member.builder().username("park").password("1234").email("park@naver.com").build();
        //id를 넣지 않았기 때문에 id는 0이 출력된다.
        System.out.println(TAG + "getter :" + m.getUsername());
        m.setUsername("seed");
        System.out.println(TAG + "setter :" + m.getUsername());
        return "lombok test 완료";
    }


    //인터넷 브라우저 요청은 무조건   get 요청밖에 할 수 없다.
    //http://localhost:8080/http/get(select)
    @GetMapping("/http/get")
    public String getTest(Member m){ //id=1&username=seed&password=1234&email=seed@naver.com  스프링이 Member 자동으로 넣어준다.


        return "get 요청 :" + m.getId() + " , " + m.getUsername() +  ", " + m.getPassword() + " , " + m.getEmail();
    }
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){ //MessageConverter 가 json data로 변환해준다.
        return "post요청 :" + m.getId() + " , " + m.getUsername() +  ", " + m.getPassword() + " , " + m.getEmail();
    }
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청 :" + m.getId() + " , " + m.getUsername() +  ", " + m.getPassword() + " , " + m.getEmail();
    }
    @DeleteMapping("/http/delete")
    public String deleteTest(@RequestBody Member m){
        return "delete 요청"+ m.getId() + " , " + m.getUsername() +  ", " + m.getPassword() + " , " + m.getEmail();
    }
}
