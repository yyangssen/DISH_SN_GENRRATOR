package Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.*;
import javax.swing.*;

import Exception.InputException;
import Exception.getTableException;

import java.io.*;

public class Dish_SN_Wazard extends JPanel implements ActionListener {
	int currentCard = 0;
	String CurrentDate;
	CardLayout card = new CardLayout();
	SurveyPanel[] input = new SurveyPanel[3];
	Hashtable monthTable = new Hashtable();
	Hashtable yearTable = new Hashtable();

	public Dish_SN_Wazard(String date) {

		setLayout(card);
		input[0] = new SurveyPanel("LSLTE", "start with(5)");
		input[1] = new SurveyPanel(null, "mount ");
		
		input[0].preButton.addActionListener(this);
		input[1].preButton.addActionListener(this);
		input[0].nextButton.addActionListener(this);
		input[1].nextButton.addActionListener(this);
		
		
		add(input[0], "Step 0");
		add(input[1], "Step 1");

		this.CurrentDate = date;

		this.initMonthHashTable();
		this.initYearHashTable();
		this.show();
	}

	private void initMonthHashTable() {

		monthTable.put("01", "A");
		monthTable.put("02", "B");
		monthTable.put("03", "C");
		monthTable.put("04", "D");
		monthTable.put("05", "E");
		monthTable.put("06", "F");
		monthTable.put("07", "G");
		monthTable.put("08", "H");
		monthTable.put("09", "J");
		monthTable.put("10", "K");
		monthTable.put("11", "L");
		monthTable.put("12", "M");

	}

	private void initYearHashTable() {
		yearTable.put("2015", "B");
		yearTable.put("2016", "C");
		yearTable.put("2017", "D");
		yearTable.put("2018", "E");
		yearTable.put("2019", "F");
	}

	private boolean GeneratorSN(String start, String date, String mount) throws InputException,getTableException {
		String year = date.substring(0, 4);
		// String month=date.substring(6);
		int head = date.indexOf("-");
		int tail = date.lastIndexOf("-");
		String month = date.substring(++head, tail);
		//
		if (Integer.parseInt(mount) >= 99999) {// 数量超过范围的异常
			throw new InputException("数量大于99999");
		} 
		
		if((this.yearTable.get(year) == null)||(this.monthTable.get(month)==null)) {
			throw new getTableException("年份或月份无定义");
		}
		try {
			FileWriter file = new FileWriter("DISH_SN.txt");
			System.out.println("the file path is "+file.getClass());
			for (int i = 0; i < Integer.parseInt(mount); i++) {
				file.write(
						String.format("%s%s%05d%s%n", start, this.yearTable.get(year), i, this.monthTable.get(month)));
			}
			file.close();
		} catch (IOException e) {
			e.getMessage();
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		Object source = evt.getSource();
		if (source == input[currentCard].nextButton) {
			if (currentCard == 1) {
				if (input[1].input.getText().equals("")) {// getText返回一个String类型的对象
					currentCard--;
					String response = JOptionPane.showInputDialog(this, "Enter the mount:", "Input",
							JOptionPane.QUESTION_MESSAGE);
					input[1].input.setText(response);
				} else {
					// input[3]=new SurveyPanel("LSLTE","2017.8.9","1000");
					input[2] = new SurveyPanel(input[0].input.getText(), this.CurrentDate, input[1].input.getText());
					input[2].finalButton.addActionListener(this);
					input[2].preButton.addActionListener(this);
					add(input[2], "Step 2");
				}

			}

			currentCard++;
			if (currentCard >= input.length) {
				System.exit(0);
			}

			card.show(this, "Step " + currentCard);

		} else if (source == input[currentCard].finalButton) {
			try {
				this.GeneratorSN(input[2].labelStart.getText(), input[2].labelDate.getText(),
						input[2].labelMount.getText());
				JOptionPane.showMessageDialog(null, "Gennerate finish");
				System.exit(0);
			} catch (InputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(getTableException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "年份或月份无定义");
				System.exit(0);
			}
			finally{
				input[1].input.setText("");
				currentCard--;
				card.show(this, "Step " + currentCard);
			}
			
		}else if(source == input[currentCard].preButton) {
			
			if (currentCard <1) {

			}else {
				currentCard--;
				card.show(this, "Step " + currentCard);
			}

			
		}

	}

}

class SurveyPanel extends JPanel {
	JLabel item;
	JButton nextButton = new JButton("Next");
	JButton finalButton = new JButton("Generator");
	JButton preButton=new JButton("Previous");
	JButton chooseDateButton;
	Calendar calendar;
	JTextField input;
	// 结果
	JLabel labelStart;
	JLabel labelDate;
	JLabel labelMount;

	// 文本输入面板
	SurveyPanel(String defaultValue, String tip) {
		super();
		this.setSize(160, 110);
		JPanel sub1 = new JPanel();
		this.item = new JLabel(tip + ":");
		sub1.add(item);
		// JPanel sub2=new JPanel();

		input = new JTextField(defaultValue, 10);
		sub1.add(input);
		JPanel sub3 = new JPanel();
		this.nextButton.setAlignmentX(RIGHT_ALIGNMENT);
		sub3.add(preButton);
		sub3.add(nextButton);
		GridLayout grid = new GridLayout(3, 1);
		setLayout(grid);
		add(sub1);
		//add(sub2);
		add(sub3);
	}

	// 日期选择面板
	SurveyPanel(String date) {

		super();
		// calendar=Calendar.getInstance();
		JPanel sub1 = new JPanel();
		// JLabel lab=new JLabel("current
		// date:"+calendar.get(1)+"-"+calendar.get(2)+"-"+calendar.get(5));
		JLabel lab = new JLabel(date);
		chooseDateButton = new JButton("Next");
		BoxLayout box = new BoxLayout(sub1, BoxLayout.Y_AXIS);
		sub1.setLayout(box);
		sub1.add(lab);
		sub1.add(chooseDateButton);
		this.add(sub1);
		// chooseDate=new JButton("");
	}

	// 结果显示面板
	SurveyPanel(String start, String date, String mount) {
		super();
		this.setSize(120, 30);
		// JPanel sub1=new JPanel();
		JLabel label = new JLabel("Confirm");
		// sub1.add(label);
		JPanel sub2 = new JPanel();
		JLabel label2 = new JLabel("Start:");
		labelStart = new JLabel(start);
		JLabel label3 = new JLabel("Date:");
		labelDate = new JLabel(date);
		JLabel label4 = new JLabel("Mount:");
		labelMount = new JLabel(mount);
		BoxLayout box = new BoxLayout(sub2, BoxLayout.Y_AXIS);
		sub2.add(label);
		sub2.setLayout(box);
		sub2.add(label2);
		sub2.add(labelStart);

		sub2.add(label3);
		sub2.add(labelDate);
		sub2.add(label4);
		sub2.add(labelMount);
		JPanel sub3 = new JPanel();
		sub3.add(preButton);
		sub3.add(finalButton);

		GridLayout grid = new GridLayout(2, 1);
		setLayout(grid);
		// this.add(sub1);
		this.add(sub2);
		this.add(sub3);

	}
}