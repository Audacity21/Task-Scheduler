import java.util.*;
import java.io.*;
import java.net.*;

class List implements Comparable<List> {
	String title, priority;
	public List(String p, String t) {
		priority=p;
		title=t;
	}
	public int compareTo(List l)
	{
		if((this.priority.compareTo(l.priority))>0)
			return 1;
		else
			return -1;
	}
}
public class Task {
	public static ArrayList<List> tasks = new ArrayList<List>();
	public static ArrayList<List> complete = new ArrayList<List>();
	public static void main(String args[]) throws FileNotFoundException {
		readFile();
		writeFile();
		String s="";
		if(args.length>0)
			s=args[0];

		switch(s) {
		case "ls": display();
		break;
		case "add": 
			try {
				add(args[1], args[2]);
			}
			catch(Exception e) {
				System.out.println("Please add both priority and title at the end of add");
			}
		break;
		case "del":
			try {
				delete(Integer.parseInt(args[1]));
			}
			catch(Exception e) {
				System.out.println("Please add index at the end of del");
			}
		break;
		case "done":
			try {
				done(Integer.parseInt(args[1]));
			}
			catch(Exception e) {
				System.out.println("Please add index at the end of done");
			}
		break;
		case "report": report();
		break;
		default: System.out.println("Usage :-\n$ ./task add 2 hello world    # Add a new item with priority 2 and text \"hello world\" to the list\n$ ./task ls                   # Show incomplete priority list items sorted by priority in ascending order\n$ ./task del INDEX            # Delete the incomplete item with the given index\n$ ./task done INDEX           # Mark the incomplete item with the given index as complete\n$ ./task help                 # Show usage\n$ ./task report               # Statistics");
		}
	}
	public static void display() {
		int i=0;
		for (List list: tasks)
		{
			System.out.println((++i)+". "+list.title+" ["+list.priority+"]");
		}
	}
	public static void add(String p, String t) throws FileNotFoundException {
		tasks.add(new List(p,t));
		System.out.println("Added task: \""+t+"\" with priority "+p);
		writeFile();
	}
	public static void delete(int i) throws FileNotFoundException {
		if(i>tasks.size())
			System.out.println("Error: item with index "+i+" does not exist. Nothing deleted.");
		else {
			tasks.remove(i-1);
			System.out.println("Deleted item with index "+i);
		}
		writeFile();
	}
	public static void done(int i) throws FileNotFoundException {
		if(i>tasks.size())
			System.out.println("Error: no incomplete item with index "+i+"  exist.");
		else {
			complete.add(tasks.get(i-1));
			tasks.remove(i-1);
			System.out.println("Marked item is done");
		}
		writeFile();
	}
	public static void report() {
		int i=0,j=0;
		System.out.println("Pending : "+tasks.size());
		for (List list: tasks)
		{
			System.out.println((++i)+". "+list.title+" ["+list.priority+"]");
		}
		System.out.println("Completed : "+complete.size());
		for (List list: complete)
		{
			System.out.println((++j)+". "+list.title);
		}
	}
	public static void readFile() throws FileNotFoundException {
		URL url1 = Task.class.getResource("pending.txt"); 
		File pending = new File(url1.getPath());
		URL url2 = Task.class.getResource("completed.txt"); 
		File completed = new File(url2.getPath());
		
		Scanner sc = new Scanner(pending);
		Scanner sn = new Scanner(completed);
		
		try {
			while(sc.hasNextLine()) {
				tasks.add(new List(sc.next(), sc.next()));
			}
		}
		catch(NoSuchElementException e) {
			
		}
		try {	
			while(sn.hasNextLine()) {
				complete.add(new List(sn.next(), sn.next()));
			}
		}
		catch(NoSuchElementException e) {
			
		}
		
		sc.close();
		sn.close();
	}
	public static void writeFile() throws FileNotFoundException {
		URL url1 = Task.class.getResource("pending.txt"); 
		File pending = new File(url1.getPath());
		URL url2 = Task.class.getResource("completed.txt"); 
		File completed = new File(url2.getPath());
		
		PrintWriter p1 = new PrintWriter(pending);
		PrintWriter p2 = new PrintWriter(completed);
		
		p1.close();
		p2.close();
		
		p1 = new PrintWriter(pending);
		p2 = new PrintWriter(completed);
		
		Collections.sort(tasks);
		
		for (List list: tasks)
		{
			p1.write(list.priority+" "+list.title);
			p1.println();
		}
		for (List list: complete)
		{
			p2.write(list.priority+" "+list.title);
			p2.println();
		}
		
		p1.close();
		p2.close();
	}
}
