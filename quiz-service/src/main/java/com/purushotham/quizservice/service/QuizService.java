package com.purushotham.quizservice.service;

import com.purushotham.quizservice.dto.QuestionWrapper;
import com.purushotham.quizservice.dto.Response;
import com.purushotham.quizservice.entity.Quiz;
import com.purushotham.quizservice.feign.QuizInterface;
import com.purushotham.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

     @Autowired
     QuizRepository quizRepository;
     @Autowired
     QuizInterface quizinterface;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions= quizinterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);
       return new ResponseEntity<>("SUCCESS",HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
    Quiz quiz= quizRepository.findById(id).get();
    List<Integer> questionIds=quiz.getQuestionIds();
    quizinterface.getQuestionsFromId(questionIds);
    ResponseEntity<List<QuestionWrapper>>questions=quizinterface.getQuestionsFromId(questionIds);
    return questions;
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

          ResponseEntity<Integer> score=  quizinterface.getScore(responses);
        return score;
    }}

