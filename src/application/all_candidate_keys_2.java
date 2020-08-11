package application;

import java.util.ArrayList;
import java.util.HashMap;

public class all_candidate_keys_2
{
	HashMap<String, String> min_cov_fd; //assuming that lhs does not repeat : NO - A->B, A->C
	ArrayList<String> essential, common, useless, Relation, candidate_keys;
	ArrayList<ArrayList<String>> common_power_set;
	
	public void input(HashMap<String, String> input, ArrayList<String> relation )
	{
		this.Relation = relation;
		this.min_cov_fd = input;
		
	}
	public void divide_attributes()
	{
		this.essential = new ArrayList<String>();
		this.candidate_keys = new ArrayList<String>();
		this.common = new ArrayList<String>();
		this.useless = new ArrayList<String>();
		boolean[] occur_in_lhs = new boolean[this.Relation.size()];
		boolean[] occur_in_rhs = new boolean[this.Relation.size()];
		for(int i=0 ; i<this.Relation.size() ; i++)
		{
			for(String lhs : this.min_cov_fd.keySet())
			{
				char curr_attribute = this.Relation.get(i).charAt(0);
				if(lhs.indexOf(curr_attribute) != -1)
				{
					occur_in_lhs[i] = true;
					break;
				}
				else
				{
					occur_in_lhs[i] = false;
				}
			}
			for(String rhs : this.min_cov_fd.values())
			{
				char curr_attribute = this.Relation.get(i).charAt(0);
				if(rhs.indexOf(curr_attribute) != -1)
				{
					occur_in_rhs[i] = true;
					break;
				}
				else
				{
					occur_in_rhs[i] = false;
				}
			}
			if(occur_in_lhs[i] && occur_in_rhs[i])
			{
				this.common.add(this.Relation.get(i));
			}
			else if(!occur_in_lhs[i] && occur_in_rhs[i])
			{
				this.useless.add(this.Relation.get(i));
			}
			else
			{
				this.essential.add(this.Relation.get(i));
			}
		}
	}
	public String last_few_bits(int a, int nos_bits)
	{
		String s1 = Integer.toBinaryString(a);
		int len_s1 = s1.length();
		if(len_s1 > nos_bits)
		{
			return s1.substring(len_s1 - nos_bits);
		}
		else if(len_s1 < nos_bits)
		{
			String s2 = s1;
			for(int i=0 ; i<(nos_bits-len_s1) ; i++)
			{
				s2 = "0".concat(s2);
			}
			return s2;
		}
		return s1;
	}
	public boolean belongsTo(String s, ArrayList<String> a)
	{
		int count = 0 ;
		for(int i=0 ; i<s.length() ; i++)
		{
			if(a.contains(Character.toString(s.charAt(i))))
			{
				count = count + 1;
			}
		}
		if(count == s.length())
		{
			return true;
		}
		return false;
	}
	public ArrayList<String> find_closure(ArrayList<String> attr)
	{
		ArrayList<String> closure = new ArrayList<String>();
		for(int i=0 ; i<attr.size(); i++)
		{
			closure.add(attr.get(i));
		}
		boolean change_to_closure = true;
		while(change_to_closure)
		{
			change_to_closure = false;
			for(String lhs : this.min_cov_fd.keySet())
			{
				if(this.belongsTo(lhs, closure))
				{
					//System.out.println("lhs = " + lhs);
					String rhs = this.min_cov_fd.get(lhs);
					for(int i=0 ; i<rhs.length() ; i++)
					{
						if(!closure.contains(Character.toString(rhs.charAt(i))))
						{
							closure.add(Character.toString(rhs.charAt(i)));
							change_to_closure = true;
						}
					}
				}
			}
		}
		return closure;
	}
	public ArrayList<ArrayList<String>> gen_sorted_power_set(ArrayList<String> list)
	{
		ArrayList<ArrayList<String>> power_set = new ArrayList<ArrayList<String>>();
		for(int i=1 ; i<Math.pow(2, list.size()) ; i++)
		{
			String s = this.last_few_bits(i, list.size());
			ArrayList<String> temp = new ArrayList<String>();
			for(int j=0 ; j<s.length() ; j++)
			{
				if(s.charAt(j) == '1')
				{
					temp.add(list.get(j));
				}
			}
			power_set.add(temp);
		}
		for(int i=0 ; i<power_set.size() ; i++)
		{
			for(int j=0 ; j<power_set.size() - 1 ; j++)
			{
				if(power_set.get(j).size() > power_set.get(j + 1).size())
				{
					ArrayList<String> t = power_set.get(j + 1);
					power_set.remove(j + 1);
					power_set.add(j, t);
				}
			}
		}
		return power_set;
	}
	public int next_not_removed(boolean[] rem)
	{
		for(int i=0 ; i<rem.length ; i++)
		{
			if(!rem[i])
			{
				return i;
			}
		}
		return -1;
	}
	public ArrayList<String> find_all_keys()
	{
		ArrayList<String> essential_closure = this.find_closure(this.essential);
		if(essential_closure.containsAll(this.Relation) && this.Relation.containsAll(essential_closure))
		{
			String s = "";
			for(int i=0 ; i<this.essential.size() ; i++)
			{
				s = s.concat(this.essential.get(i));
			}
			this.candidate_keys.add(s);
			//System.out.println(this.candidate_keys);
			return this.candidate_keys;
		}
		boolean removed[] = new boolean[this.common_power_set.size()];
		for(int i=0 ; i<removed.length ; i++)
		{
			removed[i] = false;
		}
		while(this.next_not_removed(removed) != -1)
		{
			int next_ind = this.next_not_removed(removed);
			ArrayList<String> z = this.common_power_set.get(next_ind);
			removed[next_ind] = true;
			ArrayList<String> z_closure = this.find_closure(z);
			if(z_closure.containsAll(this.Relation) && this.Relation.containsAll(z_closure))
			{
				String s = "";
				for(int j=0 ; j<z.size(); j++)
				{
					s = s.concat(z.get(j));
				}
				this.candidate_keys.add(s);
				for(int i=0 ; i<this.common_power_set.size() ; i++)
				{
					if(!removed[i] && this.common_power_set.get(i).containsAll(z))
					{
						removed[i] = true;
					}
				}
			}
		}
		return this.candidate_keys;
	}
	public void add_essential_to_common_power_set()
	{
		for(int i=0 ; i<this.common_power_set.size() ; i++)
		{
			for(int j=0 ; j<this.essential.size() ; j++)
			{
				this.common_power_set.get(i).add(this.essential.get(j));
			}
		}
	}
	
}
