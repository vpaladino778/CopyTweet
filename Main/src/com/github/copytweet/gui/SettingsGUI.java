package com.github.copytweet.gui;

import com.github.copytweet.keylistener.TweetKeyListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lc.kra.system.keyboard.GlobalKeyboardHook;

public class SettingsGUI extends Application {

    TextField twitterName;
    HBox nameBox;
    Button verifyButton;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CopyTweet Settings");

        twitterName = new TextField();
        verifyButton = new Button("Verify");
        nameBox = new HBox(twitterName,verifyButton);

        Scene settingsScene = new Scene(nameBox, 200, 200);

        primaryStage.setScene(settingsScene);
        primaryStage.show();


    }

    private static boolean run = true;

    public static void main(String[] args){
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input
        keyboardHook.addKeyListener(new TweetKeyListener());
        try{
            launch(args);
            while (run) Thread.sleep(128);
        } catch(InterruptedException e)
        { }
        finally
        {
            keyboardHook.shutdownHook();
        }
    }
}
