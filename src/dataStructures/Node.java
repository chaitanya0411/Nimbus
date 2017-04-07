package dataStructures;

public class Node 
{
    public int no;
    public int frequency;
    public Node lChild;
    public Node rChild;
    
    public Node(int no, int frequency)
    {
        this.no = no;
        this.frequency = frequency;
        this.lChild = null;
        this.rChild = null;
    }

    public int getNo()
    {
        return no;
    }

    public int getFrequency()
    {
        return frequency;
    }
    
}
