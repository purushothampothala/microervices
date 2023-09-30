package com.purushotham.questionservice.controller;

import com.purushotham.questionservice.dto.QuestionWrapper;
import com.purushotham.questionservice.dto.Response;
import com.purushotham.questionservice.entity.Question;
import com.purushotham.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allquestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public List<Question> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);

    }
    @PostMapping
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);

    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>>getQuestionsForQuiz(
            @RequestParam String categoryName,@RequestParam Integer numQuestions ){
    return questionService.getQuestionsForQuiz(categoryName,numQuestions);

    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>>getQuestionsFromId(@RequestBody List<Integer>questionIds){
    return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response>responses){
    return questionService.getScore(responses);
}

}
