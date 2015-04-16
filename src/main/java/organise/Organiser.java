//package organise;
//
//import stanfordParser.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import languageGenerator.CreateSentence;
//import reddit.Reddit;
//import com.github.jreddit.entity.Comment;
//import com.github.jreddit.entity.Submission;
//
//public class Organiser {
//    public static void main(String[] args) {
//        ArrayList<String> list = new ArrayList<String>();
//
//        //Takes string from ismael()
//        //Main object1 = new Main();
//        //String runner = object1.main(); //Ismaels code
//
//        Reddit reddit = new Reddit("izzy19959","u7f-ozz-nkv-Gkq");
//        List<Submission> submissions =  reddit.getSubmission(1); //returns 1 submission
//        //pass strings to Conor(Parser)
//
//        Parser stanParse = new Parser();
//        CreateSentence simpNlG = new CreateSentence();
//        /*for (Submission s : submissions) {
//        	ArrayList<Token> arrTemp = stanParse.parse(s.);
//        	simpNLG.process(arrTemp);
//        }*/
////        for (Submission sub : submissions) {
////			//System.out.println("SUBMISSION TITLE :"+sub.getTitle() + "\n\n" + "COMMENTS:\n");
////        	ArrayList<Token> arrTemp = stanParse.parse(sub.getTitle());
////        	String ret = simpNlG.process(arrTemp);
////        	System.out.println(ret);
////			String id = sub.getIdentifier();
////			for (Comment c : reddit.getCommentsForSubmission(id)){
////				arrTemp = stanParse.parse(c.getBody());
////				simpNlG.process(arrTemp);
////				ret = c.getBody();
////	        	System.out.println(ret+"\n---------");
////			}
////			//System.out.println();
////		}
//    }
//
//}