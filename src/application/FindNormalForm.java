package application;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FindNormalForm
{
		ArrayList<String> Relation, candidate_keys, prime_attr, non_prime_attr;
		HashMap<String, String> fd;
		ArrayList<ArrayList<String>> proper_subsets_of_candidate_keys; 
		
		public void input(HashMap<String, String> input_fd, ArrayList<String> relation, ArrayList<String> output_list)
		{
			this.Relation = relation;
			this.fd = input_fd;
			this.candidate_keys = output_list;
			this.proper_subsets_of_candidate_keys = new ArrayList<ArrayList<String>>();
			this.non_prime_attr = new ArrayList<String>();
			this.prime_attr = new ArrayList<String>();
			int k = this.candidate_keys.size();
			for(int i=0 ; i<k ; i++)
			{
				String candidate_key = this.candidate_keys.get(i);
				for(int j=0 ; j<candidate_key.length() ; j++)
				{
					this.prime_attr.add(Character.toString(candidate_key.charAt(j)));
				}
			}
			for(String str : this.Relation)
			{
				if(!this.prime_attr.contains(str))
				{
					this.non_prime_attr.add(str);
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
		public ArrayList<ArrayList<String>> proper_power_set(ArrayList<String> list)
		{
			ArrayList<ArrayList<String>> power_set = new ArrayList<ArrayList<String>>();
			for(int i=1 ; i<Math.pow(2, list.size()) ; i++)
			{
				String s = this.last_few_bits(i, list.size());
				ArrayList<String> temp = new ArrayList<String>();
				for(int j=0 ; j<s.length() ; j++)
				{
					if(s.charAt(j) == '1')
						temp.add(list.get(j));
				}
				if(!power_set.contains(temp))
					power_set.add(temp);
			}
			for(int i=0 ; i<power_set.size(); i++)
			{
				if(power_set.get(i).size() == list.size())
				{
					power_set.remove(i);
				}
			}
			return power_set;
		}
		public void subset_generator()
		{
			for(String s : this.candidate_keys)
			{
				ArrayList<String> t = new ArrayList<String>();
				for(int i=0 ; i<s.length() ; i++)
				{
					t.add(Character.toString(s.charAt(i)));
				}
				for(ArrayList<String> a : this.proper_power_set(t))
				{
					if(!this.proper_subsets_of_candidate_keys.contains(a))
					{
						this.proper_subsets_of_candidate_keys.add(a);
					}
				}
			}
		}
		public boolean is_superkey(String x)
		{
			ArrayList<String> X = new ArrayList<String>();
			for(int i=0 ; i<x.length() ; i++)
			{
				X.add(Character.toString(x.charAt(i)));
			}
			for(int i=0 ; i<this.candidate_keys.size() ; i++)
			{
				ArrayList<String> temp = new ArrayList<String>();
				for(int j=0 ; j<this.candidate_keys.get(i).length() ; j++)
				{
					temp.add(Character.toString(this.candidate_keys.get(i).charAt(j)));
				}
				if(X.containsAll(temp))
				{
					return true;
				}
			}
			return false;
		}
		public boolean contains_non_prime(String x)
		{
			for(int i=0 ; i<x.length() ; i++)
			{
				if(this.non_prime_attr.contains(Character.toString(x.charAt(i))))
				{
					return true;
				}
			}
			return false;
		}
		public boolean check_2NF()
		{
			for(String s : this.fd.keySet())
			{
				ArrayList<String> lhs = new ArrayList<String>();
				for(int i=0 ; i<s.length() ; i++)
				{
					lhs.add(Character.toString(s.charAt(i)));
				}
				String s1 = this.fd.get(s);
				if(this.proper_subsets_of_candidate_keys.contains(lhs) && contains_non_prime(s1))
					return false;
			}
			return true;
		}
		public boolean check_3NF()
		{
			for(String str : this.fd.keySet())
			{
				ArrayList<String> str_explode = new ArrayList<String>();
				for(int i=0 ; i<str.length() ; i++)
				{
					str_explode.add(Character.toString(str.charAt(i)));
				}
				if(!(this.is_superkey(str) || this.prime_attr.containsAll(str_explode)))
				{
					return false;
				}
			}
			return true;
		}
		public boolean check_BCNF()
		{
			for(String str : this.fd.keySet())
			{
				if(!this.is_superkey(str))
					return false;
			}
			return true;
		}
	}
	

