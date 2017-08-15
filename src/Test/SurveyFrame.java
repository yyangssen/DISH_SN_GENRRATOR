package Test;

import java.awt.*;
import javax.swing.*;

public class SurveyFrame extends JFrame {
	public SurveyFrame() {
		super("Generotor");
		//this.setSize(290,140);
		this.setBounds(200, 300, 200, 250);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JCalendarChooser date=new JCalendarChooser(this);
		date.showCalendarDialog();
		Dish_SN_Wazard wiz=new Dish_SN_Wazard(date.getCurrentDate());
		this.add(wiz);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new SurveyFrame();
	}
}
