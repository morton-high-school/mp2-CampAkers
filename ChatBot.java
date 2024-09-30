public class ChatBot{

  /*
  * Get a default greeting
  * @return a greeting
  */
  public String getGreeting(){
    return "Hello, let's talk.";
  }

  /*
  * Gives a response to a user statement
  * @param statement
  * @return a response based on the rules given
  */
  public String getResponse(String statement){
    String response = "";
    String x = statement.trim();
    if (x.length()==0){
      response = "Please enter something into the ChatBot!";
    }
    else if(findKeyword(x, " no ", 0)>=0){
      response = "Why so negative?";
    }else if(findKeyword(x, "mother", 0)>=0 ||  findKeyword(x, "father", 0)>=0 || findKeyword(x, "sister", 0)>=0 || findKeyword(x, "brother", 0)>=0){
      response = "Tell me more about your family.";
    }else if (findKeyword(x, "dog", 0)>=0 || findKeyword(x, "cat", 0)>=0){
      response = "Tell me more about your pets.";
    }else if(findKeyword(x, "Mr. Zeller", 0)>=0){
      response = "Mr. Zeller?! I love that guy! He's so gracious and benevolent!";
    }else if(findKeyword(x, "rain", 0)>=0){
      response = "I love playing in the puddles when it rains!";
    }else if(findKeyword(x, "sudoku", 0)>=0){
      response = "Sudoku is really hard, but so enjoyable!";
    }else if(findKeyword(x,"I want to",0)>=0){
      response = transformIWantToStatement(x);
    }else if(findKeyword(x,"you",0)>=0 && findKeyword(x,"me",(x.indexOf("you")+3))>=0){
      response = transformYouMeStatement(x);
    }else if(findKeyword(x,"I",0)>=0 && findKeyword(x,"you",(x.indexOf("I")+1))>=0){
      response = transformIYouStatement(x);
    }else{
      response = getRandomResponse();
    }
    return response;
  }

  /*
  * Pick a default response to use if nothing else fits.
  * @return a non-commital string
  */
  private String getRandomResponse(){
    int numberOfResponses = 6;
    double r = Math.random();
    int whichResponse = (int)(r*numberOfResponses);
    String response = "";

    if(whichResponse==0){
      response = "Interesting, tell me more.";
    }else if(whichResponse==1){
      response = "Hmmm.";
    }else if(whichResponse==2){
      response = "Do you really think so?";
    }else if(whichResponse==3){
      response = "You don't say.";
    }else if(whichResponse==4){
      response = "Wow!.";
    }else if(whichResponse==5){
      response = "That is curious.";
    }
    return response;
  }

  /*
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
  private int findKeyword(String statement, String goal, int startPos){
    String phrase = statement.trim().toLowerCase();
    goal = goal.toLowerCase();

    int psn = phrase.indexOf(goal, startPos);

    while(psn>=0){
      String before = " ";
      String after = " ";
      if(psn>0){
        before = phrase.substring(psn-1, psn);
      }
      if(psn+goal.length()<phrase.length()){
        after = phrase.substring(psn+goal.length(), psn+goal.length()+1);
      }
      if(((before.compareTo("a")<0) || (before.compareTo("z")>0)) && ((after.compareTo("a")<0) || (after.compareTo("z")>0))){
        return psn;
      }

      psn = phrase.indexOf(goal, psn+1);
    }

    return -1;
  }

  /**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no"). The search
	 * begins at the beginning of the string.
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal){
		return findKeyword(statement, goal, 0);
	}

  /*
	 * Take a statement with "I want to <something>." and transform it into
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement){
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".")){
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

  /*
	 * Take a statement with "you <something> me" and transform it into
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement){
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".")){
			statement = statement.substring(0, statement.length() - 1);
		}

		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);

		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}






  private String transformIYouStatement(String statement){
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".")){
			statement = statement.substring(0, statement.length() - 1);
		}

		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou1 = findKeyword (statement, "you", psnOfI + 1);

		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou1).trim();
		return "Why do you " + restOfStatement + " me?";
	}
}
