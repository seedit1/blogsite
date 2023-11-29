package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//스프링이 컴포너트 스캔을 통해서 Bean 에 등록을 해줌(Ioc)
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public int 회원가입(User user){
        try{
            userRepository.save(user);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("UserService : 회원가입(): " + e.getMessage());
        }
            return  -1;
    }
}
