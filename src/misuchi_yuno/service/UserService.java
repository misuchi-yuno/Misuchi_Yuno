package misuchi_yuno.service;

import static misuchi_yuno.utils.CloseableUtil.*;
import static misuchi_yuno.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import misuchi_yuno.beans.Activity;
import misuchi_yuno.beans.User;
import misuchi_yuno.dao.UserDao;
import misuchi_yuno.utils.CipherUtil;


public class UserService {

	public int count(String loginId) {

		Connection connection = null;
		try {
			connection = getConnection();

			int count = new UserDao().getCount(connection, loginId);

			commit(connection);
			return count;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


	public void register(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			new UserDao().insert(connection, user);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<User> getUsers() {
		Connection connection = null;
		try {
			connection = getConnection();

			List<User> ret = new UserDao().getUsers(connection);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}

	}

	public User getEditUser(String id) {
		Connection connection = null;
		try {
			connection = getConnection();

			User user = new UserDao().getEditUser(connection, id);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}

	}

	public void editRegister(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			if (!user.getPassword().isEmpty()) {
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
			}

			new UserDao().update(connection, user);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void activityRegister(Activity activity) {

		Connection connection = null;
		try {
			connection = getConnection();

			new UserDao().activityUpdate(connection, activity);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
