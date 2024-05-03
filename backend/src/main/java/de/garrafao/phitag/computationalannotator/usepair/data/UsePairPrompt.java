package de.garrafao.phitag.computationalannotator.usepair.data;

import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsePairPrompt {
    /**
     * Retrieves a list of chat messages including system, instructions, and user prompts.
     * @param prompt The main prompt to be displayed to the user.
     * @param firstUsage The first sentence for the user to evaluate.
     * @param secondUsage The second sentence for the user to evaluate.
     * @param lemma The target word for evaluation.
     * @return A list of ChatMessage objects containing system and user messages.
     */
    public List<ChatMessage> getChatMessages(final String prompt, final String firstUsage,
                                             final String secondUsage, final String lemma) {
        List<ChatMessage> messages = new ArrayList<>();

        // System message
        ChatMessage systemMessage = new ChatMessage("system", "You are a highly trained text data annotation tool capable of providing judgments based on contexts provided to you.");
        //ChatMessage systemMessage = new ChatMessage("system", "READ ThIS GUIDELINE AND ANNOATATE THIS");

        messages.add(systemMessage);

        // Instruction message
        ChatMessage instructionMessage = new ChatMessage("user", prompt);
        messages.add(instructionMessage);

        // First usage message
        ChatMessage firstUsageMessage = new ChatMessage("user", "Sentence 1: "+ firstUsage );
        messages.add(firstUsageMessage);

        // Second usage message
        ChatMessage secondUsageMessage = new ChatMessage("user", "Sentence 2: " + secondUsage);
        messages.add(secondUsageMessage);

        // Target word message
        ChatMessage targetWord = new ChatMessage("user", "Target word: " + lemma);
        messages.add(targetWord);

        // Return type instruction message
        ChatMessage returnType = new ChatMessage("user", "Please provide a judgment as a single integer for Sentence 1 and Sentence 2 above." +
         "For example, if your judgment is Identical, then provide 4. If your judgment is Unrelated, provide 1.");
           messages.add(returnType);

        return messages;
    }

}
