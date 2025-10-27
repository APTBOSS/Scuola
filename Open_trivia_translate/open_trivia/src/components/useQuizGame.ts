"use client";

import { useEffect, useState } from "react";

export interface QuizQuestion {
  category: string;
  question: string;
  correct_answer: string;
  incorrect_answers: string[];
}

interface UseQuizGameProps {
  language?: string; // es. "it" per italiano, "fr" per francese...
}

export function useQuizGame({ language = "en" }: UseQuizGameProps) {
  const [question, setQuestion] = useState<QuizQuestion | null>(null);
  const [options, setOptions] = useState<string[]>([]);
  const [correctScore, setCorrectScore] = useState(0);
  const [totalQuestion, setTotalQuestion] = useState(0);
  const [result, setResult] = useState("");
  const [selectedAnswer, setSelectedAnswer] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  // ✅ Carica e traduce la domanda
  const fetchQuestion = async () => {
    try {
      setLoading(true);
      setResult("");
      setSelectedAnswer(null);

      const res = await fetch(
        "https://opentdb.com/api.php?amount=1&type=multiple"
      );
      const data = await res.json();

      // ✅ Controllo se ci sono risultati
      if (!data.results || data.results.length === 0) {
        setResult("⚠️ Nessuna domanda disponibile. Riprova.");
        return;
      }

      const fetched = data.results[0] as QuizQuestion;

      let translatedQuestion = fetched.question;
      let translatedOptions = [fetched.correct_answer, ...fetched.incorrect_answers];

      // 🌍 Traduzione tramite API locale
      if (language !== "en") {
        const translationRes = await fetch("/api/translate", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            text: [fetched.question, ...translatedOptions],
            targetLang: language,
          }),
        });

        if (translationRes.ok) {
          const { translated } = await translationRes.json();
          const translationsArray = Array.isArray(translated)
            ? translated
            : [translated]; // fallback
          translatedQuestion = translationsArray[0];
          translatedOptions = translationsArray.slice(1);
        }
      }

      const shuffled = [...translatedOptions].sort(() => Math.random() - 0.5);

      setQuestion({
        ...fetched,
        question: translatedQuestion,
        correct_answer:
          translatedOptions[translatedOptions.indexOf(fetched.correct_answer)] ||
          fetched.correct_answer,
      });
      setOptions(shuffled);
    } catch (error) {
      console.error("Errore nel caricamento della domanda:", error);
      setResult("⚠️ Errore nel caricamento della domanda.");
    } finally {
      setLoading(false);
    }
  };
}
