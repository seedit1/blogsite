package com.cos.blog.test;

import com.cos.blog.model.RollType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;


@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id를 찾을 수없습니다.";
        }
        return "삭제되었습니다. : " + id;
    }

    //save함수는 id를 전달하지 않ㅇ면 insert  를 해주고
    //save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
    @Transactional //save를 사용하지 않아도 업데이트가 된다. (더티 체킹)
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int  id, @RequestBody User requestUser){
        System.out.println("id : " + id);
        System.out.println("password : " +requestUser.getPassword());
        System.out.println("email : " +requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다."); //영속성
        });
        //더티 체킹 (변경된 데이터 감지해서  데이터베이스의 값을 수정해준다.)
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
       // userRepository.save(user);
        return  user;
    }

    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한페이지당 2건에 데이터를 리턴
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
       Page<User> pagingUser = userRepository.findAll(pageable);
       List<User> users = pagingUser.getContent();
        return users;
    }

    //{id} 주소로 파라미터를 전달 받을 수 있다.
    //http://localhost:9000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
       //람다식
        /*User user = userRepository.findById(id).orElseThrow(()->{
              return new IllegalArgumentException("해당 유저는 없습니다.");
          });*/
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다.  id  :" + id);
            }
        });
        /**요청 : 웹브라우저
         * user 객체 = 자바 오브젝트
         * 변환(웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
         * 스프링부트 = MessageConverter 가 응답시에 자동 작동
         *  만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
         * user 오브젝트를 json 으로 변환해서 브라우저에 던져준다.
         */
        return user;
    }

    //http://localhost:9000/blog/dummy/join (.요청)
    //http의 body에 username,  password, email 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    public String join(User user ){ //key=value
        System.out.println("id  :" + user.getId());
        System.out.println("username  :" + user.getUsername());
        System.out.println("password  :" + user.getPassword());
        System.out.println("email  :" + user.getEmail());
        System.out.println("role  :" + user.getRoll());
        System.out.println("createDate  :" + user.getCreateDate());

        user.setRoll(RollType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다. ";
    }
}
