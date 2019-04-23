package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	/**
	 *
	 * @param param   用户输入内容
	 * @param type   1.username 2.phone 3.email
	 *  sql :select count(*) from tb_user where  username = "admin123"
	 *
	 */

	@Override
	public Boolean findCheckUser(String param, Integer type) {
		String column = null;
		switch (type) {
			case 1:
				column = "username";
				break;
			case 2:
				column = "phone";
				break;
			case 3:
				column = "email";
				break;
		}
		//根据数据库结果分析如果总数为0 返回false  否则返回true
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		return count == 0 ? false : true;
	}
}
