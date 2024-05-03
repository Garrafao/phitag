package de.garrafao.phitag.computationalannotator.lexsub.data;

import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LexsubPrompt {
    public List<ChatMessage> getChatMessages(final String prompt, final String usage, final String lemma) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage("system", "You are annotation tool");
        messages.add(systemMessage);

        ChatMessage instructionMessage = new ChatMessage("user", prompt);
        messages.add(instructionMessage);

        ChatMessage firstUsageMessage = new ChatMessage("user", "Usage: " + usage);
        messages.add(firstUsageMessage);

        ChatMessage targetWord = new ChatMessage("user", "lemma: " + lemma);
        messages.add(targetWord);
        return messages;
    }
}
