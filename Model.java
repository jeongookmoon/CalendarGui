import java.util.*;
import java.util.Map.Entry;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.text.DecimalFormat;
import java.io.*;

//possible months

/**
 * <h1>MyCalendar class</h1>
 * MyCalender handles datastructure, hashmap which include integer date as key and  the arraylist of MyEvent objects as values.
 * It can create, edit, delete each MyEvent in the arraylist of hashmap.
 * It can also display hashmap in multiple ways such as dayview , monthview.
 * Also, MyCalendar can use GregorianCalendar cal to make sure that all the dates used are correct.
 * It also traverses different types of data structures such as hashmap and arraylist.
 * Finally, it can import and export scheduled events into and from text file.
 * 
 * @author Jeong Ook Moon
 * @version 1.0
 * @since 2017-09-17
 */
public class Model 
{
	// instantiating cal and variable to store today's date
	private GregorianCalendar cal;
	
	// arrays of days, months and calendar data
	public String arrayOfDays[]  = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public String arrayOfMonths[]  = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	public String calData[][] = new String [6][7];
	
	//initializing hashmap to hold data of event and corresponding date
	private HashMap<Integer, ArrayList<MyEvent>> map;	
	
	//initializing arraylist to hold all events
	private ArrayList<MyEvent> eventList;
	
	private ArrayList<ChangeListener> listeners;
	
	/**
	 * The constructor is to set values and initiate data structures for other methods
	 */
	public Model()
	{
		// capturing today
		this.cal = new GregorianCalendar();
		this.map = new HashMap<Integer, ArrayList<MyEvent>>();
		this.eventList = new ArrayList<MyEvent>();
		listeners = new ArrayList();
		
	}
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	public String getCurrentMonth(int monthVal)
	{
		return arrayOfMonths[monthVal];
	}
	public int getDayOfWeek()
	{
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	public int getLastDayOfMonth()
	{
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * returns the year of current Calendar
	 * @return year of current Calendar
	 */
	public int getYear()
	{
		return cal.get(Calendar.YEAR);
	}
	/**
	 * sets the year of current Calendar to be the parameter value
	 * @param year value
	 */
	public void setYear(int year)
	{
		cal.set(Calendar.YEAR, year);
	}
	/**
	 * returns month value of current Calendar 
	 * @return month value of current Calendar
	 */
	public int getMonth()
	{
		return cal.get(Calendar.MONTH);
	}
	/**
	 * sets month value of current Calendar to be parameter value
	 * @param month value
	 */
	public void setMonth(int month)
	{
		cal.set(Calendar.MONTH, month);
	}
	/**
	 * returns day value of current Calendar
	 * @return day value of current Calendar
	 */
	public int getDate()
	{
		return cal.get(Calendar.DATE);
	}
	/**
	 * sets day value of current Calendar to be parameter value
	 * @param day
	 */
	public void setDate(int day)
	{
		cal.set(Calendar.DATE, day);
	}
	/**
	 * returns arraylist of MyEvent objects
	 * @return the whole arraylist
	 */
	public ArrayList<MyEvent> getArray()
	{
		return eventList;
	}
	/**
	 * sets the private arraylist to be the parameter arraylist
	 * @param r_arraylist arraylist of MyEvent objects
	 */
	public void setArrayList(ArrayList<MyEvent> r_arraylist)
	{
		eventList = r_arraylist;
	}
	/**
	 *setting Calendar data into the calData array for MonthView  
	 */
	public void setMonthView()
	{
		//initializing variables to set Calendar data into the array
		int maxColumn=arrayOfDays.length;
		int assignDate = 1;
		// saving current date
		int temp = cal.get(Calendar.DATE);
		// set the date to be 1 to figure out the day (ex, monday) of the first day of the month
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// setting actual firstday and lastday of the month
		int firstDay = cal.get(Calendar.DAY_OF_WEEK)-1;
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1;
		// let the date back to be current date 
		cal.set(Calendar.DATE, temp);
		
		//initializing the variable row
		int row = 0;
		
		//inputting data
		//<= because lastDay has to be included
		for(int i=0; i<=firstDay+lastDay; i++)
		{
			//filling empty before the firstDay index
			if(i<firstDay)
			{
				calData[row][i] = "";
			}
			
			//assigning dates since the firstDay
			else
			{
				calData[row][i%maxColumn] = Integer.toString(assignDate);
				assignDate++;
				// going to next row after filling all the column
				if(i%maxColumn == 6)
				{
					row++;
				}
			}
		}
	}
	/**
	 * shows monthview using calData array also retrieving days that events are scheduled as arraylist
	 * @param r_daysofmonth arraylist containing integer values of days that have scheduled events on this month
	 */
	public void showMonthView(ArrayList<Integer> r_daysofmonth)
	{
		//setting month data into calData array;
		setMonthView();
		
		//displaying year and month
		System.out.print("\t"+"\t"+cal.get(GregorianCalendar.YEAR)+"\t"+arrayOfMonths[cal.get(Calendar.MONTH)]);
		System.out.println();		
		int maxColumn=arrayOfDays.length;
		//displaying possible days (like monday), only first 2 letters
		for(int i=0; i<maxColumn; i++)
		{
			String str = arrayOfDays[i];
			if(str.length()>2)
			{
				System.out.print(str.substring(0, 2)+"\t");
			}
		}
		System.out.println();
		
		//initializing the variable row and setting up 
		int row = 0;
		// saving current date
		int temp = cal.get(Calendar.DATE);
		// set the date to be 1 to figure out the day (ex, monday) of the first day of the month
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// setting actual firstday and lastday of the month
		int firstDay = cal.get(Calendar.DAY_OF_WEEK)-1;
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1;
		// let the date back to be current date 
		cal.set(Calendar.DATE, temp);
		//displaying what's in calData
		//<= because lastDay has to be included
		int actualday = 1;
		for(int i=0; i<=firstDay+lastDay;i++)
		{	
			//before actual firstday displaying empty spots with tabs
			if(i<firstDay)
			{
				System.out.print(calData[row][i%maxColumn]+"\t");	
			}
			//from the actual firstday, showing days(numbers) & scheduled days & current date, actualday starts to increase
			else
			{
				boolean found = false;
				//assigning { } around scheduled days of the month
				for(int j=0; j<r_daysofmonth.size(); j++)
				{
					int day = r_daysofmonth.get(j);
					if(day == actualday)
					{
						System.out.print("{"+calData[row][i%maxColumn]+"}"+"\t");
						found = true;
					}
				}
				// assigning { } around current day
				if(found == false)
				{
					if(temp == actualday)
					{
						System.out.print("{"+calData[row][i%maxColumn]+"}"+"\t");
					}
					else
					{
						System.out.print(calData[row][i%maxColumn]+"\t");	
					}	
				}
				actualday++;
			}
			
			//going to next row after reaching the last column
			if(i%maxColumn == 6)
			{
				row++;
				System.out.println();
			}
		}
	}	

	/**
	 * to get currentdate in yyyyMMdd form
	 * @return integer yyyyMMdd
	 */
	public int getTotalDate()
	{
		int totalDate = cal.get(Calendar.YEAR)*10000+(cal.get(Calendar.MONTH)+1)*100+cal.get(Calendar.DATE);
		return totalDate;
	}
	/**
	 * shows dayview + any event scheduled on this date
	 */
	public void showDayView()
	{
		System.out.print(arrayOfDays[cal.get(Calendar.DAY_OF_WEEK)-1]);
		System.out.print(", ");
		System.out.print(arrayOfMonths[cal.get(Calendar.MONTH)]);
		System.out.print(" ");
		System.out.print(cal.get(Calendar.DATE));
		System.out.print(", ");
		System.out.print(cal.get(Calendar.YEAR));
		System.out.println();
		int passingDate = cal.get(Calendar.YEAR)*10000+(cal.get(Calendar.MONTH)+1)*100+cal.get(Calendar.DATE);		
		showEventByDay(passingDate);	
	}
	/**
	 * sets current day -1 and showing day view 
	 */
	public void prevDay()
	{
		setDate(getDate()-1);
		update();
		//showDayView();
	}
	/**
	 * sets current day -1 and showing day view 
	 */
	public void nextDay()
	{
		setDate(getDate()+1);
		update();
	}
	/**
	 * sets current month +1 and showing month view 
	 */
	public void nextMonth()
	{
		setMonth(getMonth()+1);
		update();
	}
	/**
	 * sets current month -1 and showing month view 
	 */
	public void prevMonth()
	{
		setMonth(getMonth()-1);
		update();
	}
    /**
     * checks if there's any time conflict before putting time data of event into arraylist
     * @param r_key integer of whole date yyyyMMdd
     * @param r_sTime starting time HHmm
     * @param r_eTime ending time HHmm
     * @return true if there's time conflict, false if not
     */
	public boolean checkConflict(int r_key, int r_sTime, int r_eTime)
	{
		// if there's any schedule on that date, have to check the time conflict
		if(map.containsKey(r_key))
		{
			//setting needed variables and arraylist in order to check the time conflict
			ArrayList<MyEvent> checkArrayList = new ArrayList<MyEvent>();
			checkArrayList = map.get(r_key);
			MyEvent oldinfo = new MyEvent();
			MyEvent oldinfo2 = new MyEvent();
			boolean available = false;
			//if there's only one schedule on that day 
			if(checkArrayList.size()==1)
			{
				oldinfo = checkArrayList.get(0);
				// either before or after the scheduled event there will be spot to schedule
				if(r_eTime<=oldinfo.getS_Time()||r_sTime>=oldinfo.getE_Time())
				{
					available =true;
				}
			}
			// if there's more than one schedule on that day
			else
			{
				// checking the availability
				for(int i=1; i<checkArrayList.size();i++)
				{
					oldinfo = checkArrayList.get(i-1);
					oldinfo2 = checkArrayList.get(i);
					// checking if there's available spot before the earliest event
					if(i==1)
					{
						if(r_eTime<=oldinfo.getS_Time())
						{
							
							available = true;
						}
					}
					// checking if there's available spot between current i and i-1 scheduled events
					if(r_sTime>=oldinfo.getE_Time()&&r_eTime<=oldinfo2.getS_Time())
					{
						
						available = true;
					}
					// checking if there's available spot after the last scheduled event
					if(i==(checkArrayList.size()-1))
					{
						if(r_sTime>=oldinfo2.getE_Time())
						{
							
							available = true;
						}
					}
				}
			}
			//if there's available spot, there's no time conflict. vice versa.
			if(available == true)
				return false;
			else
				return true;
		}
		// there's no key then no conflict
		else
			return false;
	}
	/**
	 * checks if there's any big event(starting time == ending time)
	 * since all the events are inserted in timely sorted order only need to check the last event
	 * @param r_key integer of whole date yyyyMMdd
     * @param r_sTime starting time HHmm
     * @param r_eTime ending time HHmm
	 * @return true if there's a big event that takes place before current event, 
	 * @return false if current event is either before the big event or there's no big event
	 */
	public boolean checkBigEvent(int r_key, int r_sTime, int r_eTime)
	{
		// if there's any schedule on that day
		if(map.containsKey(r_key))
		{
			// setting necessary arraylist to check the existence of the big event
			ArrayList<MyEvent> checkArrayList = new ArrayList<MyEvent>();
			checkArrayList = map.get(r_key);
			if(checkArrayList.size()>0)
			{
				MyEvent checkinfo = new MyEvent();
				// letting checkinfo to get the last myevent of the arraylist
				checkinfo = checkArrayList.get(checkArrayList.size()-1);
				// if the event is before the last event in the arraylist
				if(checkinfo.getS_Time()>r_eTime)	
				{
					// if the event is big event, then bigevent conflict
					if(r_sTime == r_eTime)
					{
						return true;
					}
					// if not, then no bigevent conflict
					return false;
				}
				// if the event is either happening after the last event or at the same time(another big event)
				else
				{
					// checking if the last event in the arraylist is the big event
					if(checkinfo.getS_Time()==checkinfo.getE_Time())
					{
						// bigevent
						return true;
					}
					// no bigevent
					else
						return false;	
				}

			}
			else
				return false;
		}
		// if there's no event on that day, no big event.
		else
			return false;
	}
	/**
	 * Inserts event into arraylist in timely sorted order
	 * it's designed to handle time duplicate (scheduled event has the same time) when loading the data
	 * @param MyEvent object containing schedule data
	 */
	public void insertArraylist(MyEvent r)
	{
		// setting up a new event to be equal to parameter event r
		MyEvent newinfo = new MyEvent();
		newinfo.setDate(r.getDate());
		newinfo.setTitle(r.getTitle());
		newinfo.setS_Time(r.getS_Time());
		newinfo.setE_Time(r.getE_Time());			
		// if there's no event scheduled yet, just add new event to array
		if(eventList.size()==0)
		{
			eventList.add(newinfo);	
		}
		// if there's scheduled event
		else
		{
			// setting two new myevents (one will take i of arraylist and the other will take i-1 of arraylist)
			MyEvent oldinfo = new MyEvent();
			MyEvent oldinfo2 = new MyEvent();
			// checking if the last event is the duplicate (same starting time and ending time)
			oldinfo = eventList.get(eventList.size()-1);
			if(newinfo.getS_Time()==oldinfo.getS_Time()||newinfo.getE_Time()==oldinfo.getE_Time())
			{
				//System.out.println("Duplicate");
			}
			// if there's no duplicate
			else
			{
				// if the event is after the last event
				if(newinfo.getS_Time()>=oldinfo.getE_Time())
				{
					// just add the event
					eventList.add(newinfo);
				}
				// if not
				else
				{
					// starting desired location_index to be the last index
					int location_index = eventList.size()-1;
					boolean duplicate = false;
					// traversing whole arraylist from the last index
					for(int i=eventList.size()-1; i>=0; i--)
					{
						oldinfo = eventList.get(i);
						// if there's more than 1 schedule in the arraylist
						if(i!=0)
						{
							oldinfo2 = eventList.get(i-1);
							// if the event can fit between event at i and another event at i-1
							if(newinfo.getE_Time()<=oldinfo.getS_Time()&&newinfo.getS_Time()>=oldinfo2.getE_Time())
							{
								// checking time duplicate
								if(newinfo.getS_Time()==oldinfo.getS_Time()||newinfo.getE_Time()==oldinfo.getE_Time())
								{
									duplicate = true;
								}
								// otherwise, saving the found location index
								else 
								{
									location_index=i;	
								}	
							}
						}
						// when reaching the very first scheduled event in the arraylist
						else
						{
							// checking if the event can fit before the first event
							if(newinfo.getE_Time()<=oldinfo.getS_Time())
							{
								// checking duplicate
								if(newinfo.getS_Time()==oldinfo.getS_Time()||newinfo.getE_Time()==oldinfo.getE_Time())
								{
									duplicate = true;
								}
								// saving the location index
								else 
								{
									location_index=i;	
								}
							}
						}
					}
					// if there's no duplicate, saving it in found location index
					if(duplicate == false)
					{
						eventList.add(location_index, newinfo);	
					}
				}
			}		
		}
	}
	
	/**
	 * deletes all the events corresponding to the specific date
	 * @param r_key yyyyMMdd
	 */
	public void deleteDay(int r_key)
	{
		// removing the key
		map.remove(r_key);
		// setting needed varialbes to show the message later
		MyEvent deleteInfo = new MyEvent();
		int r_year = ((r_key/10000));
		int r_month = ((r_key-r_year*10000)/100);
		int r_day = r_key-r_year*10000-r_month*100;
		boolean found = false;
		// deleting every element of the arraylist on that date
		for(int i=0; i<eventList.size();i++)
		{
			deleteInfo = eventList.get(i);
			if(r_key==deleteInfo.getDate())
			{
				found = true;
				eventList.remove(i);
			}
		}
		// if there's no such event, then show this message
		if(found==false)
		{
			System.out.println("There's no scheduled event to delete on "+r_month+"/"+r_day+"/"+r_year);
		}
		// when all the event on that day get succesfully deleted, show this message
		else
		{
			System.out.println("All the scheduled events on "+r_month+"/"+r_day+"/"+r_year+" got deleted");
		}
	}

	/**
	 * deletes all events
	 */
	public void deleteAll()
	{
		eventList.clear();
		map.clear();
	}
	
	/*public void getArraylist()
	{
		MyEvent temp = new MyEvent();
		
		for(int i=0; i<eventList.size(); i++)
		{
			temp = eventList.get(i);
			System.out.print(temp.getDate()+"\t"+temp.getTitle()+"\t"+temp.getS_Time()+"\t"+temp.getE_Time());
			System.out.println();
			}
	} saved for later use
	*/
	/**
	 * inserts event into arraylist along with hashmap
	 * @param r_key yyyyMMdd
	 * @param r MyEvent holding details of the event
	 */
	public void insertHashmap(int r_key, MyEvent r)
	{
		ArrayList<MyEvent> newArrayList = new ArrayList<MyEvent>();
		MyEvent temp_event = r;
		//in case the key is already in the hashmap data
		if(map.containsKey(r_key))
		{
			//saving the arraylist corresponding to the key
			//and update the arraylist and replace with the old arraylist
			newArrayList = map.get(r_key);
			setArrayList(newArrayList);
			insertArraylist(temp_event);
			newArrayList = getArray();
			map.replace(r_key, newArrayList);
		}
		else
		{	
			//saving the arraylist, updating it then put it in hashtable
			setArrayList(newArrayList);
			insertArraylist(temp_event);
			newArrayList = getArray();
			map.put(r_key, newArrayList);
		}		
	}
	
	/**
	 * reads events.txt file
	 */
	public void readText()
	{
		//setting needed variables
		String fileName = "C:\\java MyCalendarTester\\events.txt";
		int key = 0;
		String title = "";
		int date = 0;
		int sTime = 0;
		int eTime = 0;
		boolean gotData = false;
		// making sure there's no error while reading the file
		try
		{
			// clearing currently saved data
			deleteAll();
			// setting needed variables
			FileReader ifstream = null;
			ifstream = new FileReader(fileName);
			String line = null;
			String strs[] = null;
			BufferedReader in = new BufferedReader(ifstream);
			// reading each line and saving it in line, if this is null then while loop stops.
			while((line = in.readLine())!= null)
			{
				// tab splits strings and the result gets saved in strs String array
				strs = null;
				strs = line.split("\t");
				// when there's no tab, the line contains only key of hashmap
				if(strs.length==1)
				{
					key = Integer.valueOf(strs[0]);
				}
				// when there's tab meaning this line contains data for event
				else
				{
					gotData = true;
					// assigning splitted strings into variables to insert in arraylist later
					for(int i = 0; i<strs.length; i++)
					{
						//trim unnecessary white spaces
						strs[i]=strs[i].trim();
					}
					title = strs[1];
					date = Integer.valueOf(strs[2]);
					sTime = Integer.valueOf(strs[3]);
					eTime = Integer.valueOf(strs[4]);
				}
				// once collecting the data and the key, insert them into the hashmap
				if(gotData == true)
				{
					MyEvent temp = new MyEvent();
					temp.setTitle(title);
					temp.setDate(date);
					temp.setS_Time(sTime);
					temp.setE_Time(eTime);
					insertHashmap(key, temp);
					gotData = false;
				}
			}
			in.close();
		}
		catch (FileNotFoundException fnf)
		{
			System.out.println("This is the first run, the events.txt file can't be found");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		// showing message when loading input.txt is successful
		if(gotData == true)
		{
			System.out.println("Loading input.txt is completed");
		}
	}
	
	/**
	 * writes scheduled events into events.txt file
	 */
	public void writeInText()
	{
		// initializing necessary variables and copying hashmap into treemap for sorted keys
		Map<Integer, ArrayList<MyEvent>> tmap = new TreeMap<Integer, ArrayList<MyEvent>>(map);
		boolean worked = false;
		boolean clear = false;
		// if there's no scheduled event
		if(tmap.isEmpty())
		{
			// setting necessary variables to write events.txt
			String fileLoc = "C:\\java MyCalendarTester\\events.txt";
			File file = new File(fileLoc);
			File directory = new File("C:\\java MyCalendarTester\\");
			// checking if there's no error while clearing events.txt
			try 
			{
				// if there no such directory, make one
				if(!directory.exists())
				{
					directory.mkdir();
				}
				// if the file doesn't exist yet, make one
				if(!file.exists())
				{
					file.createNewFile();
				}
				// clearing output.txt
				FileWriter fstream = new FileWriter(file.getAbsoluteFile());
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("");
				out.flush();
				out.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			clear = true;
		}
		// if there's any scheduled event
		else
		{
			String fileLoc = "C:\\java MyCalendarTester\\events.txt";
			File file = new File(fileLoc);
			File directory = new File("C:\\java MyCalendarTester\\");
			try 
			{
				// if there no such directory, make one
				if(!directory.exists())
				{
					directory.mkdir();
				}
				// if the file doesn't exist yet, make one
				if(!file.exists())
				{
					file.createNewFile();
				}
				// ready to write file
				FileWriter fstream = new FileWriter(file.getAbsoluteFile());
				BufferedWriter out = new BufferedWriter(fstream);
				// traversing keys of the treemap via entryset
				for(Entry<Integer, ArrayList<MyEvent>> en : tmap.entrySet())
				{
					// writing out keys with new line
					String key = String.valueOf(en.getKey());
					out.write(key);
					out.newLine();
					out.flush(); 
					// traversing values(Arraylist of MyEvent objects) of the treemap	
					for(MyEvent obj : en.getValue())
					{	
						// writing all the data of the event in string formats 
						DecimalFormat df = new DecimalFormat("0000");
						String date = String.valueOf(obj.getDate());
						String sTime = df.format(obj.getS_Time());
						String eTime = df.format(obj.getE_Time());
						out.write("\t"+ obj.getTitle()+"\t"+date+"\t"+sTime+"\t"+eTime);
						out.newLine();
						out.flush(); 			
					}
				}
				out.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			worked = true;
		}
		// showing this message when the scheduled events are successfully written in events.txt
		if(worked == true)
		{
			System.out.println("Scheduled events are saved in events.txt");
		}
		// showing this message when events.txt got cleared
		if(clear == true)
		{
			System.out.println("output.txt got cleared since there's no scheduled event");
		}

	}
	/**
	 * displays all events in the hashmap
	 */
	public void getAllEvents()
	{
		// saving hashmap into treemap
		Map<Integer, ArrayList<MyEvent>> tmap = new TreeMap<Integer, ArrayList<MyEvent>>(map);
		// there's no scheduled event
		if(tmap.isEmpty())
		{
			System.out.println("There's no scheduled event");
		}
		else
		{	
			// traversing keys of the treemap over the entryset of the treemap
			for(Entry<Integer, ArrayList<MyEvent>> en : tmap.entrySet())
			{
				// displaying key (date)
				DecimalFormat df = new DecimalFormat("00");
				int r_year = ((en.getKey()/10000));
				int r_month = ((en.getKey()-r_year*10000)/100);
				int r_date = en.getKey()-r_year*10000-r_month*100;
				System.out.println(df.format(r_month)+"/"+df.format(r_date)+"/"+r_year);
				// traversing values(Arraylist of MyEvent objects) of the treemap
				for(MyEvent obj : en.getValue())
				{	
					// displaying detailed data of the event
					int stimehour = obj.getS_Time()/100;
					int stimemin = obj.getS_Time()-(stimehour*100);
					int etimehour = obj.getE_Time()/100;
					int etimemin = obj.getE_Time()-(etimehour*100);
					System.out.println(String.format("%-30s %-5s %-1s %-5s" , obj.getTitle(), df.format(stimehour)+":"+df.format(stimemin),"-", df.format(etimehour)+":"+df.format(etimemin)));
				}
			}	
		}
	}
	public HashMap<Integer, ArrayList<MyEvent>> getMap()
	{
		return this.map;
	}
	/**
	 * displays all events of the parsed date (r_key)
	 * @param r_key yyyyMMdd
	 */
	public void showEventByDay(int r_key)
	{
		// if there's schedule on that day
		if(map.containsKey(r_key))
		{
			// traversing hashmap using entryset
			for(Entry<Integer, ArrayList<MyEvent>> en : map.entrySet())
			{
				// on the parsed date
				if(r_key==en.getKey())
				{
					// displaying the date
					DecimalFormat df = new DecimalFormat("00");
					int r_year = ((en.getKey()/10000));
					int r_month = ((en.getKey()-r_year*10000)/100);
					int r_date = en.getKey()-r_year*10000-r_month*100;
					System.out.println(df.format(r_month)+"/"+df.format(r_date)+"/"+r_year);
					// traversing keys(MyEvent objects) of the hashmap
					for(MyEvent obj : en.getValue())
					{	
						// displaying the detailed data of the event

						int stimehour = obj.getS_Time()/100;
						int stimemin = obj.getS_Time()-(stimehour*100);
						int etimehour = obj.getE_Time()/100;
						int etimemin = obj.getE_Time()-(etimehour*100);
						System.out.println(String.format("%-30s %-5s %-1s %-5s" , obj.getTitle(), df.format(stimehour)+":"+df.format(stimemin),"-", df.format(etimehour)+":"+df.format(etimemin)));
					}
				}
			}
		}
		// if there's no schedule on that day
		else
		{
			int r_year = ((r_key/10000));
			int r_month = ((r_key-r_year*10000)/100);
			int r_date = r_key-r_year*10000-r_month*100;
			System.out.println("There's no schedule on "+r_month+"/"+r_date+"/"+r_year);
		}
	}
	
	/**
	 * displays all events of the parsed month
	 * @param r_key yyyyMMdd
	 */
	public void showEventByMonth(int r_key)
	{
		// removing day of r_key(date)
		r_key = (r_key/100)*100;
		boolean event = false;
		// checking if there's any event on that month
		for(int i=0; i<31; i++)
		{	
			r_key++;
			// if there's event on that month
			if(map.containsKey(r_key))
			{	
				// traversing hashmap's keys using entryset
				for(Entry<Integer, ArrayList<MyEvent>> en : map.entrySet())
				{
					// if there's event on that month
					if(r_key==en.getKey())
					{
						// displaying days
						event = true;
						int date = en.getKey();
						int modifier = ((date/100)*100);
						date = date - modifier;
						System.out.println("Day: "+date);
						// traversing values(arraylist of myevent)
						for(MyEvent obj : en.getValue())
						{	
							// displaying the details of the event
							DecimalFormat df = new DecimalFormat("00");
							int stimehour = obj.getS_Time()/100;
							int stimemin = obj.getS_Time()-(stimehour*100);
							int etimehour = obj.getE_Time()/100;
							int etimemin = obj.getE_Time()-(etimehour*100);
							System.out.println(String.format("%-30s %-5s %-1s %-5s" , obj.getTitle(), df.format(stimehour)+":"+df.format(stimemin),"-", df.format(etimehour)+":"+df.format(etimemin)));
						}
					}
				}
			}
		}
		if(event == false)
		{
			System.out.println("There's no schedule on this month");
		}
	}
	
	/**
	 * collects days that hold scheduled event on the month and returning the arraylist containing these days
	 * @param r_key yyyyMMdd
	 * @return arraylist containing days that hold scheduled events on the month of r_key
	 */
	public ArrayList<Integer> getDaysOfMonth(int r_key)
	{
		// removing day part of the whole date(r_key)
		r_key = (r_key/100)*100;
		ArrayList<Integer> daysofMonth = new ArrayList<Integer>();
		// checking from day 1 to 31
		for(int i=0; i<31; i++)
		{	
			r_key++;
			// if there's a scheduled event
			if(map.containsKey(r_key))
			{
				// traversing keys(date) of hashmap
				for(Entry<Integer, ArrayList<MyEvent>> en : map.entrySet())
				{
					// if there's a event on that date
					if(r_key==en.getKey())
					{
						// saving the day into daysofMonth array
						int date = en.getKey();
						int modifier = ((date/100)*100);
						date = date - modifier;
						daysofMonth.add(date);
					}
				}
			}
		}
		return daysofMonth;
	}
}
