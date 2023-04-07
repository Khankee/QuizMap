package brunel.ac.uk.quizmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonCaller {

    public List<String>  runPythonScript_generateQuestions(String extractedText) throws IOException {
        List<String> questions = new ArrayList<>();

        ProcessBuilder processBuilder =
                    new ProcessBuilder("python", "scripts/generate_questions.py",extractedText);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while((line = reader.readLine())!= null){
            if(line.isEmpty())continue;
            if(line.charAt(line.length() - 1) != '?'){
                StringBuilder sb = new StringBuilder(line);
                while(true){
                    line = reader.readLine();
                    if(line.isEmpty())continue;
                    sb.append(" ").append(line);
                    if(line.charAt(line.length() - 1) == '?'){
                        break;
                    }
                }
                line = sb.toString();
            }
            questions.add(line);
        }

        return questions;
    }
}
