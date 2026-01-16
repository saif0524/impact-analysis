# Impact Analysis Tool for Change in Source Code

## Overview

This project is a **static impact analysis tool for Java source code**, developed as an **undergraduate final-year project** for the course **SE-801 (Project)** at the Institute of Information Technology, University of Dhaka.

The tool helps identify **which Java classes may be impacted by a change** by analyzing:
- Git version control history
- Static dependencies between classes
- Method invocation relationships

The work was completed and submitted in **October–November 2016**.

---

## Motivation

Software systems are composed of interacting components. Changes to one component may unintentionally affect others, especially in large or legacy systems where developers may lack prior knowledge of the codebase.

Impact analysis aims to reduce this risk by identifying the **set of software entities potentially affected by a change request**. This project focuses on **class-level impact analysis using static analysis and version control history**.

---

## Approach Summary

The proposed approach consists of three main components:

1. **Change Collector**
2. **Source Code Parser**
3. **Dependency Graph Generator**

The workflow is:

Git Repository
↓
Changed Java Files
↓
Static Source Code Parsing
↓
Class Dependency Extraction
↓
Reverse Dependency Graph (Impact Set)


Only the Java files changed between Git revisions are analyzed, reducing unnecessary computation.

---

## Dependency Model

The tool applies the following rule:

> A class **A** is dependent on class **B** if **A invokes at least one method declared in B**.

Based on this, a **reverse dependency graph** is constructed:

Changed Class → Impacted Classes


This allows the tool to answer:

> *“If this class changes, which other classes are likely to be affected?”*

---

## Implementation Details

- Language: Java
- GUI: Swing
- Git Integration: JGit
- Source Code Parsing: JavaParser (AST-based)
- Analysis Granularity: Class level

The project includes a graphical interface that allows users to:
- Select a Git repository
- Compare commits
- View changed files
- Visualize class dependencies and impact sets

---

## Project Structure

- `ia.changecollector`  
  Handles Git repository access and changed file detection.

- `ia.dependencyresolver`  
  Implements the core class-level dependency and impact analysis logic.

- `ia.gui`  
  Swing-based user interface for visualizing results.

- `ia.historycollector`  
  Intended for historical analysis (partially implemented).

Some additional classes represent experimental or exploratory work conducted during development.

---

## Experimental Evaluation

The tool was evaluated on the following Java projects:

- **Java Blog Aggregator (JBA)**
- **ArgoUML**

The evaluation focused on:
- Accuracy of detected dependencies
- Execution time for dependency extraction

Results showed that the tool correctly identified class dependencies for the tested change histories within acceptable execution time for small to medium-sized projects :contentReference[oaicite:1]{index=1}.

---

## Limitations

- Static analysis only (no runtime or dynamic analysis)
- Class-level granularity (method-level impact not fully implemented)
- Method matching based on names (no overload or polymorphism resolution)
- No external library dependency resolution
- Designed for small to medium-sized projects

These limitations reflect the academic scope and timeframe of the project.

---

## How to Run

1. Open the project in an IDE (e.g., Eclipse)
2. Run:
ia.gui.MainFrame

3. Select a `.git` directory when prompted
4. Enter the number of commits to compare
5. Explore changed files and dependency results via the GUI

---

## Project Status

This project is **complete** as an undergraduate final-year submission and is **not under active development**.

---

## Author

**Saif Uddin Mahmud**  
BSSE 0524  
Institute of Information Technology  
University of Dhaka  

Original Submission: November 15, 2016  
Re-submission: December 14, 2016 :contentReference[oaicite:2]{index=2}

---


## License

This project is intended for academic and educational use.
