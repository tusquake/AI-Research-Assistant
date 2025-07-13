
# 🧠 AI Research Assistant – Chrome Extension

**IntellAINote** is a Chrome extension that helps users summarize selected text from web pages and save research notes locally using the power of AI. Designed for students, researchers, and knowledge workers who want quick, smart, and clean summarization right in the browser.

---

## 🚀 Features

- 🔍 **Text Summarization**: Select any text on a webpage and click "Summarize" to get a quick AI-generated summary.
- 📝 **Research Notes**: Save your own notes for later reference directly inside the extension.
- 💾 **Local Storage**: Notes are stored locally using Chrome's storage API.
- ⚡ **Seamless UX**: Loading indicators, success messages, and a clean UI for smooth interaction.

---

## 📷 Screenshots

<img width="1920" height="831" alt="image" src="https://github.com/user-attachments/assets/492bcf8b-4c39-4e9c-9cae-cae4107d7cc0" />
<img width="1920" height="834" alt="image" src="https://github.com/user-attachments/assets/e863639e-6390-4166-bc1b-313046710950" />

---

## 🛠️ Technologies Used

- **HTML5**
- **CSS3**
- **JavaScript**
- **Chrome Extensions API**
- **Chrome Storage API**
- **Custom AI Summarizer API** (Hosted at `http://localhost:8081/api/research/process`)
+ **Spring Boot (Java)** – for building the AI summarization backend
+ **Custom REST API** (Hosted at `http://localhost:8081/api/research/process`)

---

## 📦 Folder Structure

```
AI-Research-Assistant/
├── sidepanel.html         # Extension UI
├── sidepanel.js           # Core logic for summarization and notes
├── sidepanel.css          # Styling
├── manifest.json          # Chrome extension config
├── spinner.gif            # Loader image
└── README.md              # Project documentation
```

---

## 🧪 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/tusquake/AI-Research-Assistant.git
cd AI-Research-Assistant
```

### 2. Add Your AI Summarizer API

Ensure your backend is running and accessible at:

```
http://localhost:8081/api/research/process
```

Payload format:

```json
{
  "content": "The text to summarize",
  "operation": "summarize"
}
```

### 3. Load Extension in Chrome

1. Go to `chrome://extensions/`
2. Enable **Developer Mode**
3. Click **Load unpacked**
4. Select the project directory

---

## 🧠 Sample Usage

1. Highlight any text on a webpage.
2. Click the **Summarize** button.
3. View the summary and optionally save your notes.

---

## 📌 TODOs / Future Enhancements

- ✅ Save multiple notes with timestamps
- ✅ Improve summarization with context
- ⬜ Add dark mode toggle
- ⬜ Export notes to `.txt` or `.md`
- ⬜ Deploy backend API to cloud (e.g., Render, Railway, Azure)

---

## 🙌 Credits

Developed by **Tushar Seth** 🚀  
Connect on [LinkedIn](https://linkedin.com/in/your-profile)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
