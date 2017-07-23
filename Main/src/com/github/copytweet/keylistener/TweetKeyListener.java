package com.github.copytweet.keylistener;

import com.github.copytweet.twitter.TweetHandler;
import com.github.copytweet.utils.Utilities;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

public class TweetKeyListener implements GlobalKeyListener {

    private int copyKeyCode;
    private String userName;
    private TweetHandler tweetHandler;

    public TweetKeyListener(){
        super();
        copyKeyCode = 45; //Default key is insert
        userName = "GOONS_TXT";

        tweetHandler = new TweetHandler(userName);
    }


    @Override
    public void keyPressed(GlobalKeyEvent globalKeyEvent) {
        System.out.println(globalKeyEvent);

    }

    @Override
    public void keyReleased(GlobalKeyEvent globalKeyEvent) {
        System.out.println(globalKeyEvent);
            if(globalKeyEvent.getVirtualKeyCode() == copyKeyCode){
                Utilities.putClipboard(tweetHandler.getRandomTweet());
            }
    }
}
