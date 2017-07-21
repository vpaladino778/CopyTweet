package com.github.copytweet.main;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Map.Entry;

public class Driver {



    private static boolean run = true;

    public static void main(String[] args){
        //Configure Twitter
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("your consumer key")
                .setOAuthConsumerSecret("your consumer secret")
                .setOAuthAccessToken("your access token")
                .setOAuthAccessTokenSecret("your access token secret");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();


        // might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input
        System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");
        for(Entry<Long, String> keyboard:GlobalKeyboardHook.listKeyboards().entrySet())
            System.out.format("%d: %s\n",keyboard.getKey(),keyboard.getValue());

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed (GlobalKeyEvent event){
                System.out.println(event);

                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE)
                    run = false;
            }
            @Override public void keyReleased (GlobalKeyEvent event){
                System.out.println(event);
                if(event.getVirtualKeyCode() == 45){
                    //TODO
                }
            }
        });

        try{
            while (run) Thread.sleep(128);
        } catch(InterruptedException e)

        { /* nothing to do here */ }
        finally

        {
            keyboardHook.shutdownHook();
        }
    }

    public static void putClipboard(String copyText){
        StringSelection selection = new StringSelection(copyText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void getRandomTweet(){

    }
}
