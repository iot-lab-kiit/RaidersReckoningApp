# 🤝 Contributing to Raiders' Reckoning

Thank you for your interest in contributing to **Raiders' Reckoning**!  

We welcome contributions from developers, designers, and enthusiasts eager to enhance this gamified event application.  

---

## 🧭 How to Contribute

Follow these steps to contribute effectively:

### 1. Fork the repository
 Navigate to the [RaidersReckoningApp GitHub page](https://github.com/iot-lab-kiit/RaidersReckoningApp) and click the **Fork** button at the top-right corner to create your own copy.

### 2. Clone your fork locally
```bash
git clone https://github.com/<your-username>/RaidersReckoningApp.git
cd RaidersReckoningApp
```
Replace **your-username** with your GitHub username.

### 3. Set Upstream Remote
To keep your fork up to date with the original repository:

```bash
git remote add upstream https://github.com/iot-lab-kiit/RaidersReckoningApp.git
```

### 4. Install Dependencies

Ensure you have Android Studio
 installed. Open the project in Android Studio and sync the project to download all necessary dependencies.

### 5. Create a new branch for your changes
Always create a descriptive branch name for each feature or bug fix:

```bash
git checkout -b feature/your-feature-name
```
### 6. Make your changes
- Follow the existing project structure (MVVM + Clean Architecture)
- Stick to the code style used in the project
- Add comments and documentation for new code

### 7. Stage and commit your changes
```bash
git add .
git commit -m "Add: Short description of your change"
```

### 8. Push your branch to your fork
```bash
git push origin feature/your-feature-name
```

### 9. Submit a Pull Request (PR)
- Go to your fork on GitHub
- Click Compare & Pull Request
- Provide a clear description of your changes
- Reference any related issues (e.g., Fixes #12)

## 🧱 Code Guidelines
- Language: Kotlin
- UI: Jetpack Compose
- Architecture: MVVM + Clean Architecture
- Follow Kotlin style conventions: Kotlin Coding Conventions
- Keep commits clean and descriptive
- Run lint and ensure the app builds successfully before submitting

## 🧪 Testing Your Changes
- Test new features on both emulator and real devices if possible
- Ensure no crashes or major UI issues occur
- Add unit tests for critical business logic where applicable

## 🗣️ Reporting Issues
- If you find a bug or want to request a feature:
- Go to the Issues tab in this repository
- Click New Issue
- Provide a descriptive title and detailed steps to reproduce (if applicable)
- Include screenshots or logs if necessary

## 📚 Additional Guidelines
- Keep pull requests focused on a single feature/fix
- Be respectful and professional in discussions
- Avoid making unrelated formatting changes
- Maintain backward compatibility whenever possible


> We appreciate your interest in contributing to Raiders' Reckoning!
> Thank you for helping us improve this project! 💙
