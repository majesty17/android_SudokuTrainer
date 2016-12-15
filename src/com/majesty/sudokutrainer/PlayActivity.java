package com.majesty.sudokutrainer;

import java.text.DecimalFormat;
import java.util.Random;

import android.app.Activity;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {

	Button[] btn_show=null;
	Button[] btn_put=null;
	
	Button btn_next=null;
	Button btn_reset=null;
	
	
	TextView tv_info=null;
	
	int lost_num=0;
	
	int[] num=null;//{1,2,3,4,5,6,7,8,9};
	
	//��¼�ܴ�����ÿ��ʱ�䣬��ȷ����
	int cnt_total=0;
	int cnt_right=0;
	int cnt_wrong=0;
	int cnt_skip=0;
	
	//TODO ����ʱ��
	//List time_list=null;
	
	
	//radioGroup decide the mode
	RadioGroup play_rg_mode=null;
	//1:box       2:row        3:column       4:random
	static int play_mode=1;
	
	//������
	private Vibrator vibrator=null;
	
	
	//������������
	LinearLayout play_ll_01=null;
	LinearLayout play_ll_02=null;
	LinearLayout play_ll_03=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		//��ʼ����btn_showϵ�а�ť��layout
		play_ll_01=(LinearLayout)findViewById(R.id.play_ll_01);
		play_ll_02=(LinearLayout)findViewById(R.id.play_ll_02);
		play_ll_03=(LinearLayout)findViewById(R.id.play_ll_03);
		
		//��ʼ�����а�ť
		btn_show=new Button[9];
		btn_put=new Button[9];
		btn_show[0]=(Button)findViewById(R.id.play_btn_show01);
		btn_show[1]=(Button)findViewById(R.id.play_btn_show02);
		btn_show[2]=(Button)findViewById(R.id.play_btn_show03);
		btn_show[3]=(Button)findViewById(R.id.play_btn_show04);
		btn_show[4]=(Button)findViewById(R.id.play_btn_show05);
		btn_show[5]=(Button)findViewById(R.id.play_btn_show06);
		btn_show[6]=(Button)findViewById(R.id.play_btn_show07);
		btn_show[7]=(Button)findViewById(R.id.play_btn_show08);
		btn_show[8]=(Button)findViewById(R.id.play_btn_show09);
		for(int i=0;i<9;i++){
			btn_show[i].setText("");
			btn_show[i].setEnabled(false);
		}
		
		btn_put[0]=(Button)findViewById(R.id.play_btn_put01);
		btn_put[1]=(Button)findViewById(R.id.play_btn_put02);
		btn_put[2]=(Button)findViewById(R.id.play_btn_put03);
		btn_put[3]=(Button)findViewById(R.id.play_btn_put04);
		btn_put[4]=(Button)findViewById(R.id.play_btn_put05);
		btn_put[5]=(Button)findViewById(R.id.play_btn_put06);
		btn_put[6]=(Button)findViewById(R.id.play_btn_put07);
		btn_put[7]=(Button)findViewById(R.id.play_btn_put08);
		btn_put[8]=(Button)findViewById(R.id.play_btn_put09);
		
		for(int i=0;i<9;i++){
			btn_put[i].setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					//�����ť
					Button btn=(Button)arg0;
					
					//�ҵ��������ĸ���ť
					int ind=-1;//=btn_put.
					for(int j=0;j<9;j++){
						if(btn_put[j].hashCode()==btn.hashCode()){
							ind=j+1;
							break;
						}
					}
					//��ind����ť����������Ҫ+1��������

					
					
					if((ind)==lost_num){
						Toast.makeText(PlayActivity.this, "OK!!!!!!", Toast.LENGTH_SHORT).show();
						tv_info.setTextColor(Color.GREEN);
						lost_num=init_show();
						cnt_right++;
					}
					else{
						Toast.makeText(PlayActivity.this, "ERROR!!!!!!", Toast.LENGTH_SHORT).show();
						//��Դ�������
						tv_info.setTextColor(Color.RED);
						lost_num=init_show();
						cnt_wrong++;
						vib();
					}
					cnt_total++;
					update_info();
					if(lost_num<=0)
						System.exit(-3);
				}
				
			});
		}
		
		
		//��һ��
		btn_next=(Button)findViewById(R.id.play_btn_next);
		btn_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//next ��ť�¼�
				lost_num=init_show();
				if(lost_num<=0)
					System.exit(-2);
				tv_info.setTextColor(Color.GRAY);
				cnt_total++;
				cnt_skip++;
				update_info();
			}
			
		});
		
		
		//��λ
		btn_reset=(Button)findViewById(R.id.play_btn_reset);
		btn_reset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				cnt_total=0;
				cnt_right=0;
				cnt_wrong=0;
				cnt_skip=0;
				update_info();
				tv_info.setTextColor(Color.BLACK);
			}
		});
		
		//����

		
		System.out.println("before init!");
		//��ʼ������
		num=new int[9];
		lost_num=init_show();
		if(lost_num<=0)
			System.exit(-1);
		
		
		
		
		//��ʼ��tv_info
		tv_info=(TextView)findViewById(R.id.play_tv_info);
		update_info();
		
		
		
		//��ʼ��vib
		vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
	
		
		//��ʼ��radiogroup ����ֵ��ʼmode
		play_mode=1;
		play_rg_mode=(RadioGroup)findViewById(R.id.play_rg_mode);
		play_rg_mode.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				int radioButtonId = arg0.getCheckedRadioButtonId();
				if(radioButtonId==((RadioButton)findViewById(R.id.play_rb_box)).getId())
					play_mode=1;
				if(radioButtonId==((RadioButton)findViewById(R.id.play_rb_row)).getId())
					play_mode=2;
				if(radioButtonId==((RadioButton)findViewById(R.id.play_rb_column)).getId())
					play_mode=3;
				if(radioButtonId==((RadioButton)findViewById(R.id.play_rb_random)).getId())
					play_mode=4;
					
			}
		});
	}
	
	public int init_show(){
		//���¸�ֵ����1~9
		for(int i=0;i<9;i++){
			num[i]=i+1;
		}
		//���ȥ��һ��
		
		Random r=new Random();
		int lost_num=r.nextInt(9)+1;
		System.out.println(num.length+"��lost num is"+lost_num);
		num[lost_num-1]=0;       //������
		System.out.println("before ran btn");
		//�������
		for(int i=0;i<100;i++){
			int start=r.nextInt(9);
			int end=r.nextInt(9);
			
			int tmp=num[start];
			num[start]=num[end];
			num[end]=tmp;
		}
		System.out.println("after ����");
		
		//TODO canvas����ok֮�� ������µ�      ��button��ֵ
		for(int i=0;i<9;i++){
			btn_show[i].setTextColor(Color.BLACK);
			if(num[i]>0)
				btn_show[i].setText(num[i]+"");
			else{
				btn_show[i].setText("?");
				btn_show[i].setTextColor(Color.RED);
			}
		}
		
		//TODO canvas����

		//
		int tmp_mode=play_mode;
		if(tmp_mode==4){
			Random ra=new Random();
			tmp_mode=ra.nextInt(3)+1;
		}
		
		
		play_ll_01.removeAllViews();
		play_ll_02.removeAllViews();
		play_ll_03.removeAllViews();
		switch(tmp_mode)
		{
		case 1:
			play_ll_01.setOrientation(LinearLayout.HORIZONTAL);
			play_ll_01.addView(btn_show[0]);
			play_ll_01.addView(btn_show[1]);
			play_ll_01.addView(btn_show[2]);
			play_ll_02.addView(btn_show[3]);
			play_ll_02.addView(btn_show[4]);
			play_ll_02.addView(btn_show[5]);
			play_ll_03.addView(btn_show[6]);
			play_ll_03.addView(btn_show[7]);
			play_ll_03.addView(btn_show[8]);
			break;
		case 2:
			play_ll_01.setOrientation(LinearLayout.HORIZONTAL);
			play_ll_01.addView(btn_show[0]);
			play_ll_01.addView(btn_show[1]);
			play_ll_01.addView(btn_show[2]);
			play_ll_01.addView(btn_show[3]);
			play_ll_01.addView(btn_show[4]);
			play_ll_01.addView(btn_show[5]);
			play_ll_01.addView(btn_show[6]);
			play_ll_01.addView(btn_show[7]);
			play_ll_01.addView(btn_show[8]);
			break;
		case 3:
			play_ll_01.setOrientation(LinearLayout.VERTICAL);
			play_ll_01.addView(btn_show[0]);
			play_ll_01.addView(btn_show[1]);
			play_ll_01.addView(btn_show[2]);
			play_ll_01.addView(btn_show[3]);
			play_ll_01.addView(btn_show[4]);
			play_ll_01.addView(btn_show[5]);
			play_ll_01.addView(btn_show[6]);
			play_ll_01.addView(btn_show[7]);
			play_ll_01.addView(btn_show[8]);			
			break;
		default:
			return -1;
		}
		
		
		return lost_num;
	}
	public void update_info(){
		double r_rate=((double)cnt_right/(double)cnt_total)*100.0;
		DecimalFormat df = new DecimalFormat("0.00");
		
		tv_info.setText("��ȷ["+cnt_right+"] ����["+cnt_wrong+"] ����["+cnt_skip+"] ����TOT["+cnt_total+"] ��ȷ��["+df.format(r_rate)+"%]");
	}
	public void vib(){
		//������200ms
		vibrator.vibrate(new long[]{0,200}, -1);
	}
}
