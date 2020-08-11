package application;
import java.util.*;

public class Remove_Redundancy {
	public ArrayList<String> refine_relation(ArrayList<String> input)
	{
		/*Scanner sc = new Scanner(System.in);
		int nos_attributes = sc.nextInt();	// the number of attributes in relation R
		char[] Relation = new char[nos_attributes];	// the relation R
		for(int i=0 ; i<nos_attributes ; i++)
		{
			Relation[i] = sc.next().charAt(0);
		}*/
		int nos_func_dep =  input.size();	// the number of functional dependencies
		ArrayList<ArrayList<HashSet<String>>> all_func_deps = new ArrayList<ArrayList<HashSet<String>>>();
		for(int i=0 ; i<nos_func_dep ; i++) // get functional dependencies in right format
		{
			String full_fd = input.get(i);
			String left_of_fd_str = full_fd.substring(0, full_fd.indexOf('-'));
			String right_of_fd_str = full_fd.substring(full_fd.indexOf('>') + 1);
			
			HashSet<String> left_of_fd_set = new HashSet<String>();
			HashSet<String> right_of_fd_set = new HashSet<String>();
			for(int j=0 ; j<left_of_fd_str.length() ; j++)
			{
				left_of_fd_set.add(left_of_fd_str.substring(j, j+1));
			}
			for(int j=0 ; j<right_of_fd_str.length() ; j++)
			{
				right_of_fd_set.add(right_of_fd_str.substring(j, j+1));
			}
			ArrayList<HashSet<String>> single_func_dep = new ArrayList<HashSet<String>>();
			single_func_dep.add(left_of_fd_set);
			single_func_dep.add(right_of_fd_set);
			all_func_deps.add(single_func_dep);
		}
		for(int i=0 ; i<all_func_deps.size() ; i++)
		{
			ArrayList<HashSet<String>> full_fd_temp = all_func_deps.get(i);
			int index = all_func_deps.indexOf(full_fd_temp);
			all_func_deps.remove(index);
			HashSet<String> left_fd_temp = new HashSet<String>();
			left_fd_temp = full_fd_temp.get(0);
			Iterator<ArrayList<HashSet<String>>> itr2 = all_func_deps.iterator();
			while(itr2.hasNext())
			{
				ArrayList<HashSet<String>> fd_to_compare = new ArrayList<HashSet<String>>();
				fd_to_compare = itr2.next();
				if(left_fd_temp.containsAll(fd_to_compare.get(0)))
				{
					left_fd_temp.addAll(fd_to_compare.get(1));
				}
			}
			all_func_deps.add(index, full_fd_temp);
			if(left_fd_temp.containsAll(full_fd_temp.get(1)))
			{
				input.remove(i);
			}
		}
		/*ListIterator<ArrayList<HashSet<String>>> itr1 = all_func_deps.listIterator();
		while(itr1.hasNext())
		{
			ArrayList<HashSet<String>> full_fd_temp = itr1.next();
			int index = all_func_deps.indexOf(full_fd_temp);
			itr1.remove();
			HashSet<String> left_fd_temp = new HashSet<String>();
			left_fd_temp = full_fd_temp.get(0);
			Iterator<ArrayList<HashSet<String>>> itr2 = all_func_deps.iterator();
			while(itr2.hasNext())
			{
				ArrayList<HashSet<String>> fd_to_compare = new ArrayList<HashSet<String>>();
				fd_to_compare = itr2.next();
				if(left_fd_temp.containsAll(fd_to_compare.get(0)))
				{
					left_fd_temp.addAll(fd_to_compare.get(1));
				}
			}
			all_func_deps.add(index, full_fd_temp);
			if(left_fd_temp.containsAll(full_fd_temp.get(1)))
			{
				System.out.println("FD nos " + index + 1 + "is redundant");
			}
		}
		sc.close();*/
		return input;
	}
}
