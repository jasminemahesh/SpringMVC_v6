package com.ing.training.dao;

import java.util.List;

import com.ing.training.domain.User;

public interface UserManagementDao {

	/* (non-Javadoc)
	 * @see com.ing.training.dao.UserManagementDao#createUser(com.ing.training.domain.User)
	 */
	public abstract User createUser(User user);

	public abstract User getUserById(int id);

	public abstract List<User> listUsers();

}