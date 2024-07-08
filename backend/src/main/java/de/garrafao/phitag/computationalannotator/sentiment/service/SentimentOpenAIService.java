package de.garrafao.phitag.computationalannotator.sentiment.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import de.garrafao.phitag.computationalannotator.common.error.WrongApiKeyException;
import de.garrafao.phitag.computationalannotator.common.error.WrongModelException;
import de.garrafao.phitag.computationalannotator.common.function.CommonFunction;
import de.garrafao.phitag.computationalannotator.sentiment.data.SentimentPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentOpenAIService {


    private final SentimentPrompt sentimentPrompt;

    private final CommonFunction commonFunction;

    @Autowired
    public SentimentOpenAIService(SentimentPrompt sentimentPrompt, CommonFunction commonFunction) {
        this.sentimentPrompt = sentimentPrompt;
        this.commonFunction = commonFunction;
    }

    public String chat(final String apiKey, final String model, final double temperature, final double topP, final String prompt, final String system, final String finalMessage, final String usage, final String lemma) {
        try {
            List<ChatMessage> messages = this.sentimentPrompt.getChatMessages(prompt,system, finalMessage, usage, lemma);

            OpenAiService service = new OpenAiService(apiKey);
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(messages)
                    .model(model)
                    .temperature(temperature)
                    .topP(topP)
                    .n(1)
                    .build();
            List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();
            StringBuilder returnString = new StringBuilder();

                for (ChatCompletionChoice choice : choices) {
                    ChatMessage message = choice.getMessage();
                    if (message != null) {
                        returnString.append(message.getContent()).append(System.lineSeparator());
                    }
                }

            int result = this.commonFunction.extractInteger(returnString.toString());

            return String.valueOf(result);


        }catch (OpenAiHttpException e) {
            if (e.getMessage().contains("The model")) {
                throw new WrongModelException(model);
            }
            if (e.getMessage().contains("Incorrect API key provided")) {
                throw new WrongApiKeyException();
            }
            throw e;
        }
    }
}
