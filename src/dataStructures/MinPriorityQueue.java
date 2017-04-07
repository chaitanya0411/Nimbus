package dataStructures;

public class MinPriorityQueue extends PriorityQ
{
    
    public MinPriorityQueue()
    {
        super();
    }
    
    public void insert(Node node)
    {
        list.add(node);
        heapify(true);
    }
    
    public Node removeMin()
    {
        if(list.isEmpty())
        {
            throw new RuntimeException(
                "List is empty. Remove min not possible"
            );
        }
        
        Node returnNode = list.get(0);
        
        Node node = list.remove(list.size() - 1);
        
        if(!list.isEmpty())
        {
            list.set(0, node);
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
        return list.get(0);
    }
    
    protected void heapify(boolean moveUp)
    {
        int index;
        
        // insert
        if(moveUp)
        {
            index = list.size() - 1;
            while(index > 0)
            {
                if(list.get(index).getFrequency() <
                   list.get(index / 2).getFrequency())
                {
                    Node temp = list.get(index);
                    list.set(index, list.get(index / 2));
                    list.set(index / 2, temp);
                    index /= 2;
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
           index = 0;
            
            while(index >= 0 && index < list.size())
            {
                Node min = list.get(index);
                
                int j = -1;
                
                if(2 * index < list.size() &&
                   list.get(2 * index).getFrequency() < min.getFrequency())
                {
                    min = list.get(2 * index);
                    j = 2 * index;
                }
                
                if(2 * index  + 1 < list.size() &&
                   list.get(2 * index + 1).getFrequency() < min.getFrequency())
                {
                    min = list.get(2 * index + 1);
                    j = 2 * index + 1;
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
