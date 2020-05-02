//회원정보를 담는 자바빈
package user;

public class User
{
	//아이디, 비밀번호, 이메일, 약자 유형을 담을 프로퍼티
		//직접 접근할 수 없도록 private 사용
		private String userId;		//아이디
		private String userPW;	//비밀번호
		private String rePW; //비밀번호 확인
		private String userEmail;	//이메일
		private String userType[];	//약자 유형

		public String getUserId() 
		{
	 		return userId; 
		} 
		public void setUserId(String userId) 
		{ 
			this.userId = userId; 
		} 
		public String getUserPW() 
		{ 
			return userPW; 
		} 
		public void setUserPW(String userPW) 
		{ 
			this.userPW = userPW; 
		}
		public String getRePW() 
		{ 
			return rePW; 
		} 
		public void setRePW(String rePW) 
		{ 
			this.rePW = rePW; 
		}
		public String getUserEmail() 
		{ 
			return userEmail; 
		} 
		public void setUserEmail(String userEmail) 
		{ 
			this.userEmail = userEmail; 
		}
		public String[] getUserType() 
		{
			return userType;
		}
		public void setUserType(String[] userType) 
		{
			this.userType = userType;
		}
		/*
		public String[] getUserType() 
		{ 
			if(userType != null) 
			{
				String[] tempData = new String[userType.length];
				System.arraycopy(userType, 0, tempData, 0, userType.length);
				return tempData;
			}
			else 
			{
				return null;
			}
		} 
		public void setUserType(String[] userType) 
		{ 
			if(userType != null) 
			{
				this.userType = new String[userType.length];
				System.arraycopy(userType, 0, this.userType, 0, userType.length);
			}
			else 
			{
				this.userType=null;
			}
		}
		*/
}
