import 'dart:math';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Hızlı Matematik',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Hızlı Matematik Oyunu'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int num1 = 0;
  int num2 = 0;
  int correctAnswer = 0;
  List<int> answerOptions = [];

  @override
  void initState() {
    super.initState();
    generateQuestion();
  }

  void generateQuestion() {
    final random = Random();
    num1 = random.nextInt(10) + 1; // 1-10 arası
    num2 = random.nextInt(10) + 1; // 1-10 arası
    correctAnswer = num1 + num2;

    int wrongAnswer;
    do {
      // Yanlış cevap doğru cevaptan farklı olmalı
      wrongAnswer = correctAnswer + random.nextInt(5) - 2;
    } while (wrongAnswer == correctAnswer);

    answerOptions = [correctAnswer, wrongAnswer];
    answerOptions.shuffle(); // Şıkları karıştır

    setState(() {});
  }

  void checkAnswer(int selectedAnswer) {
    bool isCorrect = selectedAnswer == correctAnswer;

    final snackBar = SnackBar(
      content: Text(isCorrect ? 'Tebrikler!' : 'Bilemediniz!'),
      duration: const Duration(seconds: 1),
    );

    ScaffoldMessenger.of(context).showSnackBar(snackBar).closed.then((_) {
      generateQuestion();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              '$num1 + $num2 = ?',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 30),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: answerOptions.map((option) {
                return ElevatedButton(
                  onPressed: () => checkAnswer(option),
                  child: Text('$option'),
                );
              }).toList(),
            ),
          ],
        ),
      ),
    );
  }
}
