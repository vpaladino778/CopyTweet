package com.github.copytweet.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TweetHandler {
    private static final String OAUTH_CONSUMER_KEY = "lGmqpY8UAN0rEAWz0AWKz4KI6";
    private static final String OAUTH_CONSUMER_SECRET = "dlWIMBeSG3lc5qTbNKHNKjNuBW263jXY6a9YF1h64xoCm2TwPG";
    private static final String OAUTH_TOKEN = "827153527-JBQHVNI4XAVQD4pwqWGNBi0qX5ShGhw9KVfoBpyu";
    private static final String OAUTH_TOKEN_SECRET = "QHn5Mt9hcVZsiZpg15eul4oiMLFuIDPQ3HRgAXFrBd0Gm";

    private static final String DELIMITER = "\n"; //Used for saved tweets
    private String twitterUser;
    private ArrayList<String> tweetList;
    private List<Status> tweets;
    private Twitter twitter;
    private File tweetsFile;
    private Random randomNumGen;
    public TweetHandler(String username){
        tweets = new ArrayList<>();
        tweetList = new ArrayList<String>();
        randomNumGen = new Random();
        changeUser(username);
    }

    private void linkTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
                .setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
                .setOAuthAccessToken(OAUTH_TOKEN)
                .setOAuthAccessTokenSecret(OAUTH_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    private boolean populateTweets(String user)  {
        int pageNum = 1;
        int size;
        boolean notDone = true;
        tweetList.clear();
            Paging page = new Paging(pageNum, 200);
            try {
                tweets = twitter.getUserTimeline(user, page);
                for (Status s : tweets){
                    if(!s.getText().contains("t.co") && !s.getText().contains("@" + user))//Dont add retweets or links
                        tweetList.add(s.getText());
                }

            } catch (TwitterException e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }

    public String getRandomTweet(){
        int randIndex = randomNumGen.nextInt(tweetList.size());
        return tweetList.get(randIndex);
    }

    /**
     * Used to changed the twitter account that we are retrieving tweets from
     * @param user Twitter user to retrieve tweets from
     * @return Returns true if user exists
     */
    public boolean changeUser(String user){
        linkTwitter();
        File saveDirectory = new File("Tweets");
        twitterUser = user;
        if(!saveDirectory.isDirectory()) //Tweet folder doesn't exist, create it
            saveDirectory.mkdir();

        File tweetFile = new File(saveDirectory.getAbsolutePath() + "/" + twitterUser + ".txt" );
        if(tweetFile.exists()){ //Tweet file already exists, load it
            tweetsFile  = tweetFile;
            loadTweets(tweetsFile);
        }else {  //Directory Exists, file doesn't
            if (!populateTweets(user))   //If populateTweets fails, return false
                return false;
            saveTweets(tweetFile);
        }


        return true;
    }

    /**
     * @param file The file to be loaded
     * @return  True is the file was successfully parsed and saved
     */
    private boolean loadTweets(File file){

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){

            tweetList = new ArrayList<>();
            String nextTweet;
            while ((nextTweet = bufferedReader.readLine()) != null){
                tweetList.add(nextTweet);
            }
            bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     *
     * @param tweetFile Name of file to save to
     * @return  True if saving was successful
     */
    private boolean saveTweets(File tweetFile){
        Writer writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            String tweetString = String.join(DELIMITER, tweetList);
            if(!tweetFile.exists()) {
                tweetFile.createNewFile();
            }
            writer = new FileWriter(tweetFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(tweetString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
                try {
                    if(bufferedWriter != null)
                        bufferedWriter.close();
                    if(writer != null)
                        writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }
}
