package reddit;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Comments;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.CommentSort;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import java.util.List;

public class Reddit
{
    private String uName ;
    private String pWord;
    private User user;
    private RestClient restClient;
    private final String SUBMISSION_TOPIC = "WorldNews";

    public Reddit(String userName, String password){
        setuName(userName);
        setpWord(password);
        setRestClient(new HttpRestClient());
        setUser(new User(getRestClient(), getuName(), getpWord()));
        getRestClient().setUserAgent("bot/1.0 by name");
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
        Comments coms = new Comments(getRestClient(), getUser());
        List<Comment> commentsSubmission = coms.ofSubmission(subId, null, 0, 8, 20, CommentSort.TOP);
        //System.out.println("Comment.size = " + commentsSubmission.size());
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