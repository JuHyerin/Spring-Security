package com.innilabs.inniboard.repository;

import java.util.HashMap;
import java.util.Map;

import com.innilabs.inniboard.dto.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private static Map<String, User> userMap = new HashMap<String, User>();
    public UserDao(){
        User u1 = new User();
        u1.setUserId("uid1");
        u1.setPassword("pwd1");
        u1.setName("user1");
        userMap.put(u1.getUserId(), u1);

        User u2 = new User();
        u2.setUserId("uid2");
        u2.setPassword("pwd2");
        u2.setName("user2");
        userMap.put(u2.getUserId(), u2);

        User u3 = new User();
        u3.setUserId("uid3");
        u3.setPassword("pwd3");
        u3.setName("user3");
        userMap.put(u3.getUserId(), u3);
    }

    public Map<String, User> getAllUsers(){
        return userMap;
    }

	public User getUserById(String id) {
		return userMap.get(id);
	}
}
