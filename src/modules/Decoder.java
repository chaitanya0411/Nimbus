package modules;
//package dataStructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dataStructures.Node;

public class Decoder
{

    public static void main(String[] args)
        throws IOException
    {
        decode(args[0], args[1]);
    }
    
    private static void decode(String encodedFileName, String codeTableFileName)
        throws IOException
    {
        HashMap<Integer, String> codeTableMap = new HashMap<>();
        
        File inputFile = new File(codeTableFileName);
        
        Scanner input = null;
        
        input = new Scanner(inputFile);
        
        //read file and create map
        while(input.hasNextLine())
        {
            String temp = input.nextLine();
            String parts[] = temp.split(" ");
            
            codeTableMap.put(Integer.parseInt(parts[0]), parts[1]);
        }
        input.close();
        
        
        // construct tree by traversing map
        
        Node root = new Node(Integer.MAX_VALUE, -1);
        
        for(Map.Entry<Integer, String> entry : codeTableMap.entrySet())
        {
            Node node = root;
            
            char[] encodedValue = entry.getValue().toCharArray();
            
            for(char ch : encodedValue)
            {
                if(ch == '0')
                {
                    if(null == node.lChild)
                    {
                        node.lChild = new Node(Integer.MAX_VALUE, -1);
                        
                    }
                    node = node.lChild;
                }
                else
                {
                    if(null == node.rChild)
                    {
                        node.rChild = new Node(Integer.MAX_VALUE, -1);
                        
                    }
                    node = node.rChild;
                }
            }
            node.no = entry.getKey();
        }
        
        //read encoded file and create decoded file
        
        
        String text = getEncodedString(encodedFileName);
        
        Node node = root;
        
        PrintWriter writer = new PrintWriter("decoded.txt", "UTF-8");
        
        for(char ch : text.toCharArray())
        {
            if(ch == '0')
            {
                node = node.lChild;
            }
            else
            {
                node = node.rChild;
            }
            
            if(null == node.lChild && null == node.rChild)
            {
                writer.println(node.getNo());
                node = root;
            }
        }
        writer.close();
    }
    
    public static String getEncodedString(String encodedPath)
        throws IOException, FileNotFoundException
    {
        byte[] bytes;
        StringBuilder outputString = new StringBuilder();
        
        bytes = Files.readAllBytes(new File(encodedPath).toPath());
        
        for(byte b: bytes)
        {
            outputString.
                append(Integer.toBinaryString(b & 255 | 256).substring(1));
        }
        
        return outputString.toString();
    }

}
