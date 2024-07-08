package de.garrafao.phitag.computationalannotator.sentiment.data;

import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SentimentPrompt {
    /**
     * Retrieves a list of chat messages including system, instructions, and user prompts.
     * @param prompt The main prompt to be displayed to the user.
     * @param usage The first sentence for the user to evaluate.
     * @param lemma The target word for evaluation.
     * @return A list of ChatMessage objects containing system and user messages.
     */
    public List<ChatMessage> getChatMessages(final String prompt, final String system, final String finalMessage,  final String usage, final String lemma) {
        List<ChatMessage> messages = new ArrayList<>();

        // System message
        ChatMessage systemMessage = new ChatMessage("system", system);

        messages.add(systemMessage);

        // Instruction message
        ChatMessage instructionMessage = new ChatMessage("user", prompt);
        messages.add(instructionMessage);

        // First usage message
        ChatMessage firstUsageMessage = new ChatMessage("user", "Sentence:"+ usage );
        messages.add(firstUsageMessage);

        // Second usage message

        // Target word message


        // Return type instruction message
        ChatMessage returnType = new ChatMessage("user", finalMessage);
           messages.add(returnType);

        return messages;
    }

}
