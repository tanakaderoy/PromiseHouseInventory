import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.lang.Math;
public class randomReorder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  
		    int[] numbers = {1,0,3,9,2};
				 int n = numbers.length;
				 for(int i= n-1; i>=0; i--) {
					 int pick = Integer.valueOf((int) Math.floor((i+1 )* Math.random()));
					 int temp = numbers[i];
					 numbers[i] = numbers[pick];
					 numbers[pick] = temp;
				 }
				 System.out.println(Arrays.toString(numbers));
		  }
		

}
