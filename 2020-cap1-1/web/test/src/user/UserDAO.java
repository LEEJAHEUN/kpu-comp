//ȸ�� �����ͺ��̽��� ȸ�������� �ҷ���/�Է���
package user;

import java.sql.*;

public class UserDAO {

	//db�� �����ϴ� ��ü connection
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	//mysql�� �����ϴ� �κ�
	public UserDAO() //������ ����ø��� �ڵ����� db����
	{
		try 
		{
			String driver = "com.mysql.cj.jdbc.Driver";
			String dbURL = "jdbc:mysql://localhost:3306/bbs?serverTimezone=UTC"; // timezone �ʼ�
			String dbID = "root";
			String dbPassword = "wkzns123zzz.";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}
		catch(Exception e)
		{
			e.printStackTrace();	//���� ���
		}
	}
	
	//�α��� �Լ�
	public int login(String userId, String userPW) 
	{
		String SQL="select userPW from USER where userId = ?";
		try 
		{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
		
			//result set �� �������
			rs = pstmt.executeQuery();
			
			//����� �����Ѵٸ� ����
			if (rs.next()) 
			{
				// �н����� ��ġ�Ѵٸ� ����
				if (rs.getString(1).equals(userPW)) 
				{
					return 1; // �α��� ����
				} 
				else 
				{
					return 0; // ��й�ȣ ����ġ	
				}
			}
			else 
			{
				return -1;	//���̵� ����
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();	//���� ���
		}		
		finally 
		{
            // Connection, PreparedStatement�� �ݴ´�.
            try
            {
                if ( pstmt != null )
                { 
                	pstmt.close(); pstmt=null;
                }
                if ( conn != null )
                { 
                	conn.close(); conn=null;   
                }
            }
            catch(Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
		}	
		return -2;	//�����ͺ��̽� ����
	}
	
	//ȸ������ �Լ�
	public int join(User user) 
	{
		String sql = "INSERT INTO USER VALUES (?,?,?,?)";
		
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPW());
			pstmt.setString(3, user.getUserEmail());
			
			if(!(user.getUserPW()).equals(user.getRePW()))
			{
				return 0;	//��й�ȣ Ȯ�� ����ġ
			}
			
			String[] userType = user.getUserType();
			String ut = "";
			for(int i=0; i<userType.length; i++)
			{
				ut += userType[i]+" ";
			}
			pstmt.setString(4, ut);
			return pstmt.executeUpdate();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		finally 
		{
            // Connection, PreparedStatement�� �ݴ´�.
            try
            {
                if ( pstmt != null )
                { 
                	pstmt.close(); pstmt=null;
                }
                if ( conn != null )
                { 
                	conn.close(); conn=null;   
                }
            }
            catch(Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
		}	
		return -1; // �����ͺ��̽� ����(���̵� �ߺ�)
	}
	
	
	//ȸ�� ���� ��ȸ �Լ�
	//���̵� �̿��� ȸ�� ������ ������
	public User getUserInfo(String UserId) 
	{
		User userNow = null;
		String sql = "select * from USER where userId = ?";
		
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, UserId);
			System.out.println(UserId);
			//result set �� �������
			rs = pstmt.executeQuery();
			//����� �����Ѵٸ� ȸ�������� User�� ����
			if (rs.next()) 
			{
				userNow = new User();
				userNow.setUserId(rs.getString("userId"));
				userNow.setUserPW(rs.getString("userPW"));
				System.out.println(rs.getString("userPW"));
				userNow.setUserEmail(rs.getString("userEmail"));
				System.out.println(rs.getString("userEmail"));
				//������� ������ ���� �������� �߶� ���� ������
				String ut = rs.getString("userType");
				String[] userType;
				System.out.println(ut);
				userType = ut.split(" ");
				userNow.setUserType(userType);
				System.out.println(userType);
			}
			return userNow;
		}	
		catch(Exception sqle)
		{
			 throw new RuntimeException(sqle.getMessage());
		}
		finally 
		{
            // Connection, PreparedStatement�� �ݴ´�.
            try
            {
                if ( pstmt != null )
                { 
                	pstmt.close(); 
                	pstmt=null; 
                }
                if ( conn != null )
                { 
                	conn.close(); 
                	conn=null;    
                }
            }
            catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
	} 
}
