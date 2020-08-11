package application;


import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NormalController 

{
	@FXML
	private Button removeRedundancy;

	@FXML
	private Button computeKey;

	@FXML
	private Button calculatenormalForm;

	@FXML
	private Button decomposetable;
	
	@FXML
	private Button vieworignal;
	
	@FXML
	private Button newSearch;
	
	@FXML
	private TextField re;
	@FXML
	private TextArea fd;
	
	@FXML
	private TextArea compute_key;
	
	@FXML
	private TextArea found_normal_form;
	
	@FXML
	private TextArea decomposed_relation;
	
	@FXML private Label addfd;
	
	@FXML private Label addre;
	
	@FXML private Label redundancy_removed_messsage;
	
	private String input[];
	
	private String key_output;
	private ArrayList<String> minimal_cover;
	
	private ArrayList<String> output_list;
	
	private ArrayList<String> relation;
	
	private HashMap<String,String> input_fd ;
	private int arrow_index;
	
	
	
	public void remove_redundancy(ActionEvent e) throws Exception
	{
	   redundancy_removed_messsage.setText("Redundancy removed you are good to go!");
	    input = fd.getText().split("\n");
	   ArrayList<String> list_input= new ArrayList<String>();
	   for(int i=0; i<input.length; i++)
	   {
		   list_input.add(input[i]);
	   }
	   Remove_Redundancy obj1 = new Remove_Redundancy();
	   minimal_cover = obj1.refine_relation(list_input);
	   fd.setText(obj1.refine_relation(list_input).toString());  
	}
	
	public void view_orignal(ActionEvent e) throws Exception
	{
		fd.appendText("\n");
		for(int i = 0; i<input.length; i++)
		{
			if(input[i].length()>0) {
				fd.appendText("\n");
			}
			
			fd.appendText(input[i]);
		}
	}
	
	public void compute_key(ActionEvent e) throws Exception
	{
		all_candidate_keys_2 obj = new all_candidate_keys_2();
		input_fd = new HashMap<String,String>();
		for(String str : minimal_cover)
		{
		    arrow_index = str.indexOf('-');
			input_fd.put(str.substring(0, arrow_index), str.substring(arrow_index+2));
		}
		
		relation = new ArrayList<String>();
       	for(int i=0; i<re.getText().length(); i++)
		{
			relation.add(Character.toString(re.getText().charAt(i)));
		}
		
		obj.input(input_fd,relation);
		obj.divide_attributes();
		obj.common_power_set = obj.gen_sorted_power_set(obj.common);
		obj.add_essential_to_common_power_set();
		output_list = obj.find_all_keys();
		 key_output = "";

		for(int i=0; i<output_list.size(); i++)
		{
			key_output = key_output + ", "+ output_list.get(i);
		}
		
		compute_key.setText("The keys for given set of functional dependecies are: "+key_output.substring(1));
	}
	
	
	
	
	public void compute_normalform(ActionEvent e) throws Exception
	{
		FindNormalForm obj = new FindNormalForm();
		obj.input(input_fd,relation,output_list);
		obj.subset_generator();
		
		if(obj.check_2NF())
		{
			found_normal_form.setText("The given relation is in 2NF");
		}
		
		if(obj.check_3NF())
		{
			
			found_normal_form.setText("The given relation is in 3NF");
		}
		if(obj.check_BCNF())
		{
			found_normal_form.setText("The given relation is in BCNF");
		}
		if(!obj.check_2NF() && !obj.check_3NF() && !obj.check_BCNF())
		{
			found_normal_form.setText("The given relation is in 1NF");
		}
	}
	
	public void normalize_table(ActionEvent e) throws Exception{
		NormalizeTable obj = new NormalizeTable();
		obj.input(input_fd, relation, output_list);
		
		decomposed_relation.setText("New relations are: "+ obj.to_BCNF().toString());
		ArrayList<ArrayList<String>> new_relations = obj.to_BCNF();
		decomposed_relation.appendText("\n");	
		ArrayList<HashMap<String, String>> new_fd = obj.display_fd();
		decomposed_relation.appendText("Functional dependencies are: "+new_fd.toString().substring(0,(new_fd.toString().length()/2)-1)+"]");
		
		all_candidate_keys_2 obj1 = new all_candidate_keys_2();
		ArrayList<ArrayList<String>> new_keys = new ArrayList<ArrayList<String>>();
		
		for(int i=0; i<new_relations.size(); i++)
		{
			obj1.input(new_fd.get(i), new_relations.get(i));
			obj1.divide_attributes();
			obj1.common_power_set = obj1.gen_sorted_power_set(obj1.common);
			obj1.add_essential_to_common_power_set();
			new_keys.add(obj1.find_all_keys());
		}
		
		decomposed_relation.appendText("\n");
		decomposed_relation.appendText("New keys are: "+ new_keys.toString().substring(0,(new_keys.toString().length()/2)-1)+"]");
		
	}
	
	public void new_search(ActionEvent e) throws Exception{
		re.setText("");
		fd.setText("");
		compute_key.setText("");
		found_normal_form.setText("");
		decomposed_relation.setText("");
		redundancy_removed_messsage.setText("");
		
	}
		

}
	
	
			
	


