package com.tencent.sso.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

import com.tencent.sso.util.MD5Util;

public class MyAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected boolean authenticateUsernamePasswordInternal(
			UsernamePasswordCredentials credentials)
			throws AuthenticationException {

		boolean flag = false;
		final String username = credentials.getUsername();
		final String password = credentials.getPassword();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			String sql = "select * from account where account = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					/*byte[] pwd = rs.getBytes("password_hash");
					byte[] salt = rs.getBytes("password_salt");

					byte[] passwordHash = SHA256PasswordEncryptionService
							.createPasswordHash(password, salt);
					String passwordHashStr = new String(passwordHash);
					String sqlpwd = new String(pwd);

					if (sqlpwd.equals(passwordHashStr)) {
						flag = true;
					} else {  
						flag = false;
					}*/
					
					String pwd = rs.getString("password");
					
					if((MD5Util.encript(password)).equals(pwd)) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
}
