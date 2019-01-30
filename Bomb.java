package bomb;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class Block extends JButton {      //雷块block定义
	public boolean isclicked=false;//isclickd代表是否点开
	public int num=0;              //周围地雷个数         ,如果num=100;代表是雷
	public int x=0,y=0;            //这个雷区在数组中的位置。
}


public class Bomb extends JFrame implements MouseListener{
	int count=0;//游戏中，找出的雷数；
	Block b[][]=new Block[10][10]; 
	int rand[]=new int[20];
	int leishu=10;                //布雷的个数
	boolean canclick=true;        //是否能点开
	JPanel p=new JPanel();
	private ImageIcon blei=new ImageIcon("images/lei.png");//地雷图片
	private ImageIcon lei=new ImageIcon("images/ilei.png");//踩雷图片
	
	public Bomb(){
		setTitle("扫雷");
		setSize(500,500);
		setLocation(200,200);
		
		p.setLayout(new GridLayout(10,10));  //网格布局
		random();       //随机确定地雷位置
		initBlack();    ////初始化100个Block
		zhilei();       //放置雷进Block数组，确定每个位置周围的雷数	
		add(p);
		addWindowListener(new WindowAdapter(){  //关闭窗口
			public void windowClosing(WindowEvent e){System.exit(0);}
		});
		setVisible(true);   //窗口可见		
	}
	//初始化随机雷数组
	private void random(){
		int k=0,i,x;           //已经生成的雷的个数
		boolean bool=true;     //新生成的雷的位置是否与以前的重复
		Random r=new Random();  
		while(k<leishu){       //只要雷的个数不够
			x=r.nextInt(99);  //生成雷的随机位置0-100
			
			for(i=0;i<k;i++){  //到已经生成的雷中比较是否有重复的
				if(x==rand[i]){ bool=false;   break; }          //如果有，放弃					
			}
			if(bool)rand[k++]=x;//如果没有，可以用，放到数组中
		}
	}
	
	//初始化Block
	private void initBlack(){
		int i,j;	
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				b[i][j]=new Block();              //初始化100的Block按钮
				b[i][j].addMouseListener(this);   //为每一个Block注册监听器
				b[i][j].x=i;
				b[i][j].y=j;                      //该Block块记下自己的坐标位置
				p.add(b[i][j]);                   //添加到面板中
			}       
		}
	}
	
	//放置雷进Black数组
	private void zhilei(){
		int x,y;
		for(int i=0;i<leishu;i++){
			x=rand[i]/10; y=rand[i]%10;     //取出数组中的一个雷的位置，计算行、列值
			b[x][y].num=100;                //表示这个black是雷
			if((x-1>=0)&&(b[x-1][y].num!=100)) b[x-1][y].num++;  
			if((x+1<10)&&(b[x+1][y].num!=100)) b[x+1][y].num++;
			if((y-1>=0)&&(b[x][y-1].num!=100)) b[x][y-1].num++;
			if((y+1<10)&&(b[x][y+1].num!=100)) b[x][y+1].num++;   //将四周8个位置不是雷区域的num+1；
			if((x-1>=0)&&(y-1>=0)&&(b[x-1][y-1].num!=100)) b[x-1][y-1].num++;
			if((x-1>=0)&&(y+1)<10&&(b[x-1][y+1].num!=100)) b[x-1][y+1].num++;
			if((x+1<10)&&(y-1)>=0&&(b[x+1][y-1].num!=100)) b[x+1][y-1].num++;
			if((x+1<10)&&(y+1)<10&&(b[x+1][y+1].num!=100)) b[x+1][y+1].num++;
		}
	}
	//主函数
	public static void main(String args[]){
		Bomb bomb=new Bomb();  //创建对象，显示界面		
	}
	
	
	public void click(int x,int y){      //递归函数，成片的打开空白区域
		if(b[x][y].isclicked) return ;   //如果这个区域已经打开，返回；
		else{                            //如果没点开
			b[x][y].isclicked=true;                               //标识已经点开；
			b[x][y].setBackground(Color.LIGHT_GRAY );	          //设置背景颜色
			if (b[x][y].num!=0){                                   //如果这个block块只是数字
				b[x][y].setText(b[x][y].num+"");                      //显示数字；			
			}
			else{                                 //如果是空白区，递归调用，将周边的8个位置调用click函数
			     if (x>0&&y>0) {click(x-1,y-1);}
			     if (x>0)  {click(x-1,y);}
			     if(x>0&&y<9){click(x-1,y+1);}
			     if(y>0) {click(x,y-1);}          //调用之前要判定是否越界，
			     if(y<9) {click(x,y+1);}
			     if(x<9&&y>0){click(x+1,y-1);}
			     if(x<9){click(x+1,y);}
			     if(x<9&&y<9){click(x+1,y+1);}		
			}
		}
	}
	
	//实现鼠标事件
	@Override
	public void mouseClicked(MouseEvent e) {   //点击事件；
		// TODO Auto-generated method stub
		Block temb=(Block)(e.getSource());//得到被点击的block块
		
		if(e.getClickCount() == 2){ //如果双击；
			int n=0;
			int x=temb.x,y=temb.y;
			 if (x>0&&y>0) {if((b[x-1][y-1].isclicked)&&(b[x-1][y-1].num==100))n++;}
		     if (x>0)  {if((b[x-1][y].isclicked)&&(b[x-1][y].num==100))n++;}
		     if(x>0&&y<9){if((b[x-1][y+1].isclicked)&&(b[x-1][y+1].num==100))n++;}
		     if(y>0) {if((b[x][y-1].isclicked)&&(b[x][y-1].num==100))n++;}
		     if(y<9) {if((b[x][y+1].isclicked)&&(b[x][y+1].num==100))n++;}
		     if(x<9&&y>0){if((b[x+1][y-1].isclicked)&&(b[x+1][y-1].num==100))n++;}
		     if(x<9){if((b[x+1][y].isclicked)&&(b[x+1][y].num==100))n++;}
		     if(x<9&&y<9){if((b[x+1][y+1].isclicked)&&(b[x+1][y+1].num==100))n++;}	
		     
			 if (n==temb.num){   //如果当前四周用右键点击的雷的个数==该区域的num
				 temb.num=0;  temb.isclicked=false;  //设置该区域为空白区，并且没点开过。否则调用不了click函数；
				 click(x,y);     //将这四周的八个区域点击开；
				 temb.num=n;  	 //	重新将num值赋值；
			}
		} 
		else{                       //如果是单击；
			if (e.getButton()==e.BUTTON1){       //左键，				
				if (temb.num!=100){	click(temb.x,temb.y);}//如果不是雷，调用递归函数，显示周围数字
				else{temb.setIcon(blei);                  //如果是雷，直接提示游戏结束
				JOptionPane.showMessageDialog(null,"Game Over!"); 
				}
			}	
			if (e.getButton()==e.BUTTON3){//右键				
				if(!temb.isclicked){				   //如果没点开				
					temb.setIcon(lei);                 //显示雷图片	
					temb.isclicked=true;	           //标识已经点过	
					count++;                           //排出雷的个数+1
					if (count==leishu){JOptionPane.showMessageDialog(null,"恭喜你，成功过关！"); }
			    } 
			    else{	                               //如果点过
			    	if(lei==temb.getIcon()){           //如果上面有雷的图片，才能修改；
			    		temb.setIcon(null);                //改回空
			    		temb.isclicked=false;		       //标识没有点过	
			    		count--;
			    	}
				}			
			}			
		}//单击		
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}	
}
