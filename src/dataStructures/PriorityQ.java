package dataStructures;

import java.util.ArrayList;

public abstract class PriorityQ
{
    protected ArrayList<Node> list;
    
    protected PriorityQ()
    {
        list = new ArrayList<>();
    }
    
    public abstract void insert(Node node);
    
    public abstract Node removeMin();
        
    public int size()
    {
        return list.size();
    }
    
    protected abstract void heapify(boolean moveUp);
}
