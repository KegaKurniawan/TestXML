package com.example.kegafirstapp.testxml;


/**
 * Created by Kega on 7/25/2016.
 */

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;


public class AccessPasswordProtectedURLWithAuthenticator {

    public static void main(String[]args){
        try{
            Authenticator.setDefault(new CustomAuthenticator());
            URL url = new URL("http://jogjabay.cloudapp.net:7047/DynamicsNAV90/WS/CRONUS%20International%20Ltd./Codeunit/Test");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    public static class CustomAuthenticator extends Authenticator {
        protected PasswordAuthentication getPasswordAuthentication() {
            // Get information about the request
            String prompt = getRequestingPrompt();
            String hostname = getRequestingHost();
            InetAddress ipaddr = getRequestingSite();
            int port = getRequestingPort();
            String username = "Administrator";
            String password = "Guru2013";
            // Return the information (a data holder that is used by Authenticator)
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }

}

