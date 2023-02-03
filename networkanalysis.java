/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package project;
import java.io.*;
import java.util.Stack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author habiba
 */
public class networkanalysis {
    
ArrayList<String> FollowerList = new ArrayList<>();

ArrayList<String> rows = new ArrayList<>();

ArrayList<String> rowssp = new ArrayList<>();

	public networkanalysis(String xml){
		
		String[] rowsArray = xml.trim().replace(" ", "").replaceAll(">", ">\n").replaceAll("<", "\n<").split("\n");
                String[] rowsspecial = xml.trim().replace("  ", "").replaceAll(">", ">\n").replaceAll("<", "\n<").split("\n");
		for (String s : rowsArray){
			if (!s.isEmpty()){
				rows.add(s);
			}
		}
                for (String s : rowsspecial){
			if (!s.isEmpty()){
				rowssp.add(s);
			}
		}
	}


	public ArrayList<String> getMostInfluencerUser(){

		int mostInfluencerFollowers=0, mostInfluencerID=0;
		int currentUserFollowers=0, currentUserID=0;
                
                  ArrayList<String>list = new ArrayList<>();
                   ArrayList<String>repeated = new ArrayList<>();
		for (int i=0; i< rows.size(); i++){
			if (rows.get(i).equals("<user>") && rows.get(i+1).equals("<id>")){
				currentUserID = getUserID(i+2);
                                repeated.add("*");
                                repeated.add(String.valueOf(currentUserID));
				currentUserFollowers = getCurrentUserFollowers(i);
                                repeated.add(String.valueOf(currentUserFollowers));
                                
                              
				if (currentUserFollowers > mostInfluencerFollowers){
					
					mostInfluencerID = currentUserID;
					mostInfluencerFollowers = currentUserFollowers;
				}
                                
			}
		}
                
                for(int i =0 ; i < repeated.size() ; i++){
                    
                    if(repeated.get(i).equals("*")){
                        if(repeated.get(i+2).equals(String.valueOf(mostInfluencerFollowers))){
                            list.add(repeated.get(i+1));
                        
                        }
                        
                    
                    }
                
                }
                System.out.println(repeated);

		return list;
	}


	public int getCurrentUserFollowers(int startingIndex) {

		int followers =0;
		for (int i=startingIndex; i< rows.size(); i++) {
			
			if (rows.get(i).equals("<follower>")){
                            
				followers++;
                                
			}else if (rows.get(i).equals("</followers>")){
				break;
			}
		}

		return followers;
	}
       
     public ArrayList<String> getMostActiveUser(){

		int mostactiveFollowing=0, mostactiveID=0;
		int currentUserFollowing=0, currentUserID=0;
                
                  ArrayList<String>list = new ArrayList<>();
                  ArrayList<String>repeated = new ArrayList<>();
		for (int i=0; i< rows.size(); i++){
			if (rows.get(i).equals("<user>") && rows.get(i+1).equals("<id>")){
				currentUserID = getUserID(i+2);
                                repeated.add("*");
                                repeated.add(String.valueOf(currentUserID));
				currentUserFollowing = getFollowingList(rows.get(i+2));
                                repeated.add(String.valueOf(currentUserFollowing));
                              
				if (currentUserFollowing > mostactiveFollowing){
					
					mostactiveID = currentUserID;
					mostactiveFollowing = currentUserFollowing;
				}
			}
		}

		 for(int i =0 ; i < repeated.size() ; i++){
                    
                    if(repeated.get(i).equals("*")){
                        if(repeated.get(i+2).equals(String.valueOf(mostactiveFollowing))){
                            list.add(repeated.get(i+1));
                        
                        }
                        
                    
                    }
                
                }
                System.out.println(repeated);

		return list;
	}
        
        
        
        
        
      public int getFollowingList(String x) {
		int following = -1;
		for (int i=0; i< FollowerList.size(); i++) {
		if(FollowerList.get(i).equals(x)){
                following++;
                }				
		}

		return following;
	}

        
      public String PostSearch(String word){
        Stack<String> stackPosts = new Stack<>();
        String post = "";
        String result = "";
        int counter=1;
        boolean val= false ;    
        stackPosts.push("*");
        for (int i=0; i< rowssp.size(); i++){

                 if (rowssp.get(i).equals("<post>") ){      
                   for(int j=i+1 ;j<rowssp.size(); j++)
                   {
                        if((rowssp.get(j).equals("<body>"))){       
                      if(!(rowssp.get(j++).equals("</body>"))){
                      stackPosts.push(rowssp.get(j++));
                      }}
                   else if(rowssp.get(j).equals("<topics>")){
                       while(!rowssp.get(j++).equals("</topics>")){
                         if((rowssp.get(j).equals("<topic>"))){
                         if(!(rowssp.get(j++).equals("</topic>"))){
                            stackPosts.push(rowssp.get(j++));}
                         }}
                         
                        } else{
                      break;}
                 }stackPosts.push("*");
                 }}

         while( !stackPosts.empty() && !val ){
             post=stackPosts.peek();
             val = post.contains(word);
             stackPosts.pop();
             while( !stackPosts.empty() && val)
             {
             if(!stackPosts.peek().equals("*")){
              post=stackPosts.peek();
              
             stackPosts.pop();

             }else{ 
             result+="post "+counter+" :\r\n"; 
             result+=post+"\r\n";
             counter ++;
             if(!stackPosts.empty()){
               val=false;
               stackPosts.pop();
             }else{
                 
               break;}
             
             }              

             }}
       return result;
        }


         
	public int getUserID(int Index){
		int id=0;
		id = Integer.parseInt(rows.get(Index));
		return id;
	}
        
        public ArrayList<String> getlist(String x) {
          ArrayList<String> Listt = new ArrayList<>();
           for(int i=0; i<FollowerList.size()-1;i++){
    
            if(FollowerList.get(i).equals("*"))
            {
                
                if(FollowerList.get(i+1).equals(x))
                {
                   
                   for(int j =i+2;j<FollowerList.size();j++)
                   {
           
                   if(!(FollowerList.get(j).equals("*"))){
                       Listt.add(FollowerList.get(j));
                   
                   }
                   else{break;}
                   }
                }
            }
        
        }
          return Listt;
          }
        
          
        public ArrayList<String> getSuggestList(String ID){
            ArrayList<String> currentfollowerlist=new ArrayList<>();
             ArrayList<String> currentfollowinglist=new ArrayList<>();
            ArrayList<String> result=new ArrayList<>();
            Set<String> mix=new HashSet<String>(); 
             ArrayList<String> temp=new ArrayList<>();     
            currentfollowerlist=getlist(ID);
            currentfollowinglist=getFollowing(ID);
            for(int i=0; i< currentfollowerlist.size();i++){
                temp=getlist(currentfollowerlist.get(i));
                for(int j=0; j< temp.size();j++){
                mix.add(temp.get(j));
                
                }
              mix.remove(ID);
            }
            for(int j=0; j< mix.size();j++){
          
          for(int k=0 ; k<  currentfollowinglist.size();k++ ){

                  mix.remove(  currentfollowinglist.get(k));
            
          
        }}
            
             
          for(String value :mix){
              
                  result.add(value);
            
          
        }
            
            
        
           System.out.println("mix"+mix);
            return result;
            
        }
        
        
        public ArrayList<String> getMutualFollowers(String x , String y) {
            ArrayList<String> List1 = new ArrayList<>();
            ArrayList<String> List2 = new ArrayList<>();
            ArrayList<String> mutual=new ArrayList<>();
            
            List1 =getlist(x);
            List2=getlist(y);
            System.out.println(List1);
            System.out.println(List2);
            
            for(int i=0; i <List1.size();i++) 
            {
              
            for(int j=0; j <List2.size();j++)
            {
            if( (List1.get(i)).equals(List2.get(j)))
            {
                mutual.add(List2.get(j));
            }
            }
            
            }

            return mutual;
           }
        
        
        public ArrayList<String>getFollowing(String x) {
            ArrayList<String> result=new ArrayList<>();
           ArrayList<Integer> matchingIndices = new ArrayList<>();
            for (int i = 0; i < FollowerList.size(); i++) {
             
            if ((FollowerList.get(i)).equals("*")) {
               
                matchingIndices.add(i);
                    }}
            
            for(int j=0;j< matchingIndices.size()-1;j++){
            if(!(FollowerList.get(matchingIndices.get(j)+1)).equals(x)){
            for(int k=matchingIndices.get(j)+1;k<matchingIndices.get(j+1);k++){
                if(FollowerList.get(k).equals(x)){
                result.add(FollowerList.get(matchingIndices.get(j)+1));
                
                }}}}
           
            return result;
        }
        
        
        
        
        
	public void getFollowersList() {
                
		int  currentUserID=0;
                FollowerList.add("*");
    
		for (int i=0; i< rows.size(); i++){
			if (rows.get(i).equals("<user>") && rows.get(i+1).equals("<id>")){
				currentUserID = getUserID(i+2);
                                FollowerList.add(rows.get(i+2));
                                 }
                    for (int j=i; j< rows.size(); j++) {
                     if (rows.get(i).equals("<follower>")){
                           FollowerList.add(rows.get(j+2));
                        break;
			}	else if (rows.get(i).equals("</followers>")){
                              FollowerList.add("*");
				break;
			}
                               
                        }	
			}
                        
                }
        
            public static void main(String args[]) throws IOException {
      //String s= "h";
         
        String s = "<users>\n" +
                "    <user>\n" +
                "        <id>1</id>\n" +
                "        <name>Ahmed Ali</name>\n" +
                "        <posts>\n" +
                "            <post>\n" +
                "                <body>\n" +
               // "                  shamosaa"+
                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        economy\n" +
                "                    </topic>\n" +
                "                    <topic>\n" +
                "                        finance\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "            <post>\n" +
                "                <body>\n" +
                //"                  mai"+
                "                    A friend is someone who makes it easy to believe in yourself\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        solar_energy\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "        </posts>\n" +
                "        <followers>\n" +
                "            <follower>\n" +
                "                <id>2</id>\n" +
                "            </follower>\n" +
                "            <follower>\n" +
                "                <id>3</id>\n" +
                "            </follower>\n" +
                "        </followers>\n" +
                "    </user>\n" +
                "    <user>\n" +
                "        <id>2</id>\n" +
                "        <name>Yasser Ahmed</name>\n" +
                "        <posts>\n" +
                "            <post>\n" +
                "                <body>\n" +
                //"                  maram"+
                "                    family is not an important thing. its everything.\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        education\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "        </posts>\n" +
                "        <followers>\n" +
                "            <follower>\n" +
                "                <id>1</id>\n" +
                "            </follower>\n" +
                "            <follower>\n" +
                "                <id>3</id>\n" +
                "            </follower>\n" +
                "            <follower>\n" +
                "                <id>4</id>\n" +
                "            </follower>\n" +
                "        </followers>\n" +
                "    </user>\n" +
                "    <user>\n" +
                "        <id>3</id>\n" +
                "        <name>Mohamed Sherif</name>\n" +
                "        <posts>\n" +
                "            <post>\n" +
                "                <body>\n" +
              //  "                 habiba"+
                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        family\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "        </posts>\n" +
                "        <followers>\n" +
                "            <follower>\n" +
                "                <id>1</id>\n" +
                "            </follower>\n" +
                "        </followers>\n" +
                "    </user>\n" +
                "    <user>\n" +
                "        <id>4</id>\n" +
                "        <name>Yasser Ahmed</name>\n" +
                "        <posts>\n" +
                "            <post>\n" +
                "                <body>\n" +
                //"                esraa"+
                "                    Every person you meet knows something you dont; learn from them\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        sport\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "        </posts>\n" +
                "        <followers>\n" +
                "            <follower>\n" +
                "                <id>3</id>\n" +
                "            </follower>\n" +
                "        </followers>\n" +
                "    </user>\n" +
                "    <user>\n" +  
                 "        <id>5</id>\n" +
                "        <name>Yasser Ahmed</name>\n" +
                "        <posts>\n" +
                "            <post>\n" +
                "                <body>\n" +
                //"                esraa"+
                "                   every person you meet knows something you dont; learn from them \n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        cartoon\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "        </posts>\n" +
                "        <followers>\n" +
                "            <follower>\n" +
                "                <id>1</id>\n" +
                "            </follower>\n" +
                "            <follower>\n" +
                "                <id>3</id>\n" +
                "            </follower>\n" +
                "            <follower>\n" +
                "                <id>4</id>\n" +
                "            </follower>\n" +                
                "        </followers>\n" +
                "    </user>\n" +                  
                "</users>";
        networkanalysis var =new networkanalysis(s);
        
        var.getFollowersList();
        
    //  System.out.println(  var.getFollowing("4"));
         //       System.out.println(var.FollowerList);
        /*
        for(int i=0 ;i< var.rowssp.size();i++ ){
         System.out.println(var.rowssp.get(i));
        }
*/
               System.out.println(var.PostSearch("cartoon"));
      //  System.out.println(var.getSuggestList("2"));
                //System.out.println(var.getSuggestList("2"));
          
           //System.out.println(var.getMostActiveUser());
          //System.out.println(var.getMutualFollowers("1","2"));
               
           
             //  System.out.println(var.getSuggestList("1") );
                
               

            }
        
}
  
        


