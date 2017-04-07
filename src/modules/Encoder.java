package modules;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dataStructures.CacheOptimized4WayPriorityQueue;
import dataStructures.MinPriorityQueue;
import dataStructures.Node;
import dataStructures.PriorityQ;
import utils.DataStructureType;
import utils.FrequencyTable;

public class Encoder
{
    public static void main(String[] args)
        throws IOException
    {
        String fileName = args[0];
        
        File inputFile = new File(fileName);
        
        Scanner input = null;
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        input = new Scanner(inputFile);
        
        while(input.hasNextLine())
        {
            String temp = input.nextLine();
            
            if(!temp.isEmpty())
            {
                list.add(Integer.parseInt(temp));
            }
        }
        
        input.close();
        
        FrequencyTable fTable = new FrequencyTable();
        
        HashMap<Integer, Integer> map = fTable.createFrequencyTable(list);
        
        Node root = null;
        
        root = createHuffmanTree(map,
                                 DataStructureType.CACHE_OPTIMIZED_4WAY_BINARYHEAP);
        
        HashMap<Integer, String> codeTableMap = new HashMap<>();
         
        encode(root, "", codeTableMap);
        
        PrintWriter writer = new PrintWriter("code_table.txt", "UTF-8");
        
        for(Map.Entry<Integer, String> entry : codeTableMap.entrySet())
        {
            writer.println(entry.getKey() + " " + entry.getValue());
        }    
        
        writer.close();
        
        createEncodedFile(codeTableMap, fileName);
    }
    
    private static Node createHuffmanTree(
        HashMap<Integer, Integer> map,
        DataStructureType dataStructureType
    )
    {
        PriorityQ queue = null;
        
        int max = -1;
        
        if(dataStructureType == DataStructureType.BINARY_HEAP)
        {
            queue = new MinPriorityQueue();
            max = 1;
        }
        else if(dataStructureType == DataStructureType.CACHE_OPTIMIZED_4WAY_BINARYHEAP)
        {
            queue = new CacheOptimized4WayPriorityQueue();
            max = 4;
        }
        
        //insert into min heap
        
        for(Map.Entry<Integer, Integer> entry : map.entrySet())
        {
            queue.insert(new Node(entry.getKey(), entry.getValue()));
        }
        
        if(queue.size() == 1)
        {
            return queue.removeMin();
        }
        
        while(queue.size() > max)
        {
            Node lChild = queue.removeMin();
        
            Node rChild = queue.removeMin();
            
            Node parent = new Node(Integer.MAX_VALUE,
                                   lChild.getFrequency() +
                                   rChild.getFrequency());
            parent.lChild = lChild;
            parent.rChild = rChild;
            
            queue.insert(parent);
        }
        
        return queue.removeMin();
    }
    
    private static void encode(Node node, String prefix,
                               HashMap<Integer, String> codeTableMap)
    {
        if(null == node.lChild && null == node.rChild)
        {
            codeTableMap.put(node.getNo(), prefix);
            return;
        }
        encode(node.lChild, prefix + "0", codeTableMap);
        encode(node.rChild, prefix + "1", codeTableMap);
        
    }        

    private static void writebinFile(BufferedOutputStream bos,
                                     BufferedReader bis,
                                     HashMap<Integer, String> codeTableMap )
        throws IOException
    {
        ByteArrayOutputStream outputStream =
            new ByteArrayOutputStream();
        
        StringBuilder number = new StringBuilder();
        String line;
        
        while (null != (line = bis.readLine()))
        {
            if (!line.trim().equals(""))
            {
                int index = Integer.parseInt(line);
                
                if (codeTableMap.containsKey(index))
                {
                    String code = codeTableMap.get(index);
                    number.append(code);
                    
                    if(number.length()>=8)
                    {
                        while(number.length() >= 8)
                        {
                            byte[] byteArray =
                                getByteByString(
                                    number.substring(0, 8).toString()
                                );
                            outputStream.write(byteArray);
                            number = number.delete(0, 8);
                        }
                    }
                }
                else
                {
                    System.out.println("not found in table");
                }
            }
        }
        if (number.length() != 0)
        {
            byte[] byteArray =
                getByteByString(number.substring(0, 8).toString());
            outputStream.write(byteArray);
        }
        
        byte[] byteArray = outputStream.toByteArray();
        bos.write(byteArray);
        bos.close();
        bis.close();
      
    }
    
    public static byte[] getByteByString(String binaryString)
    {
        int splitSize = 8;

        if (binaryString.length() % splitSize == 0)
        {
            int index = 0;
            int position = 0;

            byte[] resultByteArray = new byte[binaryString.length() / splitSize];
            
            StringBuilder text = new StringBuilder(binaryString);

            while (index < text.length())
            {
                String binaryStringChunk =
                    text.substring(index,
                                   Math.min(index + splitSize, text.length()));
                Integer byteAsInt = Integer.parseInt(binaryStringChunk, 2);
                resultByteArray[position] = byteAsInt.byteValue();
                index += splitSize;
                position++;
            }
            
            return resultByteArray;
        }
        else
        {
            System.out.println("Invalid string length");
            return null;
        }
    }

    private static void createEncodedFile(HashMap<Integer, String> codeTableMap,
                                          String path)
        throws IOException
    {
        BufferedReader bis = 
            new BufferedReader(new FileReader(path));
        BufferedOutputStream bos =
            new BufferedOutputStream(new FileOutputStream("encoded.bin"));
        writebinFile(bos, bis, codeTableMap);
    }
    
}
