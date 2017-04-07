package dataStructures;

public class CacheOptimized4WayPriorityQueue extends PriorityQ
{   
    public CacheOptimized4WayPriorityQueue()
    {
        super();        
        // add first 3 elements
        list.add(null);
        list.add(null);
        list.add(null);
    }
    
    public void insert(Node node)
    {
        list.add(node);
        heapify(true);
    }
    
    public Node removeMin()
    {
        if(list.size() < 4)
        {
            throw new RuntimeException(
                "List is empty. Remove min not possible"
            );
        }
        
        Node returnNode = list.get(3);
        
        Node node = list.remove(list.size() - 1);
        
        if(list.size() > 3)
        {
            list.set(3, node);
            heapify(false);
        }
        
        return returnNode;
    }
    
    public Node getMin()
    {
        if(list.isEmpty())
        {
            throw new RuntimeException(
                "List is empty. Get min not possible"
            );
        }
        return list.get(3);
    }
    
    protected void heapify(boolean moveUp)
    {
        int index;
        
        // insert
        if(moveUp)
        {
            index = list.size() - 1;
            
            while(index > 3)
            {
                if(list.get(index).getFrequency() <
                   list.get((index + 8)/ 4).getFrequency())
                {
                    Node temp = list.get(index);
                    list.set(index, list.get((index + 8)/ 4));
                    list.set((index + 8)/ 4, temp);
                    index = (index + 8)/ 4;
                }
                else
                {
                    break;
                }
            }
        }
        // delete
        else
        {
           index = 3;
            
            while(index >= 3 && index < list.size())
            {
                Node min = list.get(index);
                
                int j = -1;
                
                for(int i = 1; i <= 4; i++)
                {
                    int childIndex = 4 * index - (i + 4);
                    
                    if(childIndex < list.size())
                    {
                        if(list.get(childIndex).getFrequency() <
                           min.getFrequency())
                        {
                            min = list.get(childIndex);
                            j = childIndex;
                        }
                    }
                }
                
                if(j != -1)
                {
                    list.set(j, list.get(index));
                    list.set(index, min);
                    index = j;
                }
                else
                {
                    break;
                }
            }
        }
    }
    
    
}
