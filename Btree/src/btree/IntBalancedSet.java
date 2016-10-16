/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;
/**
 *
 * @author Umang
 */
public class IntBalancedSet implements Cloneable {

    public static final int MINIMUM = 1;
    public static final int MAXIMUM = 2*MINIMUM;
    int dataCount;
    int[] data = new int[MAXIMUM + 1];
    int childCount;
    IntBalancedSet[] subset = new IntBalancedSet[MAXIMUM + 2];
//    public static int count =0; 

   
    public IntBalancedSet()
    {
        dataCount = 0;
        childCount = 0;
    }
    
    public int getLeftMostData()
    {
        if(subset[0] == null)
            return data[0];
        else
            return subset[0].getLeftMostData();
    }
    
    
    
    public boolean contains(int target)
    {
        
            int i=0;
 //           for (i=0; i<root.data.length && root.data[i] >= target; i++);
            for(int j = 0; j<data.length; j++)
            {
                if(data[j]>=target)
                {
                    i=j;
                    break;
                }
                else
                    i=dataCount;
            }
            if(target==data[i])
                return true;
            else if(childCount==0)
                return false;
            else
                return subset[i].contains(target);
        
    }
    
    public void add(int element) throws CloneNotSupportedException 
    {
        if(!contains(element))
        {
            looseAdd(element);
            //After loose addition root might have more than maximum(i.e. 2) elements.
            if(dataCount>2)
            {
                IntBalancedSet newRoot = new IntBalancedSet();
                for(int x = 0; x< dataCount; x++)
                {
                    newRoot.data[x] = data[x];
                }
                for(int y=0; y<childCount;y++)
                {
                    newRoot.subset[y] = subset[y];
                }
                newRoot.dataCount = dataCount;
                newRoot.childCount = childCount;
                for(int x = 0; x< dataCount; x++)
                {
                    data[x] = 0;
                }
                dataCount=0;
                childCount=1;
                subset[0] = newRoot;
                subset[1] = subset[2] = subset[3] = null;
                fixExcess(0);
            }
        }
        else
            System.out.println(element + " already exists...");
    }

    
    private void looseAdd(int element) throws CloneNotSupportedException 
    {
        int i = 0;
        for(int j = 0; j<data.length; j++)
        {
            if(data[j]>=element)
            {
                i=j;
                break;
            }
            else
                i=dataCount;
        }
        if(data[i]==element)
        {
            System.out.println("Element already exists...");
        
        }
        else if(childCount==0)
        {
            for(int x = dataCount ; x >= 0 ; x--)
            {
                if(i<x)
                {
                    data[x] = data[x-1];
                }
                else
                {
                    data[i] = element;
                    dataCount++;
                    break;
                }
            }
        }   
        
        else
        {
            subset[i].looseAdd(element);
            if(subset[i].dataCount > MAXIMUM)
                fixExcess(i);
        }
    }
    
    private void fixExcess(int i) throws CloneNotSupportedException 
    {
        if(data[i]!=0)
        {
            for(int x = dataCount ; x >= 0 ; x--)
            {
                if(i<x)
                {
                    data[x] = data[x-1];
                }
                else
                {
                    data[i] = subset[i].data[(subset[i].dataCount/2)];
                    dataCount++;
                    break;
                }
            }
        }
        else
        {
            data[i] = subset[i].data[(subset[i].dataCount/2)];
            dataCount++;
        }
        
        IntBalancedSet leftChild = new IntBalancedSet();
        IntBalancedSet rightChild = new IntBalancedSet();
        leftChild.dataCount = 1;
        rightChild.dataCount = 1;
        //copy = root.subset[i].clone();
        //root.childCount++;
        
        for(int p=0;p<MINIMUM;p++)
        {
            leftChild.data[p] = subset[i].data[p];
            rightChild.data[p] = subset[i].data[p+1+1];
        }
        
        int subCh = (subset[i].childCount/2);
        for(int p=0; p<subCh; p++)
        {
            leftChild.subset[p] = subset[i].subset[p];
            rightChild.subset[p] = subset[i].subset[p+subCh];
        }
        
        if(subCh>0)
        {
            leftChild.childCount = 2;
            rightChild.childCount = 2;
        }
        
        subset[childCount] = new IntBalancedSet();
        for(int p=childCount; p>i; p--)
        {
            subset[p] = subset[p-1];
        }
        
        childCount++;
        subset[i]=leftChild;
        subset[i+1]=rightChild;
        
    }
    
    public void addTree(IntBalancedSet treeB) throws CloneNotSupportedException 
    {
        if(treeB!=null)
        {
            for(int i=0; i<treeB.dataCount; i++)
            {
                add(treeB.data[i]);
            }
        
            for(int i=0; i<childCount; i++)
            {
                addTree(treeB.subset[i]);
            }
        }
        
    }
    
    public int size(int count) throws CloneNotSupportedException 
    {
        
        if(this == null)
            return 0;
        else
        {
            for(int i=0; i<this.dataCount; i++)
            {
                count++;
            }
        
            for(int i=0; i<childCount; i++)
            {
                 count = (this.subset[i]).size(count);
            }
        }
        return count;
    }
    
    public boolean remove(int target) 
    {
        
    }
    
    public void print(int indent)
    {
        final int EXTRA_INDENT = 4;
        int i;
        int space;
        
        for(space=0; space<indent; space++)
        {
            System.out.print(" ");
        }
        for(i=0; i<dataCount; i++)
        {
            System.out.print(data[i] + " ");
        }
        System.out.println();
        
        for(i=0; i<childCount; i++)
        {
            subset[i].print(indent + EXTRA_INDENT);
        }
    }

    

    

    
}
