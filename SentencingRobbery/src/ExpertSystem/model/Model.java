package ExpertSystem.model;

import ExpertSystem.parser.QuestionParser;

import java.util.ArrayList;

public class Model {

    private static ArrayList<Question> questions = null;
    private static State state = null;
    private static String title = null;
    private static Question current;

    /**
     * Constructor
     * Calls the reset method if member variables are not set
     */
    public Model() {
        if (questions == null && state == null && title == null) restart();
    }

    /**
     * Method
     * Reads in the questions.xml, sets the member variables
     */
    public void restart() {
        QuestionParser qp = new QuestionParser("knowledgebase/questions.xml");
        questions = qp.getQuestions();
        title = qp.getTitle();
        state = new State();
    }

    /**
     * Method
     * Gets the next question based on the current facts and question prerequisites
     * @param next  True if a new question requested, false if current question requested
     * @return      The requested question
     */
    public Question getQuestion(boolean next) {
        if (next) {
            for (Question question : questions) {
                if (question.meetsRequirements(state.getFacts())) {
                    current = question;
                    return question;
                }
            }
            System.out.println("No more questions");
            return null;
        }
        else {
            return current;
        }
    }

    /**
     * Method
     * Return the title
     * @return  The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method
     * Passes a fact to state to be added
     * @param fact  The fact to add
     */
    public void addFact(Fact fact) {
        state.addFact(fact);
    }

    /**
     * Method
     * Get the current trace of known and inferred facts from the state
     * @return  The trace as a string
     */
    public String getTrace() {
        return state.toString();
    }

    /**
     * Method
     * Get the current calculated sentence from the state
     * @return  The sentence as a string
     */
    public String getSentence() {
        return state.getSentence();
    }
}
