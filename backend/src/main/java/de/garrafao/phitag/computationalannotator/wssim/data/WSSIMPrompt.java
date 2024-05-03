package de.garrafao.phitag.computationalannotator.wssim.data;

import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WSSIMPrompt {
    public List<ChatMessage> getChatMessages(final String prompt, final String usage, final String lemma) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage("system", "You are annotation tool");
        messages.add(systemMessage);

        ChatMessage instructionMessage = new ChatMessage("user", prompt);
        messages.add(instructionMessage);

        ChatMessage usageMessage = new ChatMessage("user", "Usage: " + usage);
        messages.add(usageMessage);


        ChatMessage targetWord = new ChatMessage("user", "lemma: " + lemma);
        messages.add(targetWord);
        ChatMessage returnType = new ChatMessage("user", "give me ´´´Judgement´´ in ´´´single integer´´ " +
                "for example:if your Judgement is Identical, then give me 4");
        messages.add(returnType);

        return messages;
    }
}
