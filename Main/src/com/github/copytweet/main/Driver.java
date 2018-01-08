package com.github.copytweet.main;

import com.github.copytweet.keylistener.TweetKeyListener;
import lc.kra.system.keyboard.GlobalKeyboardHook;

import static javafx.application.Application.launch;


public class Driver {



    private static boolean run = true;

    public static void main(String[] args){
        launch(args);
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input

        TweetKeyListener tListener = new TweetKeyListener();
        keyboardHook.addKeyListener(tListener);
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
