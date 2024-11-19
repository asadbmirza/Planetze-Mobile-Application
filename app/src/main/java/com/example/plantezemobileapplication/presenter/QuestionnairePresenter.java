package com.example.plantezemobileapplication.presenter;


import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.JsonParser;
import com.example.plantezemobileapplication.utils.Question;
import com.example.plantezemobileapplication.utils.SpecialAnswer;
import com.example.plantezemobileapplication.utils.SpecialQuestion;
import com.example.plantezemobileapplication.view.questionnaire.QuestionnaireActivity;


import java.util.HashMap;
import java.util.Map;


public class QuestionnairePresenter {
    public Question[] questions;
    public int currQuestionIndex;
    private final double[][] answer5Matrix = {
            {246, 819, 1638, 3071, 4095},
            {573, 1911, 3822, 7166, 9555},
            {573, 1911, 3822, 7166, 9555}
    };

    private final double[][]  consumptionPurchaseMatrix = {
            {0, -54, -108, -180}, // monthly
            {0, -18, -36, -60}, // quarterly
            {0, -15, -30, -50}, // annual
            {0, -0.75, -1.5, -2.5} //rarely
    };

    private final double[][]  consumptionDeviceMatrix = {
            {0, 45, 60, 90}, // 1 device
            {0, 60, 120, 180}, // 2 devices
            {0, 90, 180, 270}, // 3 devices
            {0, 120, 240, 360} //4 or more
    };

    private QuestionnaireActivity view;

    public QuestionnairePresenter() {}

    public QuestionnairePresenter(QuestionnaireActivity view) {
        this.view = view;
        this.questions = initializeQuestions();
        currQuestionIndex = 0;
    }

    private Question[] initializeQuestions() {
        // Transportation questions
        Answer[] options1 = {
                new Answer("Yes", 0),
                new SpecialAnswer("No", 0, 7)
        };
        Question question1 = new Question("Do you own or regularly use a car?", options1, "transportation");

        Answer[] options2 = {
                new Answer("Gasoline", 0.25),
                new Answer("Diesel", 0.27),
                new Answer("Hybrid", 0.16),
                new Answer("Electric", 0.05),
                new Answer("I don't know", 0)
        };
        Question question2 = new Question("What type of car do you drive?", options2, "transportation");

        Answer[] options3 = {
                new Answer("Up to 5,000km", 5000),
                new Answer("5,000-10,000km", 10000),
                new Answer("10,000-15,000km", 15000),
                new Answer("15,000-20,000km", 20000),
                new Answer("20,000-25,000km", 25000),
                new Answer("More than 25,000km", 35000)
        };
        Question question3 = new Question("How many kilometers/miles do you drive per year?", options3, "transportation");

        Answer[] options4 = {
                new SpecialAnswer("Never", 0, 5),
                new Answer("Occasionally (1-2 times/week)", 0),
                new Answer("Frequently (3-4 times/week)", 0),
                new Answer("Always (5+ times/week)", 0),
        };
        Question question4 = new Question("How often do you use public transportation (bus, train, subway)?", options4, "transportation");

        Answer[] options5 = {
                new Answer("Under 1 hour", 0),
                new Answer("1-3 hours", 0),
                new Answer("3-5 hours", 0),
                new Answer("5-10 hours", 0),
                new Answer("More than 10 hours", 0),
        };
        Question question5 = new Question("How much time do you spend on public transport per week (bus, train, subway)?", options5, "transportation");

        Answer[] options6 = {
                new Answer("None", 0),
                new Answer("1-2 flights", 225),
                new Answer("3-5 flights", 600),
                new Answer("6-10 flights", 1200),
                new Answer(" More than 10 flights", 1800),
        };
        Question question6 = new SpecialQuestion("How many short-haul flights (less than 1,500 km / 932 miles) have you taken in the past year?", options6, "transportation", 3);

        Answer[] options7 = {
                new Answer("None", 0),
                new Answer("1-2 flights", 825),
                new Answer("3-5 flights", 2200),
                new Answer("6-10 flights", 4400),
                new Answer(" More than 10 flights", 6600),
        };
        Question question7 = new Question("How many long-haul flights (more than 1,500 km / 932 miles) have you taken in the past year?", options7, "transportation");

        //Food questions
        Answer[] options8 = {
                new SpecialAnswer("Vegetarian", 1000, 12),
                new SpecialAnswer("Vegan", 500, 12),
                new SpecialAnswer("Pescatarian (fish/seafood)", 1500, 12),
                new Answer("Meat-based (eat all types of animal products)", 0),
        };
        Question question8 = new SpecialQuestion("What best describes your diet?", options8, "food", 6);

        Answer[] options9_1 = {
                new Answer("Daily", 2500),
                new Answer("Frequently (3-5 times/week)", 1900),
                new Answer("Occasionally (1-2 times/week)", 1300),
                new Answer("Never", 0),
        };
        Question question9_1 = new Question("How often do you eat beef?", options9_1, "food");

        Answer[] options9_2 = {
                new Answer("Daily", 1450),
                new Answer("Frequently (3-5 times/week)", 860),
                new Answer("Occasionally (1-2 times/week)", 450),
                new Answer("Never", 0),
        };
        Question question9_2 = new Question("How often do you eat pork?", options9_2, "food");

        Answer[] options9_3  = {
                new Answer("Daily", 950),
                new Answer("Frequently (3-5 times/week)", 600),
                new Answer("Occasionally (1-2 times/week)", 200),
                new Answer("Never", 0),
        };
        Question question9_3 = new Question("What often do you eat chicken?", options9_3, "food");

        Answer[] options9_4 = {
                new Answer("Daily", 800),
                new Answer("Frequently (3-5 times/week)", 500),
                new Answer("Occasionally (1-2 times/week)", 150),
                new Answer("Never", 0),
        };
        Question question9_4 = new Question("What often do you eat fish/seafood?", options9_4, "food");

        Answer[] options10 = {
                new Answer("Never", 0),
                new Answer("Rarely", 23.4),
                new Answer("Occasionally", 70.2),
                new Answer("Frequently", 140.4),
        };
        SpecialQuestion question10 = new SpecialQuestion("How often do you waste food or throw away uneaten leftovers?", options10, "food", 11);

        // Housing questions
        Answer[] options11 = {
                new Answer("Detached house", 0),
                new Answer("Semi-detached house", 0),
                new Answer("Townhouse", 0),
                new Answer("Condo/Apartment", 0),
                new Answer("Other", 0),
        };
        Question question11 = new Question("What type of home do you live in?", options11, "housing");

        Answer[] options12 = {
                new Answer("1", 0),
                new Answer("2", 0),
                new Answer("3-4", 0),
                new Answer("5 or more", 0),
        };
        Question question12 = new Question("How many people live in your household?", options12, "housing");

        Answer[] options13 = {
                new Answer("Under 1000 sq. ft.", 0),
                new Answer("1000-2000 sq. ft", 0),
                new Answer("Over 2000 sq. ft", 0),
        };
        Question question13 = new Question(" What is the size of your home?", options13, "housing");

        Answer[] options14 = {
                new Answer("Natural Gas", 0),
                new Answer("Electricity", 0),
                new Answer("Oil", 0),
                new Answer("Propane", 0),
                new Answer("Wood", 0),
                new Answer("Other", 0),
        };
        Question question14 = new Question("What type of energy do you use to heat your home?", options14, "housing");

        Answer[] options15 = {
                new Answer("Under $50", 0),
                new Answer("$50-$100", 0),
                new Answer("$100-$150", 0),
                new Answer("$150-$200", 0),
                new Answer("Over $200", 0),
        };
        Question question15 = new Question("What is your average monthly electricity bill?", options15, "housing");

        Answer[] options16 = {
                new Answer("Natural Gas", 0),
                new Answer("Electricity", 0),
                new Answer("Oil", 0),
                new Answer("Propane", 0),
                new Answer("Wood", 0),
                new Answer("Other", 0),
        };
        Question question16 = new Question("What type of energy do you use to heat water in your home?", options16, "housing");

        Answer[] options17 = {
                new Answer("Yes, primarily (more than 50% of energy use)", -6000),
                new Answer(" Yes, partially (less than 50% of energy use)", -4000),
                new Answer("No", 0),
        };
        Question question17 = new Question("Do you use any renewable energy sources for electricity or heating (e.g., solar, wind)?", options17, "housing");

        // Consumption questions
        Answer[] options18 = {
                new Answer("Monthly", 360),
                new Answer("Quarterly", 120),
                new Answer("Annually", 100),
                new Answer("Rarely", 5),
        };
        Question question18 = new Question("How often do you buy new clothes?", options18, "consumption");

        Answer[] options19 = {
                new Answer("Yes, regularly", 0.5),
                new Answer("Yes, occasionally", 0.33),
                new Answer("No", 1),
        };
        Question question19 = new Question("Do you buy second-hand or eco-friendly products?", options19, "consumption");

        Answer[] options20 = {
                new Answer("None", 0),
                new Answer("1", 300),
                new Answer("2", 600),
                new Answer("3", 900),
                new Answer("4 or more", 1200),
        };
        Question question20 = new Question("How many electronic devices (phones, laptops, etc.) have you purchased in the past year?", options20, "consumption");

        //DEPENDS ON QUESTION 18 AND 19
        Answer[] options21 = {
                new Answer("Never", 0),
                new Answer("Occasionally", 0),
                new Answer("Frequently", 0),
                new Answer("Always", 0),
        };
        Question question21 = new Question(" How often do you recycle?", options21, "consumption");

        return new Question[]{question1, question2, question3, question4, question5, question6, question7, question8, question9_1, question9_2, question9_3, question9_4, question10, question11, question12, question13, question14, question15, question16, question17, question18, question19, question20, question21};
    }

    public void loadQuestion() {
        view.setCategory(questions[currQuestionIndex].getCategory().toUpperCase());
        view.setQuestion(questions[currQuestionIndex].getTitle());
        view.addAnswersToLayout(questions[currQuestionIndex].getAnswers());
    }

    public void handleNextQuestion() {
        int selectedAnswerIndex = questions[currQuestionIndex].getSelectedAnswer();
        // Check if the question skips to another question based on a specific answer
        if (questions[currQuestionIndex].getAnswers()[selectedAnswerIndex] instanceof SpecialAnswer) {
            int nextQuestionIndex = ((SpecialAnswer) questions[currQuestionIndex].getAnswers()[selectedAnswerIndex]).getNextQuestionIndex();
            ((SpecialQuestion) questions[nextQuestionIndex]).setPreviousQuestionIndex(currQuestionIndex);
            currQuestionIndex = nextQuestionIndex;
        }
        else {
            currQuestionIndex++;
        }

    }

    public void handleAnswer(String selectedAnswer) {
        int currAnswerIndex = findAnswerIndex(selectedAnswer, questions[currQuestionIndex]);
        questions[currQuestionIndex].setSelectedAnswer(currAnswerIndex);
    }

    public void handlePreviousQuestion() {
        if (questions[currQuestionIndex] instanceof SpecialQuestion) {
            int prevQuestionIndex = ((SpecialQuestion) questions[currQuestionIndex]).getPreviousQuestionIndex();
            ((SpecialQuestion) questions[currQuestionIndex]).setPreviousQuestionIndex(currQuestionIndex - 1); // Reset previous question redirection
            currQuestionIndex = prevQuestionIndex;

        }
        else {
            currQuestionIndex--;
        }
    }

    public Map<String, Double> calculateQuizResult() {
        Map<String, Double> categoryTotals = new HashMap<>();
        String currCategory;
        double currWeight;
        int selectedAnswer;

        updateAnswerWeight();

        categoryTotals.put("Total", 0.0);
        for (Question question : questions) {
            currCategory = question.getCategory();
            selectedAnswer = question.getSelectedAnswer();
            if (selectedAnswer != -1) {
                currWeight = question.getAnswers()[selectedAnswer].getWeight();

                if (categoryTotals.containsKey(currCategory)) {
                    categoryTotals.put(currCategory, categoryTotals.get(currCategory) + currWeight);
                } else {
                    categoryTotals.put(currCategory, currWeight);
                }
                categoryTotals.put("Total", categoryTotals.get("Total") + currWeight);
            }
        }

        int question19Answer = questions[21].getSelectedAnswer();
        categoryTotals.put("Total", categoryTotals.get("Total") * questions[21].getAnswers()[question19Answer].getWeight());
        return categoryTotals;
    }

    private void updateAnswerWeight() {
        //Question 2 & 3
        int question2Answer = questions[1].getSelectedAnswer();
        int question3Answer = questions[2].getSelectedAnswer();
        if (question2Answer != -1 || question3Answer != -1) {
            questions[2].getAnswers()[question3Answer].setWeight(questions[1].getAnswers()[question2Answer].getWeight() * questions[2].getAnswers()[question3Answer].getWeight());
            questions[1].getAnswers()[question2Answer].setWeight(0);
        }

        //Question 4 & 5
        int question4Answer = questions[3].getSelectedAnswer() - 1;
        int question5Answer = questions[4].getSelectedAnswer() - 1;
        if (question4Answer != -2) {
            questions[4].getAnswers()[question5Answer].setWeight(answer5Matrix[question4Answer][question5Answer]);
        }

        // Housing questions
        int question11Answer = questions[13].getSelectedAnswer() == 4 ? 2 : questions[13].getSelectedAnswer(); // type of home (row), if other then choose townhouse
        int question12Answer = questions[14].getSelectedAnswer(); // # of occupants (row)
        int question13Answer = questions[15].getSelectedAnswer(); // size of home (row)
        int question14Answer = questions[16].getSelectedAnswer() == 5 ? 1 : questions[16].getSelectedAnswer(); // type of energy for water (col), if other then choose electricity
        int question15Answer = questions[17].getSelectedAnswer(); // monthly bill (col)
        int question16Answer = questions[18].getSelectedAnswer() == 5 ? 1 : questions[18].getSelectedAnswer(); // type of energy for electricity/heating (col), if other then choose electricity

        JsonParser jsonReader = new JsonParser(this.view);
        questions[16].getAnswers()[question14Answer].setWeight(jsonReader.getElement("housingValues.json", question11Answer + (4 * question12Answer) + (3 * question13Answer), question14Answer + (5 * question15Answer)));
        if (question14Answer != question15Answer) {
            questions[18].getAnswers()[question16Answer].setWeight(jsonReader.getElement("housingValues.json", question11Answer + (4 * question12Answer) + (3 * question13Answer), question16Answer + (5 * question15Answer)) + 255);
        }
        else {
            questions[18].getAnswers()[question16Answer].setWeight(jsonReader.getElement("housingValues.json", question11Answer + (4 * question12Answer) + (3 * question13Answer), question16Answer + (5 * question15Answer)));
        }

        // Consumption
        int question18Answer = questions[20].getSelectedAnswer();
        int question20Answer = questions[22].getSelectedAnswer();
        int question21Answer = questions[23].getSelectedAnswer();
        questions[23].getAnswers()[question21Answer].setWeight(consumptionPurchaseMatrix[question18Answer][question21Answer] + consumptionDeviceMatrix[question20Answer][question21Answer]);
    }

    public int findAnswerIndex(String answerText, Question question) {
        Answer[] answers = question.getAnswers();
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].getAnswerText().equals(answerText)) {
                return i;
            }
        }
        return -1;
    }
}

