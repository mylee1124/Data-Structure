import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;


interface Input_Menu{
	int Input=1, Search=2, Delete=3, Exit=4;
}

interface Input_Select{
	int Normal=1, Univ=2, Company=3;
}

class PhoneInfo implements Serializable
{
	String name;
	String phoneNumber;
	//String birthday;
	
	public PhoneInfo(String name, String phoneNumber)
	{
		this.name=name;
		this.phoneNumber = phoneNumber;
		//this.birthday = birthday;
	}
	
	public void ShowPhoneInfo(){
		System.out.println("Name : "+name);
		System.out.println("Phone : "+phoneNumber);
	}
	
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public boolean equals(Object obj)
	{
		PhoneInfo pInfo = (PhoneInfo)obj;
		if(name.compareTo(pInfo.name)==0)
			return true;
		else
			return false;
	}
}

class PhoneUnivInfo extends PhoneInfo 
{
	String major;
	int year;
	
	public PhoneUnivInfo(String name, String phoneNumber, String major, int year)
	{
		super(name, phoneNumber);
		this.major=major;
		this.year=year;
	}
	
	public void ShowPhoneInfo()
	{
		super.ShowPhoneInfo();
		System.out.println("Major : "+major);
		System.out.println("Year : "+year);
	}
	
}

class PhoneCompanyInfo extends PhoneInfo 
{
	String company;
	
	public PhoneCompanyInfo(String name, String phoneNumber, String company)
	{
		super(name, phoneNumber);
		this.company=company;
	}

	public void ShowPhoneInfo()
	{
		super.ShowPhoneInfo();
		System.out.println("Compamy : "+company);
	}
	
}
class Manager
{
	/*final int MAX_CNT=100;
	PhoneInfo[] InfoStorage = new PhoneInfo[MAX_CNT];
	int CurCnt=0;
	*/
	private final File datafile = new File("PhoneBook.dat");
	HashSet<PhoneInfo> InfoStorage = new HashSet<PhoneInfo>();
	
	static Manager instance = null;
	public static Manager createManagerInst() // Manager instance have to be only one constructed.
	{
		if(instance==null)
			instance=new Manager();
		return instance;
	}
	
	private Manager()
	{
		readFromFile();
	}
	
	private PhoneInfo readBasicInfo()
	{
		System.out.print("이름 : ");
		String _name = Menu.keyboard.nextLine();
	
		System.out.print("전화번호 : ");
		String _phone = Menu.keyboard.nextLine();
		
		return new PhoneInfo(_name, _phone);
	}
	
	private PhoneInfo readUnivInfo()
	{
		System.out.print("이름 : ");
		String _name = Menu.keyboard.nextLine();
	
		System.out.print("전화번호 : ");
		String _phone = Menu.keyboard.nextLine();
		
		System.out.print("전공 : ");
		String _major = Menu.keyboard.nextLine();
		
		System.out.print("학년 : ");
		int _year = Menu.keyboard.nextInt();
		Menu.keyboard.nextLine();
		return new PhoneUnivInfo(_name, _phone, _major, _year);
	}
	
	public PhoneInfo readCompanyInfo()
	{
		System.out.print("이름 : ");
		String _name = Menu.keyboard.nextLine();
	
		System.out.print("전화번호 : ");
		String _phone = Menu.keyboard.nextLine();
		
		System.out.print("회사 : ");
		String _company = Menu.keyboard.nextLine();
		
		return new PhoneCompanyInfo(_name, _phone, _company);
	}
	
	public void Data_Insert() throws ChoiceException
	{
		try{
			System.out.println("데이터 입력을 시작합니다..");
		
			System.out.println("1.일반, 2.대학, 3.회사");
			System.out.print("선택>> ");
			int choice=Menu.keyboard.nextInt();
			Menu.keyboard.nextLine();
			PhoneInfo info = null;
		
			if(choice<Input_Select.Normal || choice>Input_Select.Company)
			{
				throw new ChoiceException(choice);
			}
			switch(choice)
			{
				case Input_Select.Normal:
					info=readBasicInfo();
					break;
				case Input_Select.Univ:
					info=readUnivInfo();
					break;
				case Input_Select.Company:
					info=readCompanyInfo();
					break;
			}

			//InfoStorage[CurCnt++]=info;
			boolean isAdded = InfoStorage.add(info);
			if(isAdded==true)
			{
				System.out.println("데이터 입력이 완료되었습니다.\n");
			}
			else
			{
				System.out.println("이미 저장된 데이터입니다.\n");
			}
			
		}catch(ChoiceException e)
		{
			e.ChoiceExcptShow();
			System.out.println("메뉴 선택을 처음부터 다시 진행합니다.\n");
		}
		
	}

	
	
	public void Data_Search()
	{
		System.out.println("데이터 검색을 시작합니다..");
		System.out.print("이름  : ");
		String _name = Menu.keyboard.nextLine();
		
		//int sIdx = search(_name);
		PhoneInfo sIdx = search(_name);
		
		/*if(sIdx == -1)
		{
			System.out.println("해당 데이터가 없습니다.\n");
		}
		else
		{
			InfoStorage[sIdx].ShowPhoneInfo();
			System.out.println("데이터 검색이 완료되었습니다.\n");
		}*/
		
		//Iterator<PhoneInfo> curInfo = InfoStorage.iterator();
		
		if(sIdx==null)
		{
			System.out.println("해당 데이터가 없습니다.\n");
		}
		else
		{
			sIdx.ShowPhoneInfo();
			System.out.println("데이터 검색이 완료되었습니다.\n");
		}
	}
	
	public void Data_Delete()
	{
		System.out.println("데이터 삭제를 시작합니다..");
		System.out.print("이름 : ");
		String _name = Menu.keyboard.nextLine();
		
		/*int dIdx = search(_name);
		if(dIdx==-1)
		{
			System.out.println("해당 데이터가 없습니다.\n");
		}
		else
		{
			InfoStorage[dIdx]=InfoStorage[dIdx+1];
			CurCnt--;
			
			System.out.println("데이터 삭제가 완료되었습니다.\n");
		}*/
		
		Iterator<PhoneInfo> dItr = InfoStorage.iterator();
		
		while(dItr.hasNext())
		{
			PhoneInfo curInfo = dItr.next();
			if(_name.compareTo(curInfo.name)==0){
				dItr.remove();
				System.out.println("데이터 삭제가 완료되었습니다.\n");
				return;
			}		
		}
		
		System.out.println("해당 데이터가 없습니다.\n");
			
		
		
		
	}
	
	private PhoneInfo search(String name)
	{
		/*for(int idx=0; idx<CurCnt; idx++)
		{
			//PhoneInfo curInfo = InfoStorage[idx];
			if((InfoStorage[idx].name).compareTo(name)==0)
				return idx;
		}
		
		return -1;*/
		
		Iterator<PhoneInfo> search = InfoStorage.iterator();
		
		while(search.hasNext())
		{
			PhoneInfo curInfo = search.next();
			if(name.compareTo(curInfo.name)==0)
				return curInfo;
		}
		
		return null;
	}
	
	public void storeToFile()
	{
		try{
			FileOutputStream DataStore = new FileOutputStream(datafile);
			ObjectOutputStream out = new ObjectOutputStream(DataStore);
		
			Iterator<PhoneInfo> itr = InfoStorage.iterator();
			while(itr.hasNext())
				out.writeObject(itr.next());
		
			out.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void readFromFile()
	{
		if(datafile.exists()==false)
			return;
		
		try{			
			FileInputStream DataRead = new FileInputStream(datafile);
			ObjectInputStream in = new ObjectInputStream(DataRead);
			
			while(true)
			{
				PhoneInfo info = (PhoneInfo)in.readObject();
				if(info==null)
					break;
				InfoStorage.add(info);
			}	
			
			in.close();
		}
		catch(IOException e)
		{
			return;
		}
		catch(ClassNotFoundException e)
		{
			return;
		}
		
	}
}


class ChoiceException extends Exception
{
	int inputchoice;
	
	public ChoiceException(int choice)
	{
		super("잘못된 선택이 이루어졌습니다.");
		inputchoice = choice;
	}
	public void ChoiceExcptShow()
	{
		System.out.println(inputchoice+"에 해당하는 선택은 존재하지 않습니다.");
	}
}


class Menu{
	
	public static Scanner keyboard = new Scanner(System.in);
	
	public static void ShowMenu()
	{
		System.out.println("선택하세요..");
		System.out.println("1.데이터 입력");
		System.out.println("2.데이터 검색");
		System.out.println("3.데이터 삭제");
		System.out.println("4.프로그램 종료");
		System.out.print("선택 : ");
	}
}

/*class MenuInputException extends Exception
{
	int menuchoice;
	
	public MenuInputException(int choice)
	{
		super("잘못된 선택이 이루어졌습니다.");
		menuchoice=choice;
	}
	
	public void MenuExceptShow()
	{
		System.out.println(menuchoice+"에 해당하는 선택은 존재하지 않습니다.");
	}
}*/

public class PhoneBook {
	
	public static void main(String[] args)
	{
		boolean on_off = true;
		int choice=0;	    
		Manager manager = Manager.createManagerInst();
				
		while(on_off)
		{
			try
			{
				Menu.ShowMenu();
		     	choice = Menu.keyboard.nextInt();
		    	Menu.keyboard.nextLine();
		    	
		    	if(choice<Input_Menu.Input || choice>Input_Menu.Exit)
		    	{
		    		throw new ChoiceException(choice);
		    	}
		    	
		    	switch(choice){
		    		case Input_Menu.Input: 
		    			manager.Data_Insert();
		    			break;
		    		case Input_Menu.Search:
		    			manager.Data_Search();
		    			break;
		    		case Input_Menu.Delete:
		    			manager.Data_Delete();
		    			break;
		    		case Input_Menu.Exit:
		    			manager.storeToFile();
		    			System.out.println("프로그램이 종료되었습니다.");
		    			System.out.println("");
		    			on_off=false;
		    			break;
		    		default:
		    			System.out.println("다시 입력해주세요! (1~4 중에 선택)");
		    			System.out.println("");
		    			break;
		    	}
					
			}catch(ChoiceException e)
			{
				e.ChoiceExcptShow();
				System.out.println("메뉴 선택을 처음부터 다시 진행합니다.\n");
			}
			
		}
		
		
	}

}
