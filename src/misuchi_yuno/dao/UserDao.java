package misuchi_yuno.dao;

import static misuchi_yuno.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import misuchi_yuno.beans.Activity;
import misuchi_yuno.beans.User;
import misuchi_yuno.exception.NoRowsUpdatedRuntimeException;
import misuchi_yuno.exception.SQLRuntimeException;

public class UserDao {


	public int getCount(Connection connection, String loginId) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder ();
			sql.append("SELECT COUNT(*) ");
			sql.append("FROM users ");
			sql.append("WHERE login_id = ?;");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, loginId);

			ResultSet rs = ps.executeQuery();
			int count = toCount(rs);

			return count;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}
	private int toCount(ResultSet rs) throws SQLException {

		int countResult = 0;
		if (rs.next()) {
			countResult = rs.getInt(1);
		}
		return countResult;

	}

	public void insert(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", activity");
			sql.append(", created_date");
			sql.append(", updated_date");
			sql.append(") VALUES (");
			sql.append("?"); // login_id
			sql.append(", ?"); // passwaod
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", 0"); // activity
			sql.append(", CURRENT_TIMESTAMP"); // created_date
			sql.append(", CURRENT_TIMESTAMP"); // updated_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPositionId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getBranches(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM branches;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toBranches(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toBranches(ResultSet rs)
			throws SQLException {

				List<User> ret = new ArrayList<User>();
				try {
					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");

						User Branches = new User();
						Branches.setId(id);
						Branches.setName(name);

						ret.add(Branches);
					}
					return ret;
				} finally {
					close(rs);
				}
	}

	public List<User> getPositions(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM positions;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toPositions(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toPositions(ResultSet rs)
			throws SQLException {

				List<User> ret = new ArrayList<User>();
				try {
					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");

						User positions = new User();
						positions.setId(id);
						positions.setName(name);

						ret.add(positions);
					}
					return ret;
				} finally {
					close(rs);
				}
	}

	public List<User> getUsers(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("users.id");
			sql.append(", users.login_id");
			sql.append(", users.name");
			sql.append(", users.branch_id");
			sql.append(", branches.name AS branch_name");
			sql.append(", users.position_id");
			sql.append(", positions.name AS position_name");
			sql.append(", users.activity ");
			sql.append("FROM users ");
			sql.append("INNER JOIN branches ");
			sql.append("ON users.branch_id = branches.branch_id ");
			sql.append("INNER JOIN positions ");
			sql.append("ON users.position_id = positions.position_id;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUsersList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUsersList(ResultSet rs)
		throws SQLException {

			List<User> ret = new ArrayList<User>();
			try {
				while (rs.next()) {
					int id = rs.getInt("id");
					String loginId = rs.getString("login_id");
					String name = rs.getString("name");
					int branchId = rs.getInt("branch_id");
					String branchName = rs.getString("branch_name");
					int positionId = rs.getInt("position_id");
					String positionName = rs.getString("position_name");
					String activity = rs.getString("activity");

					User user = new User();
					user.setId(id);
					user.setLoginId(loginId);
					user.setName(name);
					user.setBranchId(branchId);
					user.setBranchName(branchName);
					user.setPositionId(positionId);
					user.setPositionName(positionName);
					user.setActivity(Integer.valueOf(activity));

					ret.add(user);
				}
				return ret;
			} finally {
				close(rs);
			}
		}

	public User getEditUser(Connection connection, String id) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("id");
			sql.append(", login_id");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", position_id ");
			sql.append("FROM users ");
			sql.append("WHERE id = ");
			sql.append("?");
			sql.append(";");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			User user2 = new User();
			user2 = toEditUserList(rs);

			return user2;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	private User toEditUserList(ResultSet rs)
		throws SQLException {

		User user = new User();
		if (rs.next()) {
			int id = rs.getInt("id");
			String loginId = rs.getString("login_id");
			String name = rs.getString("name");
			int branchId = rs.getInt("branch_id");
			int positionId = rs.getInt("position_id");

			user.setId(id);
			user.setLoginId(loginId);
			user.setName(name);
			user.setBranchId(branchId);
			user.setPositionId(positionId);
		}
		return user;
	}

	public void update(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET ");
			sql.append("login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", position_id = ?");
			sql.append(", updated_date = CURRENT_TIMESTAMP");
			if (!StringUtils.isEmpty(user.getPassword())) {
				sql.append(", password = ?");
			}
			sql.append(" WHERE id = ?;");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getPositionId());
			if (!StringUtils.isEmpty(user.getPassword())) {
				ps.setString(5, user.getPassword());
				ps.setInt(6, user.getId());
			} else {
				ps.setInt(5, user.getId());
			}

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void activityUpdate(Connection connection, Activity activity) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET activity = ?");
			sql.append(", updated_date = CURRENT_TIMESTAMP ");
			sql.append("WHERE id = ?;");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, Integer.toString(activity.getActivity()));
			ps.setString(2, activity.getLoginId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}