# 🎮 Connect Four (JavaFX)

This project is a GUI-based implementation of the classic **Connect Four game**, developed using **JavaFX** following the **MVC (Model-View-Controller)** architecture and design patterns such as **Command** and **Strategy**.

---
https://youtu.be/7G42Y9fuXPg
## 🎥 Demo Video

[![Watch the demo](https://img.youtube.com/vi/7G42Y9fuXPg/0.jpg)](https://www.youtube.com/watch?v=7G42Y9fuXPg)

## 📸 Screenshots
<img width="627" height="867" alt="image" src="https://github.com/user-attachments/assets/9f9f9a16-b0ef-4e38-803a-6f3134228d97" />



## 🚀 Features

- 🎯 Fully playable Connect Four game
- 🧠 Multiple opponent strategies:
  - Human
  - Random
  - Greedy
  - Defensive
  - First Available
- 🔁 Undo / Redo functionality (Command Pattern)
- 🎨 Interactive GUI using JavaFX
- 📊 Move history tracking
- 🧩 MVC architecture implementation

---

## 🏗️ Architecture

This project follows **MVC design pattern**:

### 🔹 Model
- `ConnectFour.java`
- `ConnectFourBoard.java`
- Handles game logic, moves, and rules

### 🔹 View / Controller
- `BoardView.java`
- `CellView.java`
- `ControlPanel.java`
- `ConnectFourApplication.java`
- Handles UI and user interaction

---

## 🧠 Design Patterns Used

- **MVC (Model-View-Controller)**  
- **Command Pattern**
  - `DropTokenCommand`
  - `CommandManager`
- **Strategy Pattern**
  - Different AI players (Random, Greedy, Defensive)

---

---

## ⚙️ Requirements

- Java JDK 21
- JavaFX SDK 21

---

## ▶️ How to Run

### Option 1: Using IDE (Recommended)
1. Open project in Eclipse / IntelliJ
2. Configure JavaFX SDK
3. Add VM options:

``bash
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml

## Run
ConnectFourApplication.java

Option 2: Command Line
javac --module-path /path/to/javafx/lib --add-modules javafx.controls *.java
java --module-path /path/to/javafx/lib --add-modules javafx.controls ConnectFourApplication
