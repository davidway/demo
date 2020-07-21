package com.mymiaosha.demo.service;

import com.mymiaosha.demo.dao.UserDao;
import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("asd");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(2);
        u2.setName("asd");
        userDao.insert(u2);
        return true;
    }



}
