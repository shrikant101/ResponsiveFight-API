package utils;

import pojo.User;

public class TestDataBuild {

public User createPayLoad(String name, int value) {
	User user = new User();
	user.setUsername(name);
	user.setScore(value);
	return user;
	
}
}
