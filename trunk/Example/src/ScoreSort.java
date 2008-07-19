import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ScoreSort extends JPanel {
    class Student{
      int SNumber=0,Score=0;
    }
    Student[ ] st=new Student[10];//设所能处理的成绩个数为10
    int i;
    JTextField TNumberIn=new JTextField(8);//学号输入框
    JTextField TScoreIn=new JTextField(8);//成绩输入框
    JTextField TNumberQuery=new JTextField(8);//学号查询框
    JTextField TScoreQuery=new JTextField(8);//成绩查询框
    JTextArea textArea = new JTextArea(5, 20);//显示排序结果
        public ScoreSort( ) {	//数组元素初始化
        int i=0;
        for(i=0;i<st.length;i++){
          st[i]=new Student( );
        };
        JTabbedPane tabbedPane = new JTabbedPane( );        
        //建立输入面板
        JPanel panel1 =new JPanel( );
        panel1.setLayout(new BorderLayout( ));
        JLabel LNumberIn=new JLabel("请输入学号:");
        JPanel Panel11=new JPanel( );
        Panel11.setLayout(new FlowLayout( ));
        Panel11.add(LNumberIn);
        Panel11.add(TNumberIn);
        panel1.add(Panel11,BorderLayout.NORTH);
        JLabel LScoreIn=new JLabel("请输入成绩:");
        JPanel Panel12=new JPanel( );
        Panel12.setLayout(new FlowLayout( ));
        Panel12.add(LScoreIn);
        Panel12.add(TScoreIn);
        panel1.add(Panel12,BorderLayout.CENTER);
        JButton BIn=new JButton("确    认");
        panel1.add(BIn,BorderLayout.SOUTH);
        BIn.addActionListener(new InActionListener( ));
        tabbedPane.addTab("成绩输入", null, panel1, null);
        tabbedPane.setSelectedIndex(0);//一开始选中“成绩输入”标签面板
        //建立排序面板
        JPanel panel2 =new JPanel( );
        JButton BSort=new JButton(" 排    序 ");
        BSort.addActionListener(new SortActionListener( ));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea,
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        GridBagLayout gridBag = new GridBagLayout( );
        panel2.setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints( );
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        gridBag.setConstraints(scrollPane, c);
        panel2.add(scrollPane);
        c.fill = GridBagConstraints.HORIZONTAL;
        gridBag.setConstraints(BSort, c);
        panel2.add(BSort);
        tabbedPane.addTab("成绩排序", null, panel2, null);
        
        //建立查询面板
        JPanel panel3 = new JPanel( );
        panel3.setLayout(new BorderLayout( ));
        JLabel LNumberQuery=new JLabel("请输入学号:");
        JPanel Panel31=new JPanel( );
        Panel31.setLayout(new FlowLayout( ));
        Panel31.add(LNumberQuery);
        Panel31.add(TNumberQuery);
        panel3.add(Panel31,BorderLayout.NORTH);
        TScoreQuery.setEditable(false);
        JLabel LScoreQuery=new JLabel("您的成绩是:");
        JPanel Panel32=new JPanel( );
        Panel32.setLayout(new FlowLayout( ));
        Panel32.add(LScoreQuery);
        Panel32.add(TScoreQuery);
        panel3.add(Panel32,BorderLayout.CENTER);
        JButton BQuery=new JButton("查    询");
        panel3.add(BQuery,BorderLayout.SOUTH);
        BQuery.addActionListener(new QueryActionListener( ));
        tabbedPane.addTab("成绩查询", null, panel3, null);
        setLayout(new GridLayout(0, 1));
        add(tabbedPane);
    }
        class InActionListener implements ActionListener{	//事件处理------成绩输入事件服务
            public void actionPerformed(ActionEvent event){
               if(i<st.length){
                  st[i].SNumber=Integer.valueOf(TNumberIn.getText( )).intValue( );
                  st[i].Score=Integer.valueOf(TScoreIn.getText( )).intValue( );
               };
               i++;
            }
      }
      class SortActionListener implements ActionListener{	//成绩排序事件服务
            public void actionPerformed(ActionEvent event){
              int i,j,temp;
              String s=new String( );
              for(i=0;i<st.length-1;i++)
               for(j=i+1;j<st.length;j++){
                  if(st[i].Score<st[j].Score){
                    temp=st[i].Score;st[i].Score=st[j].Score;st[j].Score=temp;
                    temp=st[i].SNumber;st[i].SNumber=st[j].SNumber;st[j].SNumber=temp;
                  };};
              textArea.setText("学号        成绩\n");
              for(i=0;i<st.length;i++){
              if(st[i].SNumber!=0){
               textArea.append(s.valueOf(st[i].SNumber)+ "        "+s.valueOf(st[i].Score)+"\n");
              };};
            }
      }
      //成绩查询事件服务
      class QueryActionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
           int keyWord,i=0;
           String s=new String( );
           keyWord=Integer.valueOf(TNumberQuery.getText( )).intValue( );
           for(i=0;i<st.length;i++)
            if(st[i].SNumber==keyWord){
               TScoreQuery.setText(s.valueOf(st[i].Score));
               break;
            };
           if(i>=st.length)
               TScoreQuery.setText("未找到您的成绩");
        }
  }
      //主方法
      public static void main(String[ ] args) {
          JFrame frame = new JFrame("成绩处理");
          frame.addWindowListener(new WindowAdapter( ) {
              public void windowClosing(WindowEvent e) {System.exit(0);}
          });
          frame.getContentPane( ).add(new ScoreSort( ));
          frame.setSize(300,160);
          frame.setVisible(true);
      }
  }

