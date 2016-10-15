/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Umang
 */
public class BtreeTester {
    
    public static IntBalancedSet ReadTreeFromTextFile(String FileName) throws IOException, CloneNotSupportedException 
    {
        int element;
        
        Scanner fileIn = new Scanner(new File(FileName));
        
        IntBalancedSet treeA = new IntBalancedSet();
        while(fileIn.hasNext())
        {
            element = fileIn.nextInt();
            if(!treeA.contains(element))
            {
                treeA.add(element);
                System.out.println(element);
            }
            else
                System.out.println(element + " already exists...");
        }
        System.out.println();
        System.out.println("End of Reading!");
        return treeA;
    }
    
     public static void main(String[] args) throws IOException, CloneNotSupportedException {
         IntBalancedSet treeA = new IntBalancedSet();
         treeA = ReadTreeFromTextFile("treeA.txt");
         treeA.print(8);
         
         
    }
    
}
