package brunel.ac.uk.quizmap.controllers;

import brunel.ac.uk.quizmap.PythonCaller;
import brunel.ac.uk.quizmap.entities.Question;
import brunel.ac.uk.quizmap.entities.Topic;
import brunel.ac.uk.quizmap.repositories.TopicRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    private final TopicRepository topicRepository;

    public FileUploadController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /*
    Text extraction: Use a library Apache PDFBox to extract text from the PDF files.

    Text preprocessing: Clean and preprocess the extracted text to remove any irrelevant characters,
    symbols, or formatting. Following preprocessing techniques:

        Tokenization: Breaking the text into sentences and words.
        Lowercasing: Converting all text to lowercase.
        Stop-word removal: Removing common words like 'and', 'is', 'in', etc.
        Lemmatization: Reducing words to their base or dictionary form.
        Sentence segmentation: Breaking the preprocessed text into sentences.

        Use an NLP libraries are:
            -SpaCy,
            -NLTK,
            -Rake

    Question generation: For each sentence, questions generated using NLP techniques.
        There are several ways to approach this:

        Template-based: Creating templates for different question types
            (e.g., "What is...", "How does...", "When did...") and fill in the relevant information extracted from the sentence.
        Syntax-based: Analyzing the syntactic structure of the sentence and transform it into a question by rearranging words, changing verb forms, or adding question words.
        Machine learning: Training a machine learning model using a dataset of text and corresponding questions.
        Post-processing: Filtering and ranking the generated questions based on their quality, relevance, and difficulty level.

        //You can use heuristic rules or machine learning models for this purpose.//*/
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String topicName = file.getOriginalFilename();
        assert topicName != null;
        topicName = topicName.substring(0, topicName.length() - 4); // .pdf deleted

        //In case database has this file
        if (topicRepository.existsTopicByTitle(topicName)) {
            return "redirect:/test?error";
        }

        Topic topic = new Topic();
        topic.setTitle(topicName);

        String extractedText;

        try {
            extractedText = extractTextFromPDF(file);
            //processText(extractedText);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/test?error";
        }

        List<String> str_questions = new PythonCaller().runPythonScript_generateQuestions(extractedText);
        List<Question> questions = new ArrayList<>();

        for (String question : str_questions) {
            Question quest = new Question();
            quest.setQuestion(question);
            quest.setTopic(topic);
            questions.add(quest);
        }

        topic.setQuestions(questions);
        topicRepository.save(topic);

        return "redirect:/choose-questions?topicName=" + topicName;
    }

    public static String extractTextFromPDF(MultipartFile file) throws IOException {
        // Get the InputStream from the MultipartFile
        InputStream inputStream = file.getInputStream();

        // Load the PDF document
        PDDocument document = PDDocument.load(inputStream);

        // Create a PDFTextStripper instance
        PDFTextStripper textStripper = new PDFTextStripper();

        // Extract text from the PDF document
        String extractedText = textStripper.getText(document);

        document.close();

        return extractedText;
    }

    private static void processText(String text) {
        // Implemented logic of text printing
        String[] lines = text.split("\n");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
