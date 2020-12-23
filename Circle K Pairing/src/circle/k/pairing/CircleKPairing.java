/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circle.k.pairing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.util.Pair;

/**
 *
 * @author Travis
 */
public class CircleKPairing {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
      
		//Stores Information for families and members through input 
        ArrayList<String> families = new ArrayList<String>();
        ArrayList<Member> memberList = new ArrayList<Member>();
        
        //Map that has pairs with number of leftovers
        HashMap<ArrayList<Pair<Member, Member>>, Integer> pairings = new HashMap<ArrayList<Pair<Member, Member>>, Integer>();
        
        families = getFamilies();
        memberList = getMembers(families);
        
        Pair< ArrayList<Pair<Member, Member>> , Integer> paired; 
        
        for(int i = 0; i < 3; i++)
        { //100 Simulations
            Collections.shuffle(memberList);
            
            paired = pairMembers(memberList);
            pairings.put(paired.getKey(), paired.getValue());
        }
        
        int smallestLeftover = 100000;
        ArrayList<Pair<Member, Member>> officialList = new ArrayList<Pair<Member, Member>>();
        for (Map.Entry<ArrayList<Pair<Member, Member>>, Integer> entry : pairings.entrySet()) {
            ArrayList<Pair<Member, Member>> key = entry.getKey();
            Integer value = entry.getValue();
            
            if(value < smallestLeftover)
            {
                smallestLeftover = value;
                officialList = key;
            }
    
        }   
        
        for(int i = 0; i < officialList.size(); i++){
            System.out.println(officialList.get(i).getKey().name + " paired with " + officialList.get(i).getValue().name);
        }
    }
    
    //creates pairings based on list
    public static Pair< ArrayList<Pair<Member, Member>> , Integer> pairMembers(ArrayList<Member> memberList)
    {
        ArrayList<Pair<Member, Member>> pairList = new ArrayList<Pair<Member, Member>>(); 
        ArrayList<Member> memberListCopy = new ArrayList<>(memberList);
        int leftOvers = 0;
        int memberTracker = 0;
        int initialSize = memberList.size();
        Boolean found = false;
        
        
       
        while(!memberListCopy.isEmpty() || memberTracker >= initialSize)
        {
            found = false;
            int pairer = 0;
            
            while(!found &&  memberTracker <= memberListCopy.size())
            {
                if(canPairTogether(memberListCopy.get(memberTracker), memberListCopy.get(pairer)))
                {
                    //Get the found members
                    Member found1 = memberListCopy.get(memberTracker);
                    Member found2 = memberListCopy.get(pairer);
                    
                    //Add pair to finalize list
                    pairList.add(new Pair<> (found1,found2));
                    
                    //Remove the members from list
                    memberListCopy.remove(found1);
                    memberListCopy.remove(found2);
                    
                    found = true;
                   
                }
                else
                {
                    pairer++;
                }
                
            }
            
            if(found == false)
            {
                memberTracker++;
            }
        }
        
        leftOvers = memberList.size();
        
        Pair< ArrayList<Pair<Member, Member>> , Integer> pairings = new Pair<>(pairList, leftOvers);
        return pairings;
    }
    
    public static Boolean canPairTogether(Member person1, Member person2)
    {
        if(person1.family == person2.family)
            return false;
        else
            return true;
    }
    
    public static ArrayList<String> getFamilies()
    {
        Scanner input = new Scanner(System.in);
        
        //GET FAMILIES
        System.out.println("Enter Family #1's Name (Enter 'X' when finished): ");
        String currentFamName = input.next();
    
        ArrayList<String> families = new ArrayList<String>();
        
        while(!currentFamName.equals("x") && !currentFamName.equals("X"))
        {
            families.add(currentFamName);
            
            System.out.println("Enter Family #" + (families.size() + 1) + "'s Name (Enter 'X' when finished): ");
            currentFamName = input.next();
        }
        
        return families;
    }
    
    public static void printFams(ArrayList<String> families)
    {
        for(int i = 0 ; i < families.size(); i++)
        {
            System.out.println(i + " - " + families.get(i));
        }
    }
    
    public static ArrayList<Member> getMembers(ArrayList<String> families)
    {
        Scanner input = new Scanner(System.in);
        
        //GET MEMBERS
        Member currentMember = new Member();
        System.out.println("Enter Person #1's Name (Enter 'X' when finished): ");
        currentMember.name = input.next();
        System.out.println();
        ArrayList<Member> memberList = new ArrayList<Member>();
        
        while(!currentMember.name.equals("x") && !currentMember.name.equals("X"))
        {
            //System.out.println("Enter " + currentMember.name +"'s family number: ");
           
            do{
                try{
                    String s = input.nextLine();
                    currentMember.family = Integer.parseInt(s);
                    break;
                }
                catch(Exception e)
                {
                    printFams(families);
                    System.out.println("Enter " + currentMember.name +"'s family NUMBER: ");
                }
                
            }while(true);
                    
            
            memberList.add(currentMember);
            
            currentMember = new Member();
            
            System.out.println("Enter Person #" + (memberList.size() + 1) + "'s Name (Enter 'X' when finished): ");
            currentMember.name = input.next();
            
        }
        
        return memberList;
    }
        
  
    
}
