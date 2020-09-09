package server.Events;

import connections.Database.MYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import tools.Pair;
import tools.RandomStream.Randomizer;

public class MapleOxQuizFactory {

    private final Map<Pair<Integer, Integer>, MapleOxQuizEntry> questionCache = new HashMap<>();
    private static final MapleOxQuizFactory instance = new MapleOxQuizFactory();

    public MapleOxQuizFactory() {
        initialize();
    }

    public static MapleOxQuizFactory getInstance() {
        return instance;
    }

    public Entry<Pair<Integer, Integer>, MapleOxQuizEntry> grabRandomQuestion() {
        final int size = questionCache.size();
        while (true) {
            for (Entry<Pair<Integer, Integer>, MapleOxQuizEntry> oxquiz : questionCache.entrySet()) {
                if (Randomizer.nextInt(size) == 0) {
                    return oxquiz;
                }
            }
        }
    }

    private void initialize() {
        try {
            Connection con = MYSQL.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM wz_oxdata");
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    questionCache.put(new Pair<>(rs.getInt("questionset"), rs.getInt("questionid")), get(rs));
                }
            }
            con.close();
        } catch (Exception e) {
        }
    }

    private MapleOxQuizEntry get(ResultSet rs) throws SQLException {
        return new MapleOxQuizEntry(rs.getString("question"), rs.getString("display"),
                getAnswerByText(rs.getString("answer")), rs.getInt("questionset"), rs.getInt("questionid"));
    }

    private int getAnswerByText(String text) {
        if (text.equalsIgnoreCase("x")) {
            return 0;
        } else if (text.equalsIgnoreCase("o")) {
            return 1;
        } else {
            return -1;
        }
    }

    public static class MapleOxQuizEntry {

        private String question, answerText;
        private int answer, questionset, questionid;

        public MapleOxQuizEntry(String question, String answerText, int answer, int questionset, int questionid) {
            this.question = question;
            this.answerText = answerText;
            this.answer = answer;
            this.questionset = questionset;
            this.questionid = questionid;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswerText() {
            return answerText;
        }

        public int getAnswer() {
            return answer;
        }

        public int getQuestionSet() {
            return questionset;
        }

        public int getQuestionId() {
            return questionid;
        }
    }
}
