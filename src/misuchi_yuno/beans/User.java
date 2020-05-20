package misuchi_yuno.beans;

import java.sql.Timestamp;

public class User {

	private String id;
	private String loginId;
	private String password;
	private String name;
	private String branchId;
	private String positionId;
	private String activity;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin_id() {
		return loginId;
	}
	public void setLogin_id(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranch_id() {
		return branchId;
	}
	public void setBranch_id(String branchId) {
		this.branchId = branchId;
	}
	public String getPosition_id() {
		return positionId;
	}
	public void setPosition_id(String positionId) {
		this.positionId = positionId;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
