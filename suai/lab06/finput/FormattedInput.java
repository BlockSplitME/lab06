package suai.lab06.finput;
import suai.lab06.excpt.*;
import java.io.IOException;

public class FormattedInput{

	private static boolean ERRFLAG = false;
	private static final boolean ERROR = true;
	private static final char END = '.';

	private static class Input {
		
		private int code;
		private int len = 0;

		public String[] data;
		public int flag;
		
		Input(int len){
			data = new String[len];
			for (int i = 0; i < len; i++) {
				data[i] = "";
			}
			this.len = len;
			flag = 0;
		}
		private void flush(){
			if (flag == 1) return;

			try{
				while((char)(code = System.in.read()) != '\n');
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		public void input(){
			String b = "";
			code = 0;
			try{
				while((char)(code = System.in.read()) != '\n') {
					b += (char)code;
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			b=b.substring(0,b.length()-1);
			b+='\n';
			this.input(b);
		}
		public void input(String in){
			char c = 0;
			code = 0;
			int end = in.length()+1;
			for (int i = 0; i < len; i++) {
				while( (code+1) != end){
					c = in.charAt(code++);
					if((c != '\t') && (c != ' ') && (c != '\n') || (c == '\0')){
						break;
					}
				}
				if(c != '\0' || code == end){
					data[i] += c;
				} else {
					return;
				}
				while((code+1) != end ){
					c = in.charAt(code++);
					if((c == '\t') || (c == ' ') || (c == '\n') || (c == '\0')){
						break;
					}
					data[i] += c;
				}
				if(c == '\0' || code == end){
					return;
				}
				data[i].trim();
			}
		}
	}

	private static class Format {
		private int curFormatInd;
		private String format;
		Format(String format){
			this.format = format;
			curFormatInd = 0;
		}
		public boolean intChecker(String str){
			if(str.length() == 0) return false;
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if(!(((c >= '0') && (c <= '9')) || (c == '-'))){
					return false;
				}
				if(c == '-' && i != 0){
					return false;
				}
			}
			return true;
		}

		public boolean floatChecker(String str){
			if(str.length() == 0) return false;
			int pointCounter = 0;
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if(!(((c >= '0') && (c <= '9')) || (c == '-'))) {
					if(c != '.')
						return false;
					else
						pointCounter++;
				}
				if(c == '-' && i != 0){
					return false;
				}
			}
			return (pointCounter != 1) ? false : true;
		}
			
		public boolean charChecker(String str){
			return (str.length() != 1) ? false : true;
		}

		public int formatsCounter(){
			int counter = 0;
			for (int i = 0; i < format.length(); i++) {
				if(format.charAt(i) == '%'){
					if(i+1 != format.length()){
						counter++;
					} else {
						FormattedInputException e = 
			 			new FormattedInputException("Incorrect specification format: " + format + '\n'); 
			 			throw e;
					}
				}
			}
			return counter;
		}
		public char nextFormat(){
			curFormatInd = format.indexOf('%', curFormatInd);
			if(curFormatInd != -1){
				if(curFormatInd+1 < format.length()){
					if(curFormatInd+2 < format.length()){ //checking for the correctness of the variable type interpreter
						if(!((format.charAt(curFormatInd+2) == ' ') || (format.charAt(curFormatInd+2) == '\t') || (format.charAt(curFormatInd+2) == '%'))){
							FormattedInputException e = 
			 				new FormattedInputException("Incorrect specification format: " + format + '\n'); 
			 				throw e;
			 			}
					}
					curFormatInd++;
				} else {
					return END;
				}
				return format.charAt(curFormatInd);
			} else {
				return END;
			}
		}
		public Object formatedElement(char format, String str){
			Object item = str;
			switch(format){
				case 'd':
					if(intChecker(str)){
						item = Integer.parseInt(str);
					} else {
						
						FormattedInput.ERRFLAG = true;
					}
					break;
				case 'f':
					if(floatChecker(str)){
						item = Float.parseFloat(str);
					} else {
						
						FormattedInput.ERRFLAG = true;
					}
					break;
				case 's':
					break;
				case 'c':
					if(charChecker(str)){
						item = str.charAt(0);
					} else {
						FormattedInput.ERRFLAG = true;
					}
					break;
				case '%':
					FormattedInputException e = 
			 		new FormattedInputException("Incorrect specification format: " + format + '\n'); 
			 		throw e;
			}
			return item;
		}
	}
	private static final Object[] tryScanf(String format){
		format = format.trim();
		Format take = new Format(format);
		Object[] result = new Object[take.formatsCounter()];
		Input item = new Input(take.formatsCounter());
		item.input();
		for(int i = 0; ; i++){
			char form = take.nextFormat();
			if(form == END){
				break;
			} else {
				if(item.data[i] != null){
					result[i] = take.formatedElement(form, item.data[i]);
				}
			}
		} 
		return result;
	}

	private static final Object[] trySScanf(String format, String in){
		format = format.trim();
		in = in.trim();
		
		Format take = new Format(format);
		Object[] result = new Object[take.formatsCounter()];
		Input item = new Input(take.formatsCounter());

		item.input(in);
		for(int i = 0; ; i++){
			char form = take.nextFormat();
			if(form == END){
				break;
			} else {
				if(item.data[i] != null){
					result[i] = take.formatedElement(form, item.data[i]);
				}
			}
		} 
		return result;
	}
	public static final Object[] scanf(String format){
		Object[] result;
		do {
			if(ERRFLAG != ERROR)
				System.out.print("Please, input data: ");
			else
				System.out.print("[Invalid data] The data is differ from the specification: " + format + 
								"\nTry again: ");			
			ERRFLAG = !ERROR;
			result =  tryScanf(format);			
		} while(ERRFLAG == ERROR);
		return result;
	}
	public static final Object[] sscanf(String format, String in){
		Object[] result;
		ERRFLAG = !ERROR;
		result =  trySScanf(format, in);			
		if(ERRFLAG == ERROR) {	ERRFLAG = false;
		 						FormattedInputException e = 
		 						new FormattedInputException("Sscanf...\nThe data is differ from the specification\nIvalid data"); 
		 						throw e; }
		return result;
	}
}