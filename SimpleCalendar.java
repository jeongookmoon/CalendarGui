
public class SimpleCalendar {

	public static void main(String[] args) 
	{
		Model cal = new Model();
		View calendar = new View(cal);
		cal.attach(calendar);
	}

}
