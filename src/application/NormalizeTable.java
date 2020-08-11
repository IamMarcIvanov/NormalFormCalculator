package application;

import java.util.ArrayList;
import java.util.HashMap;

public class NormalizeTable
{
	ArrayList<String> Relation;
	HashMap<String, String> fd;
	ArrayList<ArrayList<String>> new_relations;
	ArrayList<HashMap<String, String>> new_fd;
	
	public void input(HashMap<String, String> input_fd, ArrayList<String> relations, ArrayList<String> output_list)
	{

		this.Relation = relations;
		this.fd = input_fd;
		
		this.new_relations = new ArrayList<ArrayList<String>>();
		this.new_fd = new ArrayList<HashMap<String, String>>();
		
	}

	public ArrayList<ArrayList<String>> to_BCNF()
	{
		for(String s : this.fd.keySet())
		{
			ArrayList<String> lhs = new ArrayList<String>();
			for(int i=0 ; i<s.length() ; i++)
			{
				lhs.add(Character.toString(s.charAt(i)));
			}
			ArrayList<String> rhs = new ArrayList<String>();
			String s1 = this.fd.get(s);
			for(int i=0 ; i<s1.length() ; i++)
			{
				rhs.add(Character.toString(s1.charAt(i)));
			}
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put(s, s1);
			this.new_fd.add(temp);
			
			ArrayList<String> t = new ArrayList<String>();
			for(int i=0 ; i<s.length(); i++)
			{
				String to_add = Character.toString(s.charAt(i));
				if(!t.contains(to_add))
					t.add(to_add);
			}
			for(int i=0 ; i<s1.length(); i++)
			{
				String to_add = Character.toString(s1.charAt(i));
				if(!t.contains(to_add))
					t.add(to_add);
			}
			this.new_relations.add(t);
		}
		return new_relations;
	}
	
	public ArrayList<HashMap<String, String>> display_fd()
	{
		return new_fd;
	}


}