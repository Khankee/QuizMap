package brunel.ac.uk.quizmap.controllers;

import brunel.ac.uk.quizmap.entities.Question;
import brunel.ac.uk.quizmap.entities.Topic;
import brunel.ac.uk.quizmap.repositories.QuestionRepository;
import brunel.ac.uk.quizmap.repositories.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionGenerateController {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    public QuestionGenerateController(TopicRepository topicRepository, QuestionRepository questionRepository) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/choose-questions")
    public String chooseQuestions(@RequestParam("topicName") String topicName, Model model) {
        // Fetch the Topic and associated Questions based on the topicName
        Topic topic = topicRepository.getTopicByTitle(topicName); // Implement this method to fetch the topic by name
        List<Question> questions = topic.getQuestions();

        // Add the Topic and Questions to the model
        model.addAttribute("topic", topic);
        model.addAttribute("questions", questions);

        // Render the "choose-questions" Thymeleaf page
        return "choose-questions";
    }

    @PostMapping("/submit")
    public String submitQuestions(@RequestParam("selectedQuestions") List<Long> selectedQuestionIds) {
        List<Question> selectedQuestions = questionRepository.findAllById(selectedQuestionIds);
        selectedQuestions.forEach(question -> question.setEnable(true));
        questionRepository.saveAll(selectedQuestions);
        return "redirect:/index";
    }
}
