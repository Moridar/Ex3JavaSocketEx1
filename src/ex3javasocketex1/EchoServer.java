/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex3javasocketex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Bobbie
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {

        int portNumber = 8080;

        try (
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt("" + portNumber));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(svar(inputLine) == null) clientSocket.close();
                out.println(svar(inputLine));
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private static String svar(String input) {
        if (input.startsWith("UPPER#")) {
            return input.substring(6).toUpperCase();
        }

        if (input.startsWith("LOWER#")) {
            return input.substring(6).toLowerCase();
        }
        if (input.startsWith("REVERSE#")) {
            input = input.substring(8);
            String newStr = "";
            while (!input.isEmpty()) {
                newStr += input.charAt(input.length()-1);
                input = input.substring(0,input.length()-1);
            }
            return newStr;
        }
        if (input.startsWith("TRANSLATE#")){
            input = input.substring(10);
            return translate(input);
            
        }
        return null;
    }
    
    private static String translate(String input){
        HashMap<String,String> HM = new HashMap();
        HM.put("hund", "dog");
        HM.put("dør", "door");
        HM.put("mand", "man");
        HM.put("bil", "car");
        
        if(HM.containsKey(input)) return HM.get(input);
        return "#NOT_FOUND";
    }
}
