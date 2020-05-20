package misuchi_yuno.service;

import static misuchi_yuno.utils.CloseableUtil.*;
import static misuchi_yuno.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import misuchi_yuno.beans.Activity;
import misuchi_yuno.beans.User;
import misuchi_yuno.beans.UserInformation;
import misuchi_yuno.dao.UserDao;
import misuchi_yuno.utils.CipherUtil;


public class UserService {

	public void count(User user) {

		Connection connection = null;
		try {
			connection = getConnection();


			UserDao userDao = new UserDao();
			userDao.getCount(connection, user);

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

	public int getCount(User user) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao CountDao = new UserDao();
			int count = CountDao.getCount(connection, user);

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

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

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

	public List<UserInformation> getInformation() {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao InformationDao = new UserDao();
			List<UserInformation> ret = InformationDao.getUserInformation(connection);

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

	public void editRegister(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.update(connection, user);

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

			UserDao activityDao = new UserDao();
			activityDao.activityUpdate(connection, activity);

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
