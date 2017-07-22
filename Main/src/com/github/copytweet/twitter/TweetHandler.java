package com.github.copytweet.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TweetHandler {
    private static final String OAUTH_CONSUMER_KEY = "lGmqpY8UAN0rEAWz0AWKz4KI6";
    private static final String OAUTH_CONSUMER_SECRET = "dlWIMBeSG3lc5qTbNKHNKjNuBW263jXY6a9YF1h64xoCm2TwPG";
    private static final String OAUTH_TOKEN = "827153527-f7Q2jBnzthklqyP0cpPbL1v06uF2MezZ0GVGSpdQ";
    private static final String OAUTH_TOKEN_SECRET = "dmtBFMwRnhBmvaouHuwWV6mYWeNJ5zN3jKsoqASwbzXls";


    private String twitterUser;
    private List<Status> tweets;
    private Twitter twitter;

    public TweetHandler(String username){
        twitterUser = username;
        tweets = new ArrayList<>();
        linkTwitter();

        try{
            populateTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
            //Twitter authentication failed
        }
    }

    public void linkTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("OAUTH_CONSUMER_KEY")
                .setOAuthConsumerSecret("OAUTH_CONSUMER_SECRET")
                .setOAuthAccessToken("OAUTH_TOKEN")
                .setOAuthAccessTokenSecret("OAUTH_TOKEN_SECRET");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public void populateTweets() throws TwitterException {
        int pageNum = 1;
        int size;
        boolean notDone = true;

        while(notDone) {
            size = tweets.size();
            Paging page = new Paging(pageNum, 100);
            tweets.addAll(twitter.getUserTimeline(twitterUser, page));
            if (tweets.size() == size) {
                notDone = false;
            }
        }
    }

    public String getRandomTweet(){
        int randIndex = (int)(tweets.size() * Math.random());
        return tweets.get(randIndex).getText();
    }
}