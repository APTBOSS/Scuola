"use client";

import React from "react";
import { useQuizGame } from "./useQuizGame";
import "../styles/QuizGame.css";

const QuizGame: React.FC = () => {
  const {
    question,
    options,
    selectedAnswer,
    correctScore,
    totalQuestion,
    isQuizOver,
    loading,
    result,
    decodeHTML,
    handleSelect,
    checkAnswer,
    restartQuiz,
  } = useQuizGame(5);

  return (
    <div className="flex wrapper">
      <div className="quiz-container">
        <div className="quiz-head">
          <h1 className="quiz-title">Quiz Game</h1>
          <div className="quiz-score flex">
            <span>{correctScore}</span>/<span>{totalQuestion}</span>
          </div>
        </div>

        <div className="quiz-body">
          {loading ? (
            <h2 className="quiz-question">Loading question...</h2>
          ) : (
            <>
              <h2 className="quiz-question">
                {decodeHTML(question?.question || "")}
                <br />
                <span className="category">{question?.category}</span>
              </h2>

              <ul className="quiz-options">
                {options.map((opt, i) => (
                  <li
                    key={i}
                    className={selectedAnswer === opt ? "selected" : ""}
                    onClick={() => handleSelect(opt)}
                  >
                    {i + 1}. <span>{decodeHTML(opt)}</span>
                  </li>
                ))}
              </ul>

              <div
                id="result"
                dangerouslySetInnerHTML={{ __html: result }}
              />
            </>
          )}
        </div>

        <div className="quiz-foot">
          {!isQuizOver ? (
            <button
              type="button"
              id="check-answer"
              onClick={checkAnswer}
              disabled={!selectedAnswer}
            >
              Check Answer
            </button>
          ) : (
            <button type="button" id="play-again" onClick={restartQuiz}>
              Play Again!
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default QuizGame;
