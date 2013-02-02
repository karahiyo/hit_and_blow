package yshimizu.acro.hitandblow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * Acroquest Programming Test 
 *      ~ phase 1 ~
 *
 *      @author yshimizu
 */
public class HitandBlow {

	private static final int MAX_TRIALS = 10; // 最大試行可能回数
	public static final int[] FINAL_ANSWER = new int[3]; // 正解
	public static final int DIGIT = 3; // 回答の桁数

	public static void main(String[] args) {
		System.out.println("!!START!! ---->>>>");
		
		HitandBlow hb = new HitandBlow();
		
		hb.randomSelect();

		for(int i=0;i<MAX_TRIALS;i++) {
			System.out.printf("[phase %d]\n",i+1);
			hb.challenge();
		}
		
		System.out.printf("ANSWER is %d%d%d\n", FINAL_ANSWER[0],FINAL_ANSWER[1],FINAL_ANSWER[2]);

		System.out.println(">>>>---- !!FINISH!!");
	}
	
	/**
	 * randomSelect()
	 */
	public void randomSelect() {
		int fa[] = {0,0,0};
		Random rnd = new Random();
		fa[0] = rnd.nextInt(10);
		do {
			fa[1] = rnd.nextInt(10);
		} while(fa[0] == fa[1]);
		do {
			fa[2] = rnd.nextInt(10);
		} while(fa[2]==fa[1] || fa[1]==fa[0]);
		
		for(int i=0;i<3;i++)
			FINAL_ANSWER[i] = fa[i];
		return;
	}
	
	/**
	 * マッチングフェーズ
	 * @return
	 */
	public boolean challenge() {
		String input_str;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input Three numberes >> ");
			input_str = br.readLine();
		} catch (IOException e) {
			e.getStackTrace();
			return false;
		}
		
		if (!input_str.matches("[0-9]{3}")) {
			System.out.print("** It is inaccurate input data. Please input the integer of triple figures.\n");
			return false;
		}
		
		int ans = Integer.parseInt(input_str);
		//char ans[] = input_str.toCharArray();
		//hb.hit_count();
		
		hb_check(ans);
		return true;
		
	}
	
	/**
	 *   ユーザの回答を正解の値と比較、Hit数Blow数の判定
	 * @param ans
	 * @return 
	 */
	public boolean hb_check(int ans) {
		boolean ok = true;
		for(int i=0;i<3;i++) 
			if (intAt(ans,i) != FINAL_ANSWER[i])
				ok = false;
		
				
		if(ok){
			System.out.println("\n正解です！！\n");
			return true;
		} else {
			System.out.println("Hit: "+ hit_count(ans));
			System.out.println("Blow: "+ blow_count(ans));
			return false;
		}
	}
	
	/**
	 *  Hit数チェック
	 * @param  ans ユーザ入力
	 * @return hits Hit数 
	 */
	public int hit_count(int ans) {
		int hits=0;
		for(int i=0;i < DIGIT;i++) {
			if( FINAL_ANSWER[i] == intAt(ans,i))
				hits++;
		}
		return hits;
	}
	
	/**
	 *  Blow数チェック
	 * @param ans ユーザ入力
	 * @return blows Blow数
	 */
	public int blow_count(int ans) {
		int blows=0;
		for(int i=0; i<DIGIT; i++)
			for(int j=0; j<DIGIT; j++) { 
				if(i!=j && FINAL_ANSWER[i] == intAt(ans, j))
					blows++;
			}
		return blows;
	}
	
	/** 
	 * intAt: ユーザ入力の数値や、正解の値の？桁目の値を返す関数
	 *    
	 *    @param  num   ユーザ入力の数値、正解の値
	 *    		  index 位数
	 *    @return num の index 桁目の値
	 */
	public int intAt(int num, int index)
	{
		String s;
		if(num < 100) {
			if(index == 0) return 0; //百の桁がゼロの場合は別に処理
			s = Integer.toString(num);
			int r = Integer.parseInt(s.substring(index-1,index));
			return r;
		} else {
			s = Integer.toString(num);
			int r = Integer.parseInt(s.substring(index, index+1));
			return r;
		}
	}
}


