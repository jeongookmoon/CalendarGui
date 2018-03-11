import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class View extends JFrame implements ChangeListener
{
	private Model model;
	private JFrame frame;
	private JPanel calendarPanel, eventPanel, monthViewPanel;
	private JButton prevButton, nextButton, createButton, quitButton;
	private JTextPane eventPane;
	private ArrayList<JButton> buttons;
	private JLabel calendarLabel;
	private int prevDate;
	
	
	public View(Model cal)
	{
		model = new Model();
		model = cal;
		model.readText();
		frame = new JFrame("Calendar");
		
		eventPanel = new JPanel();
		
		// making monthView part
		monthViewPanel = new JPanel();
		monthViewPanel.setLayout(new GridLayout(0,7));
		
		prevButton = new JButton("Previous Month");
		prevButton.addActionListener(new 
				ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						model.prevMonth();
					}
		});
		nextButton = new JButton("Next Month");
		nextButton.addActionListener(new 
				ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						model.nextMonth();
					}
		});

		buttons = new ArrayList<JButton>();
		
		prevDate = -1;
		
		fillCalendar();
		
		
		calendarPanel = new JPanel(new BorderLayout());
		calendarLabel = new JLabel(model.arrayOfMonths[model.getMonth()]+" "+model.getDate()+", "+model.getYear(), SwingConstants.CENTER);
		calendarPanel.add(calendarLabel, BorderLayout.NORTH);
		calendarPanel.add(monthViewPanel, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(prevButton);
		buttonsPanel.add(nextButton);
		calendarPanel.add(buttonsPanel, BorderLayout.SOUTH);
		colorDate(model.getDate()-1);

		//making dayView part
		eventPane = new JTextPane();
		eventPane.setPreferredSize(new Dimension(450, 223));
		eventPane.setEditable(false);
		eventByDay(model.getYear()*10000+model.getMonth()*100+model.getDate());
		eventPane.setCaretPosition(0);
		
		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		JScrollPane dayScroll = new JScrollPane(eventPane);
		dayScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		dayPanel.add(dayScroll, c);
		JPanel dayButtons = new JPanel();
		createButton = new JButton("Create");
		createButton.addActionListener(new 
				ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						createEvent();
						
					}
			
		});
		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.writeInText();
				System.exit(0);
			}
		});
		dayButtons.add(createButton);
		dayButtons.add(quitButton);
		c.gridx = 0;
		c.gridy = 0;
		dayPanel.add(dayButtons, c);
		frame.add(calendarPanel);
		frame.add(dayPanel);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
		
		
	}
	public void createEvent()
	{
		final JDialog eventDialog = new JDialog();
		eventDialog.setTitle("Create event");
		eventDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		final JTextField eventText = new JTextField(30);
		final JTextField timeStart = new JTextField(10);
		final JTextField timeEnd = new JTextField(10);
		JButton save = new JButton("Save");
		MyEvent ex = new MyEvent();
		save.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if (eventText.getText().isEmpty()) {
					return;
				}
				String date[] = timeStart.getText().split(":");
				int dateValues[] = new int[date.length];
				for (int i=0; i<dateValues.length; i++)
				{
					dateValues[i] = Integer.valueOf(date[i]);
				}
				int sTimeMin = dateValues[1];
				int sTimeHour = dateValues[0];
				String date2[] = timeEnd.getText().split(":");
				int dateValues2[] = new int[date2.length];
				for (int i=0; i<dateValues2.length; i++)
				{
					dateValues2[i] = Integer.valueOf(date2[i]);
				}
				int eTimeMin = dateValues2[1];
				int eTimeHour = dateValues2[0];
				
				boolean timeConflict = model.checkConflict(model.getYear()*10000+model.getMonth()*100+model.getDate(), (sTimeHour*100)+sTimeMin, (eTimeHour*100)+eTimeMin);
				// when there's no conflict
				if(timeConflict == true)
				{
					JDialog conflictDialog = new JDialog();
					conflictDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					conflictDialog.setLayout(new GridLayout(2, 0));
					conflictDialog.add(new JLabel("Time conflict!!"));
					JButton close = new JButton("Close");
					close.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							conflictDialog.dispose();
						}
					});
					conflictDialog.add(close);
					conflictDialog.pack();
					conflictDialog.setVisible(true);
				}
				else 
				{
					eventDialog.dispose();
					ex.setTitle(eventText.getText());
					ex.setDate(model.getYear()*10000+model.getMonth()*100+model.getDate());
					ex.setS_Time((sTimeHour*100)+sTimeMin);
					ex.setE_Time((eTimeHour*100)+eTimeMin);
					model.insertHashmap(model.getYear()*10000+model.getMonth()*100+model.getDate(), ex);
					eventByDay(model.getYear()*10000+model.getMonth()*100+model.getDate());
				}
			}
		});
		eventDialog.setLayout(new GridBagLayout());
		JLabel date = new JLabel();
		date.setText(model.arrayOfMonths[model.getMonth()]+" "+model.getDate()+", "+model.getYear());
		date.setBorder(BorderFactory.createEmptyBorder());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		c.gridx = 0;
		c.gridy = 0;
		eventDialog.add(date, c);
		c.gridy = 1;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.LINE_START;
		eventDialog.add(new JLabel("Event Title :"), c);
		c.gridy = 2;
		eventDialog.add(eventText, c);
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		eventDialog.add(new JLabel("Start(HH:MM) :"), c);
		c.anchor = GridBagConstraints.CENTER;
		eventDialog.add(timeStart, c);
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_START;
		eventDialog.add(new JLabel("End(HH:MM) :"), c);
		c.anchor = GridBagConstraints.CENTER;
		eventDialog.add(timeEnd, c);
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		eventDialog.add(save, c);
		eventDialog.pack();
		eventDialog.setVisible(true);
	}
	public void colorDate(int date)
	{
		Border border = new LineBorder(Color.DARK_GRAY, 3);
		buttons.get(date).setBorder(border);
		if(prevDate != -1)
		{
			buttons.get(prevDate).setBorder(new JButton().getBorder());
		}
		prevDate = date;		
	}
	
	
	public void fillCalendar()
	{
		// saving current date
		int temp = model.getDate();

		// set the date to be 1 to figure out the day (ex, monday) of the first day of the month

		model.setDate(1);

		// setting actual firstday and lastday of the month
		int firstDay = model.getDayOfWeek()-1; 
		int lastDay = model.getLastDayOfMonth(); 

		// let the date back to be current date 
		model.setDate(temp);
		
		for(int i = 0; i<model.arrayOfDays.length; i++)
		{
			JButton dayButton = new JButton(model.arrayOfDays[i]);
			dayButton.setEnabled(false);
			monthViewPanel.add(dayButton);
		}

		//initializing the variable row
		for(int i=0; i<firstDay; i++)
		{
			JButton blank = new JButton();
			blank.setEnabled(false);
			monthViewPanel.add(blank);
		}
	
		for(int i=1; i<=lastDay; i++)
		{
			final int assignDate = i;
			JButton day = new JButton(Integer.toString(assignDate));
			day.setBackground(Color.WHITE);
			day.addActionListener(new 
					ActionListener() 
					{	
						public void actionPerformed(ActionEvent e)
						{
							model.setDate(assignDate);
							calendarLabel.setText(model.arrayOfMonths[model.getMonth()]+" "+model.getDate()+", "+model.getYear());
							colorDate(assignDate-1);
							eventByDay(model.getYear()*10000+model.getMonth()*100+model.getDate());
						}		
					});
			buttons.add(day);
		}
		
		for(JButton b : buttons)
		{
			monthViewPanel.add(b);
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		calendarLabel.setText(model.arrayOfMonths[model.getMonth()]+" "+model.getDate()+", "+model.getYear());
		buttons.clear();
		monthViewPanel.removeAll();
		fillCalendar();
		prevDate=-1;
		colorDate(model.getDate()-1);
		eventPane.removeAll();
		eventByDay(model.getYear()*10000+model.getMonth()*100+model.getDate());
		frame.pack();
		frame.repaint();
	}
	public void eventByDay(int r_key)
	{
		// if there's schedule on that day
		if(model.getMap().containsKey(r_key))
		{
			// traversing hashmap using entryset
			for(Entry<Integer, ArrayList<MyEvent>> en : model.getMap().entrySet())
			{
				// on the parsed date
				if(r_key==en.getKey())
				{
					// displaying the date
					DecimalFormat df = new DecimalFormat("00");
					int r_year = ((en.getKey()/10000));
					int r_month = ((en.getKey()-r_year*10000)/100);
					int r_date = en.getKey()-r_year*10000-r_month*100;
					String val = df.format(r_month)+"/"+df.format(r_date)+"/"+r_year+"\n";
					// traversing keys(MyEvent objects) of the hashmap
					for(MyEvent obj : en.getValue())
					{	
						// displaying the detailed data of the event

						int stimehour = obj.getS_Time()/100;
						int stimemin = obj.getS_Time()-(stimehour*100);
						int etimehour = obj.getE_Time()/100;
						int etimemin = obj.getE_Time()-(etimehour*100);
						val = val + String.format("%-30s %-5s %-1s %-5s" , obj.getTitle(), df.format(stimehour)+":"+df.format(stimemin),"-", df.format(etimehour)+":"+df.format(etimemin))+"\n";
						eventPane.setText(val);
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
			eventPane.setText("No schedule on "+model.arrayOfMonths[model.getMonth()]+" "+model.getDate()+", "+model.getYear());
		}
	}
}
