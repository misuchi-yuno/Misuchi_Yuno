package misuchi_yuno.dao;

import static misuchi_yuno.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import misuchi_yuno.beans.Activity;
import misuchi_yuno.beans.User;
import misuchi_yuno.beans.UserInformation;
import misuchi_yuno.exception.NoRowsUpdatedRuntimeException;
import  misuchi_yuno.exception.SQLRuntimeException;

public class UserDao {

	public int getCount(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder ();
			sql.append("select count(*) from users where login_id = ?;");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, user.getLogin_id());

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
			sql.append(", 1"); // activity
			sql.append(", CURRENT_TIMESTAMP"); // created_date
			sql.append(", CURRENT_TIMESTAMP"); // updated_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranch_id());
			ps.setString(5, user.getPosition_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UserInformation> getUserInformation(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("users.id, ");
			sql.append("users.login_id, ");
			sql.append("users.name, ");
			sql.append("users.branch_id, ");
			sql.append("branches.name as branch_name, ");
			sql.append("users.position_id, ");
			sql.append("positions.name as position_name, ");
			sql.append("users.activity ");
			sql.append("from users ");
			sql.append("inner join branches ");
			sql.append("on users.branch_id = branches.branch_id ");
			sql.append("inner join positions ");
			sql.append("on users.position_id = positions.position_id;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserInformation> ret = toUserInformationList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	private List<UserInformation> toUserInformationList(ResultSet rs)
		throws SQLException {

			List<UserInformation> ret = new ArrayList<UserInformation>();
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

					if (activity.equals("0")) {
						activity = "活動中";
					} else {
						activity = "停止中";
					}

					UserInformation information = new UserInformation();
					information.setId(id);
					information.setLoginId(loginId);
					information.setName(name);
					information.setBranchId(branchId);
					information.setBranchName(branchName);
					information.setPositionId(positionId);
					information.setPositionName(positionName);
					information.setActivity(activity);

					ret.add(information);

				}
				return ret;
			} finally {
				close(rs);
			}
		}

	public void update(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update users set ");

			if(user.getPassword().equals("")) {
				sql.append("login_id = ? ");
				sql.append(", name = ? ");
				sql.append(", branch_id = ? ");
				sql.append(", position_id = ? ");
				sql.append("where id = ? ");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLogin_id());
				ps.setString(2, user.getName());
				ps.setString(3, user.getBranch_id());
				ps.setString(4, user.getPosition_id());
				ps.setString(5, user.getId());
			} else {
				sql.append("login_id = ");
				sql.append("?, password = ");
				sql.append("?, name = ");
				sql.append("?, branch_id = ");
				sql.append("?, position_id = ");
				sql.append("? where id = ");
				sql.append("?;");


				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLogin_id());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getName());
				ps.setString(4, user.getBranch_id());
				ps.setString(5, user.getPosition_id());
				ps.setString(6, user.getId());
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
			sql.append("update users set activity = ");
			sql.append("? where id = ");
			sql.append("? ");
			sql.append(";");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, activity.getActivity());
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