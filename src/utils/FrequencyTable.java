package utils;

import java.util.ArrayList;
import java.util.HashMap;

public class FrequencyTable
{
    private HashMap<Integer, Integer> map;
    
    public FrequencyTable()
    {
        map = new HashMap<>();
    }
    
    public HashMap<Integer, Integer> createFrequencyTable(
        ArrayList<Integer> list
    )
    {
        // create Map
        for(Integer no : list)
        {
            if(map.containsKey(no))
            {
                map.put(no, map.get(no) + 1);
            }
            else
            {
                map.put(no, 1);
            }
        }
        
        return map;
    }
    
}
