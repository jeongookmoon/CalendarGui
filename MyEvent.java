/**
 * <h1>MyEvent class</h1>
 * MyEvent holds event data such as title, date, starting time and ending time.
 * @author Jeong Ook Moon
 * @version 1.0
 * @since 2017-09-17
 */
public class MyEvent 
{
	private String title;
	private int date;
	private int s_time;
	private int e_time;
	/**
	 * MyEvent constructor set all private variables to be either 0 or null
	 */
	public MyEvent()
	{
		this.title = null;
		this.date = 0;
		this.s_time = 0;
		this.e_time = 0;
	}
	
	/**
	 * gets the title info of current MyEvent object
	 * @return title info
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * sets the title info of current MyEvent object to be the parameter value
	 * @param r_title title info
	 */
	public void setTitle(String r_title)
	{
		title = r_title;
	}
	/**
	 * gets the date info of current MyEvent object
	 * @return yyyyMMdd
	 */
	public int getDate()
	{
		return date;
	}
	/**
	 * sets the date info of current MyEvent object to be the parameter value
	 * @param r_title date info yyyyMMdd
	 */
	public void setDate(int r_date)
	{
		date = r_date;
	}
	/**
	 * gets the starting time info of current MyEvent object 
	 * @return starting time HH:mm
	 */
	public int getS_Time()
	{
		return s_time;
	}
	/**
	 * sets the current starting time to be the parameter value 
	 * @param r_s_time HH:mm
	 */
	public void setS_Time(int r_s_time)
	{
		s_time = r_s_time;
	}
	/**
	 * gets the current ending time of the current MyEvent object
	 * @return ending time HH:mm
	 */
	public int getE_Time()
	{
		return e_time;
	}
	/**
	 * sets the current ending time to be the parameter value 
	 * @param r_e_time HH:mm
	 */
	public void setE_Time(int r_e_time)
	{
		e_time = r_e_time;
	}	
}