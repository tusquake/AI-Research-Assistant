document.addEventListener('DOMContentLoaded', function () {
    // Load notes from storage
    chrome.storage.local.get(['researchNotes'], function (result) {
        if (result.researchNotes) {
            document.getElementById('notes').value = result.researchNotes;
            document.getElementById('notesSection').style.display = 'block';
        }
    });

    document.getElementById('summerizeBtn').addEventListener('click', summerizeText);
    document.getElementById('saveNotesbtn').addEventListener('click', saveNotes);
});

async function summerizeText() {
    try {
        document.getElementById('loading').style.display = 'block';
        document.getElementById('results').innerHTML = '';

        const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
        const [{ result }] = await chrome.scripting.executeScript({
            target: { tabId: tab.id },
            function: () => {
                return window.getSelection().toString();
            }
        });

        if (!result) {
            showResult("Please select some text to summarize.");
            document.getElementById('loading').style.display = 'none';
            return;
        }

        const response = await fetch('http://localhost:8081/api/research/process', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content: result, operation: 'summarize' })  
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const text = await response.text();
        showResult(text.replace(/\n/g, '<br>'));
        console.log("Selected Text:", text);
    } catch (error) {
        showResult(`Error: ${error.message}`);
    } finally {
        document.getElementById('loading').style.display = 'none';
    }
}

async function saveNotes() {
    const notes = document.getElementById('notes').value;

    await new Promise((resolve, reject) => {
        chrome.storage.local.set({ researchNotes: notes }, function () {
            if (chrome.runtime.lastError) {
                reject(chrome.runtime.lastError);
            } else {
                resolve();
            }
        });
    });

    const msg = document.getElementById('notesMessage');
    msg.style.display = 'block';
    msg.textContent = 'Notes saved successfully!';

    setTimeout(() => {
        msg.style.display = 'none';
    }, 3000);

    document.getElementById('notesSection').style.display = 'block';
}


function showResult(content) {
    const resultDiv = document.getElementById('results');
    resultDiv.style.display = 'block'; // Show the results section
    resultDiv.innerHTML = 
    `<div class="result-item">
        <div class="result-content">${content}</div>
    </div>`;
}

