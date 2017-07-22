package com.github.copytweet.main;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;



public class Driver {



    private static boolean run = true;

    public static void main(String[] args){
        //Configure Twitter
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("lGmqpY8UAN0rEAWz0AWKz4KI6")
                .setOAuthConsumerSecret("dlWIMBeSG3lc5qTbNKHNKjNuBW263jXY6a9YF1h64xoCm2TwPG")
                .setOAuthAccessToken("827153527-f7Q2jBnzthklqyP0cpPbL1v06uF2MezZ0GVGSpdQ")
                .setOAuthAccessTokenSecret("dmtBFMwRnhBmvaouHuwWV6mYWeNJ5zN3jKsoqASwbzXls");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();


        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input
        java.util.List<Status> tweetList = getTweetList();
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed (GlobalKeyEvent event){
                System.out.println(event);

                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE)
                    run = false;
            }
            @Override public void keyReleased (GlobalKeyEvent event){
                System.out.println(event);
                if(event.getVirtualKeyCode() == 45){
                    Status randTweet = (Status) getRandomFromList(tweetList);
                    putClipboard(randTweet.getText());
                }
            }
        });
        try{
            while (run) Thread.sleep(128);
        } catch(InterruptedException e)
        { }
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

    public static java.util.List getTweetList(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("lGmqpY8UAN0rEAWz0AWKz4KI6")
                .setOAuthConsumerSecret("dlWIMBeSG3lc5qTbNKHNKjNuBW263jXY6a9YF1h64xoCm2TwPG")
                .setOAuthAccessToken("827153527-f7Q2jBnzthklqyP0cpPbL1v06uF2MezZ0GVGSpdQ")
                .setOAuthAccessTokenSecret("dmtBFMwRnhBmvaouHuwWV6mYWeNJ5zN3jKsoqASwbzXls");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        //First param of Paging() is the page number, second is the number per page (this is capped around 200 I think.
        Paging paging = new Paging(1, 100);
        java.util.List<Status> statuses = null;
        try {

            statuses = twitter.getUserTimeline("GOONS_TXT",paging);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    public static Object getRandomFromList(java.util.List list){
        int randIndex =(int) (list.size() * Math.random());
        Object randItem = list.get(randIndex);
        return randItem;
    }
}
