package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.utils.AnswerUtils;
import com.jeff_media.quizbot.utils.MessageBuilder;
import com.jeff_media.quizbot.utils.YamlUtils;
import com.jeff_media.quizbot.config.Config;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The type Game.
 */
public class Game {

    private final QuizBot bot;

    @Getter private final String name;
    @Getter private final List<String> authors;
    @Getter private final List<Question> questions;
    @Getter private final TextChannel channel;
    @Getter private final Member starter;
    @Getter private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    @Getter private final Map<Question, Member> previousQuestions = new HashMap<>();
    @Getter private final Map<Member,GameStat> stats = new HashMap<>();

    @Getter private CompletableFuture<Message> lastQuestionMessage;
    @Getter private ScheduledFuture<?> questionTimeOverTask;
    @Getter private Question currentQuestion;

    public Game(QuizBot bot, String name, List<String> authors, List<Question> questions, TextChannel channel, Member starter) {
        this.bot = bot;
        this.name = name;
        this.authors = authors;
        this.questions = questions;
        this.channel = channel;
        this.starter = starter;

        Collections.shuffle(questions);
        if(bot.getConfig().distributeQuestionsEvenly()) {
            questions.sort(QuestionDistributionComparator.instance());
        }
    }

    public void start() {
        new MessageBuilder(channel).description(starter.getAsMention() + " started quiz **" + name + "** by " + String.join(", ", authors)).send().whenComplete((success, error) -> channel.sendTyping().queue());

        nextQuestion();
    }

    public static Game fromCategory(QuizBot bot, String fileName, TextChannel channel, Member starter) throws CategoryNotFoundException {
        try {
            return fromNativeCategory(bot, fileName, channel, starter);
        } catch (FileNotFoundException ignored) {

        }

        try {
            return fromRedCategory(bot, fileName, channel, starter);
        } catch (FileNotFoundException ignored) {

        }

        throw new CategoryNotFoundException();
    }

    /**
     * Instantiates a new Game.
     *
     * @param fileName the file name
     * @param channel  the channel
     * @param starter  the starter
     * @throws CategoryNotFoundException the category not found exception
     */
    public static Game fromNativeCategory(QuizBot bot, String fileName, TextChannel channel, Member starter) throws FileNotFoundException {
            Config config = new Config("categories/" + fileName + ".yml");
            String name = YamlUtils.getString(config, "name");
            List<String> authors = YamlUtils.getStringList(config,"authors");
            List<Question> questions = new ArrayList<>();
            List<Map<String,Object>> questionMap = (List<Map<String, Object>>) config.get("questions");
            for(Map<String,Object> map : questionMap) {
                questions.add(new Question(map));
            }

            //channel.sendMessage(starter.getEffectiveName() + " started quiz " + name).queue(success -> {channel.sendTyping().queue();});
            return new Game(bot,name,authors,questions,channel,starter);
    }

    public static Game fromRedCategory(QuizBot bot, String fileName, TextChannel channel, Member starter) throws FileNotFoundException{
            Config config = new Config("categories/red/" + fileName + ".yml");
            String name = fileName;
            List<String> authors =  List.of((String) config.get("AUTHOR"));
            List<Question> questions = new ArrayList<>();
            for(Map.Entry<String, Object> entry : config.entrySet()) {
                if(entry.getKey().equalsIgnoreCase("AUTHOR")) continue;
                questions.add(new Question(entry.getKey(), new Answer(entry.getValue())));
            }
            return new Game(bot,name,authors,questions,channel,starter);
    }

    public static class CategoryNotFoundException extends Exception {

    }

    public void nextQuestion() {
        System.out.println("nextQuestion called");
        //this.currentQuestion = null;

        if(questions.size() > 0 && !isWinThresholdReached()) {
            System.out.println("There are " + questions.size() + " questions left. Sending the next one in 5 seconds");
            channel.sendTyping().queue(success -> {channel.sendTyping().queue();});
            executor.schedule(() -> {
                this.currentQuestion = questions.get(0);
                QuestionDistributionComparator.raiseFrequency(currentQuestion);
                System.out.println("Sending question " + currentQuestion.getQuestion());
                questions.remove(0);
                broadcastQuestion();

                questionTimeOverTask = executor.schedule(() -> {
                    try {
                        //channel.sendMessage("Noone? The correct answer would have been: " + currentQuestion.getAnswer().getCorrectAnswerDisplay()).queue();
                        new MessageBuilder(channel)
                                .description(AnswerUtils.getYoureAllNoobResponse(currentQuestion.getAnswer().getCorrectAnswerDisplay()))
                                .replyTo(lastQuestionMessage)
                                .send();
                        questionTimeOverTask = null;
                        previousQuestions.put(currentQuestion, null);
                        currentQuestion = null;
                        System.out.println("Noone had the correct answer.");
                        nextQuestion();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, bot.getConfig().getTimePerQuestion(), TimeUnit.SECONDS);

            }, bot.getConfig().getTypingDurationPerQuestion(), TimeUnit.SECONDS);

        } else {
            //System.out.println("No questions left. The game is finished");
            gameFinished();
        }
    }

    private boolean isWinThresholdReached() {
        int threshold = bot.getConfig().getWinThreshold();
        if(threshold <= 0) return false;
        return stats.values().stream().anyMatch(stat -> stat.getCorrectAnswers() >= threshold);
    }

    private void gameFinished() {
        bot.getRunningGames().remove(channel);
        List<GameStat> winners = stats.values().stream().sorted().limit(3).toList();
        if(winners.size() == 0) {
            new MessageBuilder(channel)
                    .title("Quiz ended").description("No one answered any question correctly lol.")
                    .embed(true)
                    .send();
        } else {
            AtomicInteger place = new AtomicInteger(1);
            String leaderBoard = winners.stream().map(stat -> {
                int amount = stat.getCorrectAnswers();
                String answerWord = amount == 1 ? "answer" : "answers";
                return "**#" + place.getAndIncrement() + "** " + stat.getMember().getAsMention() + " (" + stat.getCorrectAnswers() + " correct " + answerWord + ")";
            }).collect(Collectors.joining("\n"));
            new MessageBuilder(channel)
                    .title("Quiz ended").description(leaderBoard)
                    .embed(true)
                    .send();
        }
    }

    private void broadcastQuestion() {
        int questionNumber = previousQuestions.size() + 1;
        //channel.sendMessage("**Question #" + questionNumber + "**\n" + currentQuestion.getQuestion()).queue(message -> {lastQuestionMessage = message;}, error -> {lastQuestionMessage = null;});
        lastQuestionMessage = new MessageBuilder(channel)
                .title("Question #" + questionNumber)
                .description(currentQuestion.getQuestion())
                .send();
    }

    public void handleMessage(Message message) {
        if(currentQuestion == null) return;
        if(currentQuestion.isCorrectAnswer(message)) {
            stats.computeIfAbsent(message.getMember(), GameStat::new).registerCorrectAnswer();
            if(questionTimeOverTask != null) {
                questionTimeOverTask.cancel(true);
                questionTimeOverTask = null;
            }
            new MessageBuilder(channel)
                    .description(AnswerUtils.getCorrectResponse())
                    .replyTo(message)
                    .send();
            System.out.println(message.getMember().getEffectiveName() + " correctly answered: " + message.getContentRaw());
            previousQuestions.put(currentQuestion, message.getMember());
            currentQuestion = null;
            nextQuestion();
        } else {
            System.out.println(message.getMember().getEffectiveName() + " wrongly answered: " + message.getContentRaw());
        }
    }
}
