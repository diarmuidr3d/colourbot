package reddit;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.Comments;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.CommentSort;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class Reddit
{
    private String uName ;
    private String pWord;
    private User user;
    private RestClient restClient;
    private final String SUBMISSION_TOPIC = "WorldNews";	
    private final String resource = "resource"+File.separatorChar;

    public Reddit(String configFilename){
    	login(configFilename);
    }
    
    private void login(String configFilename) {
    	String userName = null, password = null;
    	try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resource+configFilename)));
			userName = br.readLine().trim();
			password = br.readLine().trim();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	setuName(userName);
        setpWord(password);
        setRestClient(new HttpRestClient());
        setUser(new User(getRestClient(), getuName(), getpWord()));
        getRestClient().setUserAgent("RedditBot/1.0 by CIDR");
        this.connectUser();
    }

    /**
     * takes in the number of submission to return
     * @param numberOfSubmission
     * @return List of the number of submissions
     */
    public List<Submission> getSubmission(int numberOfSubmission) {
        Submissions sub = new Submissions(getRestClient(), getUser());
        List<Submission> submissionsSubreddit = sub.ofSubreddit(SUBMISSION_TOPIC, SubmissionSort.TOP, -1, numberOfSubmission, null, null, true);
        return  submissionsSubreddit;

    }



    public List<Comment> getCommentsForSubmission(String subId){
    	List<Comment> commentsSubmission = null;
    	try {
        Comments coms = new Comments(getRestClient(), getUser());
        commentsSubmission = coms.ofSubmission(subId, null, 0, 8, 20, CommentSort.TOP);
    	}catch (RetrievalFailedException e) {
    		e.printStackTrace();
    	}
        return commentsSubmission;
    }


    private void connectUser() {
        try {
            getUser().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // getters and setters for private variables.


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpWord() {
        return pWord;
    }

    public void setpWord(String pWord) {
        this.pWord = pWord;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }
}