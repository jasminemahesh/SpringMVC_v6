package com.ing.training.dao;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ing.training.domain.User;

@Repository("userManagementDao")
public class UserManagementDaoImpl implements UserManagementDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.ing.training.dao.UserManagementDao#createUser(com.ing.training.domain.User)
	 */
	
	@Override
	public User createUser(User user)
	{
		String insertUserQuery="INSERT INTO USERS VALUES(?,?,?,?,?)";
		int userId=getIdForUser();
		jdbcTemplate.update(insertUserQuery, new Object[]{userId, user.getFirstname(), user.getLastname(), user.getCity(), user.getEmail()});
		return populateUserWithId(user, userId);
		
	}
	
	private User populateUserWithId(User user, int userId)
	{
		User createdUser=new User();
		BeanUtils.copyProperties(user, createdUser);
		createdUser.setId(userId);
		return createdUser;
	}
	
	private int getIdForUser()
	{
		String highestUserIdQuery="SELECT MAX(ID) FROM USERS";
		Integer id=jdbcTemplate.queryForObject(highestUserIdQuery, Integer.class);
		return id+1;
	}
	
	/* (non-Javadoc)
	 * @see com.ing.training.dao.UserManagementDao#getUserById(java.lang.String)
	 */
	@Override
	public User getUserById(int id)
	{
		String getUserByIdQuery="SELECT * FROM USERS WHERE ID=?";
		
		return jdbcTemplate.queryForObject(getUserByIdQuery, new Object[]{id}, (rs, rowNum)->{
			
			User user=new User();
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("firstname"));
			user.setLastname(rs.getString("lastname"));
			user.setCity(rs.getString("city"));
			user.setEmail(rs.getString("email"));
			return user;
			
		});
		
	}
	
	/* (non-Javadoc)
	 * @see com.ing.training.dao.UserManagementDao#listUsers()
	 */
	@Override
	public List<User> listUsers()
	{
		String getUserByIdQuery="SELECT * FROM USERS";
		return jdbcTemplate.query(getUserByIdQuery, (rs, rowNum)->{
			
			User user=new User();
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("firstname"));
			user.setLastname(rs.getString("lastname"));
			user.setCity(rs.getString("city"));
			user.setEmail(rs.getString("email"));
			return user;
			
		});
	}


}
