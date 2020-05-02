//회원 데이터베이스에 회원정보를 불러옴/입력함
package user;

import java.sql.*;

public class UserDAO {

	//db에 접근하는 객체 connection
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	//mysql에 접속하는 부분
	public UserDAO() //생성자 실행시마다 자동으로 db연결
	{
		try 
		{
			String driver = "com.mysql.cj.jdbc.Driver";
			String dbURL = "jdbc:mysql://localhost:3306/bbs?serverTimezone=UTC"; // timezone 필수
			String dbID = "root";
			String dbPassword = "wkzns123zzz.";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}
		catch(Exception e)
		{
			e.printStackTrace();	//오류 출력
		}
	}
	
	//로그인 함수
	public int login(String userId, String userPW) 
	{
		String SQL="select userPW from USER where userId = ?";
		try 
		{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
		
			//result set 에 결과보관
			rs = pstmt.executeQuery();
			
			//결과가 존재한다면 실행
			if (rs.next()) 
			{
				// 패스워드 일치한다면 실행
				if (rs.getString(1).equals(userPW)) 
				{
					return 1; // 로그인 성공
				} 
				else 
				{
					return 0; // 비밀번호 불일치	
				}
			}
			else 
			{
				return -1;	//아이디 없음
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();	//오류 출력
		}		
		finally 
		{
            // Connection, PreparedStatement를 닫는다.
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
		return -2;	//데이터베이스 오류
	}
	
	//회원가입 함수
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
				return 0;	//비밀번호 확인 불일치
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
            // Connection, PreparedStatement를 닫는다.
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
		return -1; // 데이터베이스 오류(아이디 중복)
	}
	
	
	//회원 정보 조회 함수
	//아이디를 이용해 회원 정보를 가져옴
	public User getUserInfo(String UserId) 
	{
		User userNow = null;
		String sql = "select * from USER where userId = ?";
		
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, UserId);
			System.out.println(UserId);
			//result set 에 결과보관
			rs = pstmt.executeQuery();
			//결과가 존재한다면 회원정보를 User에 담음
			if (rs.next()) 
			{
				userNow = new User();
				userNow.setUserId(rs.getString("userId"));
				userNow.setUserPW(rs.getString("userPW"));
				System.out.println(rs.getString("userPW"));
				userNow.setUserEmail(rs.getString("userEmail"));
				System.out.println(rs.getString("userEmail"));
				//교통약자 유형은 공백 기준으로 잘라 값을 전달함
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
            // Connection, PreparedStatement를 닫는다.
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
