package com.github.copytweet.main;

import com.github.copytweet.keylistener.TweetKeyListener;
import lc.kra.system.keyboard.GlobalKeyboardHook;



public class Driver {



    private static boolean run = true;

    public static void main(String[] args){
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input
        keyboardHook.addKeyListener(new TweetKeyListener());
        try{
            while (run) Thread.sleep(128);
        } catch(InterruptedException e)
        { }
        finally
        {
            keyboardHook.shutdownHook();
        }
    }

}
