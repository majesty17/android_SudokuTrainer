package com.majesty.sudokutrainer;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;

public class NakedActivity extends Activity {

	
	TableLayout naked_tl_table=null;
	TableRow[] naked_tr_row=null;
	
	
	Button[] naked_btn_show=null;
	Button[] naked_btn_put=null;
	Button naked_btn_next=null;
	Button naked_btn_reset=null;
			
	
	int cross_column=-1;	//����1~9
	int cross_row=-1;		//����1~9
	int cross_unit=-1;		//�ڼ���1~9
	
	//������ֵ�
	int[] nums=null;
	//ģʽ��1=��17��    2=����21��
	int play_mode=1;
	int lost_num=-1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_naked);
		
		
		//�Ѹû��Ŀؼ�������
		naked_tl_table=(TableLayout)findViewById(R.id.naked_tl_table);
		naked_tr_row=new TableRow[9];
		naked_btn_show=new Button[81];			
		for(int i=0;i<9;i++){
			TableLayout.LayoutParams tl_lp=new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tl_lp.setMargins(0,-dip2px(NakedActivity.this,4),0,0);
			

			naked_tr_row[i]=new TableRow(this);
			naked_tr_row[i].setLayoutParams(tl_lp);
			naked_tr_row[i].setGravity(Gravity.CENTER);
			
			naked_tl_table.addView(naked_tr_row[i]);
			for(int j=0;j<9;j++){
				int tmp=9*i+j;
				naked_btn_show[tmp]=new Button(this);
				TableRow.LayoutParams tr_lp=new TableRow.LayoutParams(dip2px(NakedActivity.this,32), dip2px(NakedActivity.this,32));
				tr_lp.setMargins(-dip2px(NakedActivity.this,4), 0, -dip2px(NakedActivity.this,4), -dip2px(NakedActivity.this,4));
				
				naked_btn_show[tmp].setLayoutParams(tr_lp);
				naked_btn_show[tmp].setEnabled(false);
				naked_btn_show[tmp].setTextSize(sp2px(NakedActivity.this,7));
				naked_btn_show[tmp].setText((j)+"");
				naked_btn_show[tmp].setPadding(0,0,0,0);
				naked_tr_row[i].addView(naked_btn_show[tmp]);
			}
		}
		
		
		//��ʼ������
		
		nums=new int[21];
		
		lost_num=init_show();
		if(lost_num<1 || lost_num>9){
			System.out.println("quit at init:lost num is "+lost_num);
			System.exit(lost_num);
		}
		
		
		//����ť�¼� TODO
		
		
		
		
		naked_btn_next=(Button)findViewById(R.id.naked_btn_next);
		naked_btn_next.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lost_num=init_show();
				if(lost_num<1 || lost_num>9){
					System.out.println("quit at Next:lost num is "+lost_num);
					System.exit(lost_num);
				}
			}
		});
		
		
		
		
		
		
		
		
		
		
	}
	
	public int init_show(){
		System.out.println("=======================");
		System.out.println("in init_show()");
		int tmp[]={1,2,3,4,5,6,7,8,9};


		Random ra=new Random();
		int lost=ra.nextInt(9)+1;
		tmp[lost-1]=-1;
		//����
		System.out.println("before scamble");
		for(int i=0;i<100;i++){
			int start=ra.nextInt(9);
			int end=ra.nextInt(9);
			
			int t=tmp[start];
			tmp[start]=tmp[end];
			tmp[end]=t;			
		}
		System.out.println("after scamble");
		//�����ҽ����
		for(int i=0;i<9;i++){
			System.out.print(tmp[i]+",");
		}
		
		System.out.println("after check");
		if(nums==null){
			return -1;
		}
		//���nums
		for(int i=0;i<21;i++){
			nums[i]=0;
		}
		System.out.println("after clear nums[]");
		//��9�����ָ�21��λ��
		for(int i=0;i<9;i++){
			int ind=ra.nextInt(21);
			while(true){
				if(nums[ind]!=0){
					ind=ra.nextInt(21);
				}
				else{
					nums[ind]=tmp[i];
					break;
				}

			}
		}

		
		for(int i=0;i<21;i++){
			System.out.print(nums[i]+",");
		}
		System.out.println("after distribute 9 to 21");
		cross_column=ra.nextInt(9)+1;
		cross_row=ra.nextInt(9)+1;
		cross_unit=(cross_row-1)/3*3+(cross_column-1)/3+1;
		
		//��-1�ŵ�������???��
		int pos_lost=0;
		while(true){
			if(nums[pos_lost]==-1){
				break;
			}
			else{
				pos_lost++;
				continue;
			}
		}
		int pos_mag=((cross_row-1)%3)*3+(cross_column-1)%3;
		nums[pos_lost]=nums[21-(9-pos_mag)];
		nums[21-(9-pos_mag)]=-1;
		
		System.out.println("pos_mag is "+pos_mag);
		
		
		//�������button
		
		if(naked_btn_show==null){
			return-2;
		}
		for(int i=0;i<81;i++){
			naked_btn_show[i].setText("");;
		}
		System.out.println("cross_column is "+cross_column+"\ncross_row is "+cross_row+"\ncross_unit is "+cross_unit);
		
		//д��button,��5��
		//1,-�ŵ����
		for(int i=0;i<(cross_column-1)/3*3;i++){
			if(nums[i]!=0)
				naked_btn_show[(cross_row-1)*9+i].setText(nums[i]+"");
		}
		//2,-�ŵ��ұ�
		for(int i=(cross_column-1+3)/3*3;i<9;i++){
			if(nums[i-3]!=0)
				naked_btn_show[(cross_row-1)*9+i].setText(nums[i-3]+"");
		}
		//3,|�ŵ����
		for(int i=0;i<(cross_row-1)/3*3;i++){
			if(nums[i+6]!=0)
				naked_btn_show[(i)*9+cross_column-1].setText(nums[i+6]+"");
		}
		//4,|�ŵ��ұ�
		for(int i=(cross_row-1+3)/3*3;i<9;i++){
			if(nums[i+6-3]!=0)
				naked_btn_show[(i)*9+cross_column-1].setText(nums[i+6-3]+"");
		}
		//5,���һ���
		int k=12;
		for(int i=(cross_row-1)/3*3;i<(cross_row-1+3)/3*3;i++){
			for(int j=(cross_column-1)/3*3;j<(cross_column-1+3)/3*3;j++){
				if(nums[k]>0)
					naked_btn_show[9*i+j].setText(nums[k]+"");
				k++;
			}
		}
		
		//�����м������
		naked_btn_show[(cross_row-1)*9+cross_column-1].setText("?");
		
		
		
		//�ͷ���Դ
		
		return lost;
	}
	
	
	
	
   public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
   public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    } 
   public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
   public static int sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  
}
