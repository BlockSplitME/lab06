package suai.lab06;
import suai.lab06.excpt.*;
import suai.lab06.encodconv.*;
import suai.lab06.finput.*;
import java.io.IOException;

public class Main{
	public static void main(String[] args){			
		try{
			
			System.out.println("\n------------------------FormattedInput check------------------------\n");
			
			String format = "%s %f %d %c";
			String in = "GG -0.86 -23423 s";
			Object[] test1 = FormattedInput.sscanf(format, in);
			System.out.println("" + test1[0] + ", " + test1[1] + ", " + test1[2] + ", " + test1[3]);

			Object[] test2 = FormattedInput.scanf(format);
			// System.out.println("" + test2[0] + ", " + test2[1] + ", " + test2[2]);
			String t1 = (String)test2[0];
			float t2 = (float)test2[1];
			int t3 = (int)test2[2];
			char t4 = (char)test2[3];
			System.out.println("" + t1 + ", " + t2 + ", " + t3 + ", " + t4);

			
			System.out.println("\n------------------------EncodingConverter check------------------------\n");

			if(args.length != 4){
				System.out.println("Invalid input data");
				return;
			} else {
				try{
					EncodingConverter.converter(args[0], args[1], args[2], args[3]);
				} catch(IOException err){
					err.printStackTrace();
				}
			}

		} catch(FormattedInputException err){
			err.getMassage();
			err.printStackTrace();
		} catch(EncodingConverterException err){
			err.getMassage();
			err.printStackTrace();
		}
	}
}