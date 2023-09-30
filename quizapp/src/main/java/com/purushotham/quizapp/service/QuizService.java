package com.purushotham.quizapp.service;

import com.purushotham.quizapp.dto.QuestionWrapper;
import com.purushotham.quizapp.dto.Response;
import com.purushotham.quizapp.entity.Question;
import com.purushotham.quizapp.entity.Quiz;
import com.purushotham.quizapp.repository.QuestionRepository;
import com.purushotham.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

     @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        Quiz quiz= new Quiz();
        List<Question> questions= questionRepository.findQuestionByCategory(category,numQ);
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>("SUCCESS",HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quiz= quizRepository.findById(id);
        List<Question> questionsFromDb= quiz.get().getQuestions();
        List<QuestionWrapper>questionsForUser = new ArrayList<>();
        for(Question q:questionsFromDb){
            QuestionWrapper qw= new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz= quizRepository.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right=0;
        int i=0;

        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right ++;
                i ++;

            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }}

