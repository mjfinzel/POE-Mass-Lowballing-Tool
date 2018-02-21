import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static Robot robot = null;
	static String[] insults = {"Fuckstick", "Cockmuppet", "Assclown", "Douchemonger", "Twat", "Mouth-breather", "Cockshiner", "Cheesedick", "Fuckface", "Knuckle-dragger", "Shitstick", "Tool", "Shitbag", "Carpet-cleaner", "Asshat", "Slutbag", "Numbnuts", "Dicknose", "Sleezebag", "Buttmunch", "Twatwaffle", "Tard", "Shitstain", "Dickbreath", "Cockgobbler", "Douchenozzle", "Pigfucker", "Butknuckler", "Clitsplitter", "Shitshaker", "Douche canoe", "Fuckrag", "Rumpranger", "Cock-juggling thundercunt", "Ass fiddler", "Butt monkey", "Fat lard", "Inbreeder", "Boogerface", "Ballsack", "Poo-poo head", "Village idiot", "Bozo", "Wanker", "Weirdo", "Porker", "Fatso", "Geezer", "Wuss", "Fucktard"};
	static String[] adverbs = {"antiquated", "rambunctious", "mundane", "misshapen", "dreary", "dopey", "clammy", "brazen", "indiscreet", "imbecilic", "dysfunctional", "drunken", "dismal", "deficient", "daft", "asinine", "morbid", "repugnant", "unkempt", "decrepit", "impertinent", "grotesque"};
	static String item = "\"The Wretched\"";
	static String price = "3 chaos (per card)";
	static int amountToWhisperAtATime = 5;
	public static void main(String[] args) {
		if(args.length==0) {
			boolean correct = false;
			Scanner input = new Scanner(System.in);
			while(!correct) {
				System.out.println("");
				System.out.println("Enter the name of the item you want to buy:");
				item = "\""+input.nextLine()+"\"";
				
				System.out.println("Enter the price you are willing to pay:");
				price = input.nextLine();
				
				System.out.println("How many people do you want to whisper at a time?");
				amountToWhisperAtATime = Integer.valueOf(input.nextLine());
				
				System.out.println("");
				System.out.println("Item name:                       "+item);
				System.out.println("Your offer:                      "+price);
				System.out.println("# of whispers at once:           "+amountToWhisperAtATime);
				System.out.println("Is this correct? (y/n)");
				
				String inp;
				inp = input.nextLine();
				
				if(inp.equalsIgnoreCase("y")||inp.equalsIgnoreCase("yes")) correct=true;
			}
			
		}
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String data = getClipboardData();
		ArrayList<String> names = new ArrayList<String>();
		
		String lines[] = data.split("\\r?\\n");
		for(int i = 0; i<lines.length;i++) {
			if(lines[i].contains("IGN: ")) {
				//System.out.println(lines[i]);
				int begin = "    online IGN: ".length();
				int end = lines[i].length()-" Profile Whisper ".length();
				if(lines[i].contains("    IGN: ")) begin = "    IGN: ".length();
				if(lines[i].contains("AFK IGN")) begin = "    AFK IGN: ".length();
				if(lines[i].contains("Verify online IGN: ")) begin = "    Verify online IGN: ".length();
				if(lines[i].contains("Copied to clipboard")) end = lines[i].length()-" Profile Copied to clipboard".length();
				
				String name = lines[i].substring(begin, end);
				if(!names.contains(name)) {
					names.add(name);
				}
			}
		}
		System.out.println("Whispering "+names.size() + " people.");
		System.out.println("Starting in:");
		//wait for 5 seconds
		wait(10);
		
		Scanner in = new Scanner(System.in);
		for(int i = 0; i<names.size();i++) {
			if(i%amountToWhisperAtATime==0&&i>0) {
				System.out.println(i+"/"+names.size()+" messages sent, Continue?");
				String cmd = in.nextLine();
				if(cmd=="no"||cmd=="n"||cmd=="No") break;
				//wait for 5 seconds
				wait(3);
			}
			activate.makeActive("Path of Exile");
			//String item = "\""+ "Shrieking Essence of Torment" +"\"";
			//String price = "3 chaos each";
			//String message = "@"+names.get(i)+" I'll pay "+price+" per "+item+". If you're interested in selling them :)";
			//String message = "@"+names.get(i)+" I'll pay "+price+" each for your "+item+".";
			String message = "@"+names.get(i)+" "+"I'm interested in buying all of your "+item+" for "+price+" in Abyss League.";
			//String message = "@"+name+" "+"Would you be willing to help a girl out and sell me your "+item+" for "+price+" orbs? I'm new to the game and that's all I found so far after converting everything :)";
			//String message = "@"+names.get(i)+" "+"Sell me your "+item+" for "+price+" please. :)";
			//String message = "@"+names.get(i)+" Hey "+insult()+". Sell me your "+ item +" for " + price + " you "+adverb()+" "+insult();
			//String message = "@"+name+" "+"sell me your "+item+" for " +price+" or I will call the gestapo on you.";

			//sendMessage("Hello");\\\
			//System.out.println(message);
			sendMessage(message);
		}
		System.out.println("Finished sending messages!");
		
	}
	public static void sendMessage(String msg) {
		addToClipboard(msg);
		useKey(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		sleep(25, 45);
		useKey(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		useKey(KeyEvent.VK_ENTER);
		System.out.println(msg);
		sleep(207,303);
	}
	public static String insult() {
		return insults[randomNumber(0,insults.length-1)];
	}
	public static String adverb() {
		return adverbs[randomNumber(0,adverbs.length-1)];
	}
	public static void wait(int seconds) {
		for(int i = seconds; i>=1;i--) {
			System.out.println(i);
			sleep(700,700);
		}
	}
	static int randomNumber(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	public static void useKey(int k) {
			sleep(18, 32);
			robot.keyPress(k);
			sleep(18, 32);
			robot.keyRelease(k);
			sleep(180, 320);
	}
	public static void sleep(int min, int max){
		try {
			Thread.sleep(randomNumber(min, max));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void addToClipboard(String s) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(s);
		clipboard.setContents(strSel, null);
	}
	public static String getClipboardData() {
		String data;
		try {
			data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			return data;
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		return null;
	}
}
