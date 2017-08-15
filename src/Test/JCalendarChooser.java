package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by luyan on 15/12/20.
 */
public class JCalendarChooser extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 285;
    private static final int DEFAULT_HEIGHT = 280;
    private int showYears = 100;
    private JLabel label1 = null;
    private JButton button1 = null;
    private JButton button2 = null;
    private JButton button3 = null;
    private JComboBox comboBox1 = null;
    private JComboBox comboBox2 = null;
    private Calendar calendar = null;
    private String[] years = null;
    private String[] months = null;
    private int year1;
    private int month1;
    private int day1;
    private JPanel panel = null;
    private String[] tits = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private String title = "选择日期";
    private Point location = null;
    private JCalendarChooser.MouseDoubleClickedEvent mdcEvent = null;
    private JCalendarChooser chooser = null;
    private boolean flag;
    private String date;
    
    //
    public JCalendarChooser(){
    	super();
    	this.initDatas();
    }
    public JCalendarChooser(Frame parent) {
        super(parent, true);
        this.setTitle(this.title);
        this.initDatas();
    }

    public JCalendarChooser(Frame parent, String title) {
        super(parent, title, true);
        this.initDatas();
    }

    public JCalendarChooser(Frame parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        this.initDatas();
    }

    public JCalendarChooser(Frame parent, String title, Point location, int showYears) {
        super(parent, title, true);
        this.location = location;
        if(showYears > 0) {
            this.showYears = showYears;
        }

        this.initDatas();
    }

    public JCalendarChooser(Dialog parent) {
        super(parent, true);
        this.setTitle(this.title);
        this.initDatas();
    }

    public JCalendarChooser(Dialog parent, String title) {
        super(parent, title, true);
        this.initDatas();
    }

    public JCalendarChooser(Dialog parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        this.initDatas();
    }

    public JCalendarChooser(Dialog parent, String title, Point location, int showYears) {
        super(parent, title, true);
        this.setTitle(title);
        this.location = location;
        if(showYears > 0) {
            this.showYears = showYears;
        }

        this.initDatas();
    }

    private Dimension getStartDimension(int width, int height) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.width = dim.width / 2 - width / 2;
        dim.height = dim.height / 2 - height / 2;
        return dim;
    }

    private void initDatas() {
        this.chooser = this;
       // this.mdcEvent = new JCalendarChooser.MouseDoubleClickedEvent((JCalendarChooser.MouseDoubleClickedEvent)null);
        this.calendar = Calendar.getInstance();
        this.year1 = this.calendar.get(1);
        this.month1 = this.calendar.get(2);
        this.day1 = this.calendar.get(5);
        this.years = new String[this.showYears];
        this.months = new String[12];
        this.label1 = new JLabel();
        this.label1.setForeground(Color.MAGENTA);

        int start;
        for(start = 0; start < this.months.length; ++start) {
            this.months[start] = " " + this.formatDay(start + 1);
        }

        start = this.year1 - this.showYears / 2;

        for(int i = start; i < start + this.showYears; ++i) {
            this.years[i - start] = String.valueOf(i);
        }

        this.setInfo(this.year1 + "-" + this.formatDay(this.month1 + 1) + "-" + this.formatDay(this.day1) + "            ", Color.BLUE);
    }

    private void showDialog() {
        this.setLayout(new BorderLayout());
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(1));
        this.showNorthPanel(panel3);
        this.add(panel3, "North");
        this.add(this.printCalendar(), "Center");
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(2));
        this.showSouthPanel(panel2);
        this.add(panel2, "South");
        this.setSize(285, 280);
        if(this.location == null) {
            Dimension dim = this.getStartDimension(285, 280);
            this.setLocation(dim.width, dim.height);
        } else {
            this.setLocation(this.location);
        }

        this.setVisible(true);
    }

    private void showNorthPanel(JPanel panel) {
        this.button2 = new JButton("上一月");
        this.button2.setToolTipText("上一月");
        this.button2.addActionListener(this);
        panel.add(this.button2);
        this.comboBox1 = new JComboBox(this.years);
        this.comboBox1.setSelectedItem(String.valueOf(this.year1));
        this.comboBox1.setToolTipText("年份");
        this.comboBox1.setActionCommand("year");
        this.comboBox1.addActionListener(this);
        panel.add(this.comboBox1);
        this.comboBox2 = new JComboBox(this.months);
        this.comboBox2.setSelectedItem(" " + this.formatDay(this.month1 + 1));
        this.comboBox2.setToolTipText("月份");
        this.comboBox2.setActionCommand("month");
        this.comboBox2.addActionListener(this);
        panel.add(this.comboBox2);
        this.button3 = new JButton("下一月");
        this.button3.setToolTipText("下一月");
        this.button3.addActionListener(this);
        panel.add(this.button3);
    }

    private void showSouthPanel(JPanel panel) {
        panel.add(this.label1);
        this.button1 = new JButton("确定");
        this.button1.setToolTipText("确定");
        this.button1.addActionListener(this);
        panel.add(this.button1);
        panel.add(new JLabel(" "));
    }

    public Calendar showCalendarDialog() {
        this.showDialog();
        return this.calendar;
    }

    private void setInfo(String info, Color c) {
        if(this.label1 != null && c != null) {
            this.label1.setText(info);
            this.label1.setForeground(c);
        }

    }

    private String formatDay(int day) {
        return day < 10?"0" + day:String.valueOf(day);
    }

    private JPanel printCalendar() {
        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(7, 7, 0, 0));
        this.panel.setBorder(BorderFactory.createRaisedBevelBorder());
        int year2 = this.calendar.get(1);
        int month2 = this.calendar.get(2);
        this.calendar.set(5, 1);
        int weekDay = this.calendar.get(7);
        JButton b = null;

        int count;
        for(count = 0; count < this.tits.length; ++count) {
            b = new JButton("<html><b>" + this.tits[count] + "</b></html>");
            b.setForeground(Color.BLACK);
            b.setBackground(Color.WHITE);
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setEnabled(false);
            this.panel.add(b);
        }

        count = 0;

        int currday;
        for(currday = 1; currday < weekDay; ++currday) {
            b = new JButton(" ");
            b.setEnabled(false);
            this.panel.add(b);
            ++count;
        }

        boolean var9 = false;
        String dayStr = null;

        do {
            currday = this.calendar.get(5);
            dayStr = this.formatDay(currday);
            if(currday == this.day1 && this.month1 == month2 && this.year1 == year2) {
                b = new JButton("[" + dayStr + "]");
                b.setForeground(Color.RED);
            } else {
                b = new JButton(dayStr);
                b.setForeground(Color.BLUE);
            }

            ++count;
            b.setToolTipText(year2 + "-" + this.formatDay(month2 + 1) + "-" + dayStr);
            b.setBorder(BorderFactory.createEtchedBorder());
            b.addActionListener(this);
            b.addMouseListener(this.mdcEvent);
            this.panel.add(b);
            this.calendar.add(5, 1);
        } while(this.calendar.get(2) == month2);

        this.calendar.add(2, -1);
        if(!this.flag) {
            this.calendar.set(5, this.day1);
            this.flag = true;
        }

        for(int i = count; i < 42; ++i) {
            b = new JButton(" ");
            b.setEnabled(false);
            this.panel.add(b);
        }

        return this.panel;
    }

    private void updatePanel() {
        this.remove(this.panel);
        this.add(this.printCalendar(), "Center");
        this.validate();
    }
    public String getCurrentDate() {
    	return date;
    }
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int value;
        int str;
        int month5;
        if("下一月".equals(command)) {
            this.calendar.add(2, 1);
            value = this.calendar.get(1);
            str = this.year1 + this.showYears / 2 - 1;
            if(value > str) {
                this.calendar.add(2, -1);
                this.setInfo("年份越界: [" + value + " > " + str + "]      ", Color.RED);
                return;
            }

            month5 = this.calendar.get(2) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(value));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            this.updatePanel();
        } else if("上一月".equals(command)) {
            this.calendar.add(2, -1);
            value = this.calendar.get(1);
            str = this.year1 - this.showYears / 2;
            if(value < str) {
                this.calendar.add(2, 1);
                this.setInfo("年份越界: [" + value + " < " + str + "]      ", Color.RED);
                return;
            }

            month5 = this.calendar.get(2) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(value));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            this.updatePanel();
        } else if("确定".equals(command)) {
        	//
        	this.date=this.calendar.get(1) + "-" + this.formatDay(this.calendar.get(2) + 1) + "-" + this.formatDay(this.calendar.get(5));
            this.chooser.dispose();
        } else if(command.matches("^\\d+$")) {
            value = Integer.parseInt(command);
            this.calendar.set(5, value);
            String str1 = this.calendar.get(1) + "-" + this.formatDay(this.calendar.get(2) + 1) + "-" + this.formatDay(this.calendar.get(5));
            this.setInfo(str1 + "            ", this.getRandomColor());
        } else if(command.startsWith("[")) {
            this.calendar.set(5, this.day1);
            String value1 = this.calendar.get(1) + "-" + this.formatDay(this.calendar.get(2) + 1) + "-" + this.formatDay(this.calendar.get(5));
            this.setInfo(value1 + "            ", this.getRandomColor());
        } else if("year".equalsIgnoreCase(command)) {
            value = Integer.parseInt(this.comboBox1.getSelectedItem().toString().trim());
            this.calendar.set(1, value);
            this.updatePanel();
        } else if("month".equalsIgnoreCase(command)) {
            value = Integer.parseInt(this.comboBox2.getSelectedItem().toString().trim());
            this.calendar.set(2, value - 1);
            this.updatePanel();
        }

    }

    private Color getRandomColor() {
        Random random = new Random();
        Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        return c;
    }

    private class MouseDoubleClickedEvent extends MouseAdapter {
        private MouseDoubleClickedEvent() {
        }

        public void mouseClicked(MouseEvent e) {
            if(1 == e.getButton() && e.getClickCount() == 2) {
                JButton b = (JButton)e.getSource();
                String s = b.getText();
                if(s.matches("^\\d+$")) {
                    int day = Integer.parseInt(s);
                    JCalendarChooser.this.calendar.set(5, day);
                } else if(s.startsWith("[")) {
                    JCalendarChooser.this.calendar.set(5, JCalendarChooser.this.day1);
                }

                JCalendarChooser.this.chooser.dispose();
            }

        }
    }
}
