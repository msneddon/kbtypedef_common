


module Test {


	typedef string my_string;
	typedef int my_int;
	typedef float my_float;
	
	typedef list<string> my_string_list;
	typedef list<int> my_int_list;
	typedef list<float> my_float_list;
	
	typedef mapping<string,string> my_string_mapping;
	typedef mapping<string,int> my_int_mapping;
	typedef mapping<string,float> my_float_mapping;


	typedef structure {
		string name;
		my_string s;
		my_int i;
		my_float f;
	} S1;
	
	
	

};