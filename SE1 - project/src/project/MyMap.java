package project;

import java.util.ArrayList;
import java.util.List;

public class MyMap {
	public List<? extends Object> mainInfo=new ArrayList<Object>();
	public List<? extends Object> secondaryInfo;
	
	public MyMap () {
	}
	
	//throws unhandled index out of bounds exception if field lists are not same length
	//this should never occur
	public List<String> asList() {
		List<String> stringList=new ArrayList<String>();
		
		for (int i=0;i<mainInfo.size();i++) {
			stringList.add(mainInfo.get(i).toString() + " " + secondaryInfo.get(i).toString());
		}
		return stringList;
	}
}
