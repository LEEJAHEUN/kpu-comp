//ȸ�������� ��� �ڹٺ�
package user;

public class User
{
	//���̵�, ��й�ȣ, �̸���, ���� ������ ���� ������Ƽ
		//���� ������ �� ������ private ���
		private String userId;		//���̵�
		private String userPW;	//��й�ȣ
		private String rePW; //��й�ȣ Ȯ��
		private String userEmail;	//�̸���
		private String userType[];	//���� ����

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
