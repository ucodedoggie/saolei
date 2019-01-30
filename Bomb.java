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


class Block extends JButton {      //�׿�block����
	public boolean isclicked=false;//isclickd�����Ƿ�㿪
	public int num=0;              //��Χ���׸���         ,���num=100;��������
	public int x=0,y=0;            //��������������е�λ�á�
}


public class Bomb extends JFrame implements MouseListener{
	int count=0;//��Ϸ�У��ҳ���������
	Block b[][]=new Block[10][10]; 
	int rand[]=new int[20];
	int leishu=10;                //���׵ĸ���
	boolean canclick=true;        //�Ƿ��ܵ㿪
	JPanel p=new JPanel();
	private ImageIcon blei=new ImageIcon("images/lei.png");//����ͼƬ
	private ImageIcon lei=new ImageIcon("images/ilei.png");//����ͼƬ
	
	public Bomb(){
		setTitle("ɨ��");
		setSize(500,500);
		setLocation(200,200);
		
		p.setLayout(new GridLayout(10,10));  //���񲼾�
		random();       //���ȷ������λ��
		initBlack();    ////��ʼ��100��Block
		zhilei();       //�����׽�Block���飬ȷ��ÿ��λ����Χ������	
		add(p);
		addWindowListener(new WindowAdapter(){  //�رմ���
			public void windowClosing(WindowEvent e){System.exit(0);}
		});
		setVisible(true);   //���ڿɼ�		
	}
	//��ʼ�����������
	private void random(){
		int k=0,i,x;           //�Ѿ����ɵ��׵ĸ���
		boolean bool=true;     //�����ɵ��׵�λ���Ƿ�����ǰ���ظ�
		Random r=new Random();  
		while(k<leishu){       //ֻҪ�׵ĸ�������
			x=r.nextInt(99);  //�����׵����λ��0-100
			
			for(i=0;i<k;i++){  //���Ѿ����ɵ����бȽ��Ƿ����ظ���
				if(x==rand[i]){ bool=false;   break; }          //����У�����					
			}
			if(bool)rand[k++]=x;//���û�У������ã��ŵ�������
		}
	}
	
	//��ʼ��Block
	private void initBlack(){
		int i,j;	
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				b[i][j]=new Block();              //��ʼ��100��Block��ť
				b[i][j].addMouseListener(this);   //Ϊÿһ��Blockע�������
				b[i][j].x=i;
				b[i][j].y=j;                      //��Block������Լ�������λ��
				p.add(b[i][j]);                   //��ӵ������
			}       
		}
	}
	
	//�����׽�Black����
	private void zhilei(){
		int x,y;
		for(int i=0;i<leishu;i++){
			x=rand[i]/10; y=rand[i]%10;     //ȡ�������е�һ���׵�λ�ã������С���ֵ
			b[x][y].num=100;                //��ʾ���black����
			if((x-1>=0)&&(b[x-1][y].num!=100)) b[x-1][y].num++;  
			if((x+1<10)&&(b[x+1][y].num!=100)) b[x+1][y].num++;
			if((y-1>=0)&&(b[x][y-1].num!=100)) b[x][y-1].num++;
			if((y+1<10)&&(b[x][y+1].num!=100)) b[x][y+1].num++;   //������8��λ�ò����������num+1��
			if((x-1>=0)&&(y-1>=0)&&(b[x-1][y-1].num!=100)) b[x-1][y-1].num++;
			if((x-1>=0)&&(y+1)<10&&(b[x-1][y+1].num!=100)) b[x-1][y+1].num++;
			if((x+1<10)&&(y-1)>=0&&(b[x+1][y-1].num!=100)) b[x+1][y-1].num++;
			if((x+1<10)&&(y+1)<10&&(b[x+1][y+1].num!=100)) b[x+1][y+1].num++;
		}
	}
	//������
	public static void main(String args[]){
		Bomb bomb=new Bomb();  //����������ʾ����		
	}
	
	
	public void click(int x,int y){      //�ݹ麯������Ƭ�Ĵ򿪿հ�����
		if(b[x][y].isclicked) return ;   //�����������Ѿ��򿪣����أ�
		else{                            //���û�㿪
			b[x][y].isclicked=true;                               //��ʶ�Ѿ��㿪��
			b[x][y].setBackground(Color.LIGHT_GRAY );	          //���ñ�����ɫ
			if (b[x][y].num!=0){                                   //������block��ֻ������
				b[x][y].setText(b[x][y].num+"");                      //��ʾ���֣�			
			}
			else{                                 //����ǿհ������ݹ���ã����ܱߵ�8��λ�õ���click����
			     if (x>0&&y>0) {click(x-1,y-1);}
			     if (x>0)  {click(x-1,y);}
			     if(x>0&&y<9){click(x-1,y+1);}
			     if(y>0) {click(x,y-1);}          //����֮ǰҪ�ж��Ƿ�Խ�磬
			     if(y<9) {click(x,y+1);}
			     if(x<9&&y>0){click(x+1,y-1);}
			     if(x<9){click(x+1,y);}
			     if(x<9&&y<9){click(x+1,y+1);}		
			}
		}
	}
	
	//ʵ������¼�
	@Override
	public void mouseClicked(MouseEvent e) {   //����¼���
		// TODO Auto-generated method stub
		Block temb=(Block)(e.getSource());//�õ��������block��
		
		if(e.getClickCount() == 2){ //���˫����
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
		     
			 if (n==temb.num){   //�����ǰ�������Ҽ�������׵ĸ���==�������num
				 temb.num=0;  temb.isclicked=false;  //���ø�����Ϊ�հ���������û�㿪����������ò���click������
				 click(x,y);     //�������ܵİ˸�����������
				 temb.num=n;  	 //	���½�numֵ��ֵ��
			}
		} 
		else{                       //����ǵ�����
			if (e.getButton()==e.BUTTON1){       //�����				
				if (temb.num!=100){	click(temb.x,temb.y);}//��������ף����õݹ麯������ʾ��Χ����
				else{temb.setIcon(blei);                  //������ף�ֱ����ʾ��Ϸ����
				JOptionPane.showMessageDialog(null,"Game Over!"); 
				}
			}	
			if (e.getButton()==e.BUTTON3){//�Ҽ�				
				if(!temb.isclicked){				   //���û�㿪				
					temb.setIcon(lei);                 //��ʾ��ͼƬ	
					temb.isclicked=true;	           //��ʶ�Ѿ����	
					count++;                           //�ų��׵ĸ���+1
					if (count==leishu){JOptionPane.showMessageDialog(null,"��ϲ�㣬�ɹ����أ�"); }
			    } 
			    else{	                               //������
			    	if(lei==temb.getIcon()){           //����������׵�ͼƬ�������޸ģ�
			    		temb.setIcon(null);                //�Ļؿ�
			    		temb.isclicked=false;		       //��ʶû�е��	
			    		count--;
			    	}
				}			
			}			
		}//����		
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
