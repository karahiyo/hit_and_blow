package yshimizu.acro.hitandblow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
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
	public static int DIGIT=10; // 回答の桁数
	public static int[] FINAL_ANSWER = new int[DIGIT]; // 正解
	
	private static final int INPUT = 0;
	private static final int GIVEUP = 1;

	public static void main(String[] args) {
		HitandBlow hb = new HitandBlow();
		hb.selectLevel();
		hb.randomSelect();
		
		System.out.println("!!START!! ---->>>>");
		for(int i=0;i<DIGIT;i++)
			System.out.print(FINAL_ANSWER[i]);
		System.out.print("\n");

		

		for(int i=0;i<MAX_TRIALS;i++) {
			System.out.printf("[phase %d]\n",i+1);
			
			System.out.print("[MENU]: (i)nput, (h)igh_and_low, (c)ue, (g)iveup ");
			Scanner sc = new Scanner(System.in);
			String menu;
			menu = sc.next();
			
			switch (menu) {
			case "i":
				if(hb.challenge() == 1)
					i=MAX_TRIALS;
				break;
			case "h":
				hb.highandLow();
				i--;
				break;
			case "c":
				hb.cue();
				i--;
				break;
			case "g":
				i=MAX_TRIALS;
				System.out.printf("\nYOUR LOSE..\n   >> ");
				break;
			default:
				System.out.printf("** Your command[%s] is not acceptable. Try again =>>\n",menu);
				i--;
				break;
			}
			
		}
		
		System.out.printf("ANSWER is ");
		for(int i=0;i<DIGIT;i++)
			System.out.printf("%d",FINAL_ANSWER[i]);

		System.out.println("\n>>>>---- !!FINISH!!");
	}
	
	public void selectLevel() {
		System.out.print("Select Level => ");
		Scanner sc = new Scanner(System.in);
		int level = sc.nextInt();
		DIGIT = level;
	}
	
	/**
	 * randomSelect(): 正解の値をランダムに生成する
	 */
	public void randomSelect() {
		System.out.print("randomSelect()\n");
		int fa[] = new int[DIGIT];
		boolean ok;
		Random rnd = new Random();
		for(int i=0;i<DIGIT;i++) {
			fa[i] = rnd.nextInt(10);
			do {
				ok = true;
				fa[i] = rnd.nextInt(10);
				for(int j=0;j<i;j++) {
					if(fa[i] == fa[j])
						ok = false;
				}
			} while(ok==false);
		}

		System.out.printf("DIGIT=%d\n",DIGIT);
		for(int i=0;i<DIGIT;i++) {
			System.out.printf("fa[%d]=%d\n",i,fa[i]);
			FINAL_ANSWER[i] = fa[i];
		}
		return;
	}
	
	/**
	 * challenge() : ユーザの入力を受け取り、正解と比較しHit数,Blow数を数える。
	 * @param 
	 * @return  int  1 --- Good job!! It's just a correct answer. 
	 * 			     0 --- nice challenge.
	 * 			    -1 --- bad challenge...
	 */
	public int challenge() {
		int ans[] = new int[DIGIT];
		
		
		System.out.printf("Please input %d numberes >> ",DIGIT);
		Scanner sc = new Scanner(System.in);
		sc.toString();
		
		String ans_pre_check;
		try {
			ans_pre_check = sc.next(); 
		} catch (InputMismatchException e) {
			e.printStackTrace();
			return -1;
		}
		
		if(ans_pre_check.length() != DIGIT) {
			System.out.print("** The length of the integer you entered is incorrect.\n");
			System.out.printf("**** ans.length = %d\n",ans_pre_check.length());
			return -1;
		}

		if(!ans_pre_check.matches("[0-9]+")){
			System.out.print("** Please enter an integer.\n");
			return -1;
		}
		
		for(int i=0;i<DIGIT;i++) {
			ans[i] = Integer.parseInt(ans_pre_check.substring(i,i+1));
		}
		
		for(int i=0;i<DIGIT-1;i++)
			for(int j=i+1;j<DIGIT;j++) {
				if(ans[i] == ans[j]) {
					System.out.printf("** Please enter a value that is different from %d integers.\n",DIGIT);
					return -1;
				}
			}
		
		return hb_check(ans);
		
	}
	
	/**
	 * high and low
	 */
	public void highandLow() {
		for(int i=0;i<DIGIT;i++) {
			if(FINAL_ANSWER[i] > 5)
				System.out.printf("[%d]HIGH ",i);
			else
				System.out.printf("[%d]Low ", i);
		}
		System.out.print("\n");
		return;
	}
	
	public void cue() {
		Scanner sc = new Scanner(System.in);
		int p;
		System.out.print("Which is the number you want to know? >>");
		p = sc.nextInt();
		System.out.printf("That is [%d].\n",FINAL_ANSWER[p]);
		return;
	}
	
	/**
	 *   ユーザの回答を正解の値と比較、Hit数Blow数の判定
	 * @param ans
	 * @return 
	 */
	public int hb_check(int[] ans) {
		boolean correct = true;
		for(int i=0;i<DIGIT;i++) 
			if (ans[i] != FINAL_ANSWER[i])
				correct = false;
		
				
		if(correct){
			System.out.println("\n正解です！！\n");
			return 1;
		} else {
			System.out.println("Hit: "+ hit_count(ans));
			System.out.println("Blow: "+ blow_count(ans));
			return 0;
		}
	}
	
	/**
	 *  Hit数チェック
	 * @param  ans ユーザ入力
	 * @return hits Hit数 
	 */
	public int hit_count(int[] ans) {
		int hits=0;
		for(int i=0;i < DIGIT; i++) {
			if( FINAL_ANSWER[i] == ans[i])
				hits++;
		}
		return hits;
	}
	
	/**
	 *  Blow数チェック
	 * @param ans ユーザ入力
	 * @return blows Blow数
	 */
	public int blow_count(int[] ans) {
		int blows=0;
		for(int i=0; i<DIGIT; i++)
			for(int j=0; j<DIGIT; j++) { 
				if(i!=j && FINAL_ANSWER[i] == ans[j])
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
		s = Integer.toString(num);
		int r = Integer.parseInt(s.substring(index,index+1));
		return r;
	}
}


