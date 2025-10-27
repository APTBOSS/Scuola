"use client";

import { useState, useEffect } from "react";
import { useQuizGame } from "@/components/useQuizGame";

export default function QuizGamePage() {
  const [language, setLanguage] = useState("it");
  const { 
    question,
    options,
    correctScore,
    totalQuestion,
    result,
    selectedAnswer,
    loading,
    setSelectedAnswer,
    checkAnswer,
    playAgain
  } = useQuizGame({ language });

  return (
    <main className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-6">
      <div className="bg-white p-8 rounded-2xl shadow-lg w-full max-w-xl">
        <h1 className="text-3xl font-bold text-center mb-6 text-purple-700">
          Quiz Game
        </h1>

        {/* Selettore lingua */}
        <div className="flex justify-center mb-4">
          <select
            value={language}
            onChange={(e) => setLanguage(e.target.value)}
            className="border border-gray-300 rounded px-3 py-1 text-sm"
          >
            <option value="en">🇬🇧 English</option>
            <option value="it">🇮🇹 Italiano</option>
            <option value="es">🇪🇸 Español</option>
            <option value="fr">🇫🇷 Français</option>
            <option value="de">🇩🇪 Deutsch</option>
          </select>
        </div>

        {/* Punteggio */}
        <div className="text-center text-purple-700 font-semibold mb-4">
          {correctScore}/{totalQuestion}
        </div>

        {/* Domanda */}
        {loading ? (
          <p className="text-center text-gray-500">Caricamento...</p>
        ) : (
          <>
            <h2
              className="text-xl font-medium text-center mb-4"
              dangerouslySetInnerHTML={{ __html: question?.question || "" }}
            />

            {/* Opzioni */}
            <ul className="space-y-3">
              {options.map((opt, idx) => (
                <li
                  key={idx}
                  onClick={() => setSelectedAnswer(opt)}
                  className={`cursor-pointer rounded-lg border p-3 text-center transition 
                    ${
                      selectedAnswer === opt
                        ? "bg-purple-600 text-white"
                        : "bg-gray-100 hover:bg-gray-200"
                    }`}
                  dangerouslySetInnerHTML={{ __html: opt }}
                />
              ))}
            </ul>
          </>
        )}

        {/* Risultato */}
        {result && <p className="text-center mt-4 font-semibold">{result}</p>}

        {/* Bottoni */}
        <div className="flex justify-center mt-6 space-x-3">
          <button
            onClick={checkAnswer}
            disabled={loading}
            className="bg-purple-600 text-white px-4 py-2 rounded-lg hover:bg-purple-700 disabled:opacity-50"
          >
            Controlla risposta
          </button>
          <button
            onClick={playAgain}
            className="bg-gray-200 px-4 py-2 rounded-lg hover:bg-gray-300"
          >
            Nuova domanda
          </button>
        </div>
      </div>
    </main>
  );
}
