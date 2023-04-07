package brunel.ac.uk.quizmap.controllers;

import brunel.ac.uk.quizmap.entities.Question;
import brunel.ac.uk.quizmap.entities.Topic;
import brunel.ac.uk.quizmap.repositories.QuestionRepository;
import brunel.ac.uk.quizmap.repositories.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainPageController {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    public MainPageController(TopicRepository topicRepository, QuestionRepository questionRepository) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/index")
    public String showTopics(Model model) {
        List<Topic> topics = topicRepository.findAll();
        model.addAttribute("topics", topics);
        return "index";
    }

    @GetMapping("/topic/{title}")
    public String showQuestions(@PathVariable("title") String title, Model model) {
        Topic topic = topicRepository.getTopicByTitle(title);

        if (topic != null) {
            List<Question> questions = questionRepository.findByTopic(topic);
            List<Question> result = new ArrayList<>();
            for(Question question : questions){
                if(question.isEnable()) result.add(question);
            }
            model.addAttribute("topic", topic);
            model.addAttribute("questions", result);
            return "show-selected-questions";
        } else {
            return "redirect:/index";
        }
    }


}
