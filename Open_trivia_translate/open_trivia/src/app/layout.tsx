import "../styles/QuizGame.css";

export const metadata = {
  title: "Quiz Game",
  description: "A simple quiz game app",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className="min-h-screen flex items-center justify-center bg-gray-100">
        {children}
      </body>
    </html>
  );
}
