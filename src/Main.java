import com.github.jreddit.entity.Submission;

import java.util.List;


public class Main {

	public static void main(String[] args) {
		 System.out.println("BEGIN RUN");
		
		 //test account , and password
		 Reddit reddit = new Reddit("izzy19959","u7f-ozz-nkv-Gkq");
		 List<Submission> submissions =  reddit.getSubmission(1); //returns 1 submission

		 for (Submission sub : submissions) {
			 System.out.println("BEGINNING OF SUBMISSION \n"+sub);
			 System.out.println(reddit.getCommentsForSubmission(sub.getIdentifier()));  //prints out
			 System.out.println("END OF SUBMISSION \n");
		 }
		 
		 
		 System.out.println("END RUN");

	}

}
