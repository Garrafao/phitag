package de.garrafao.phitag.computationalannotator.usepair.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import de.garrafao.phitag.computationalannotator.common.error.WrongApiKeyException;
import de.garrafao.phitag.computationalannotator.common.error.WrongModelException;
import de.garrafao.phitag.computationalannotator.common.function.CommonFunction;
import de.garrafao.phitag.computationalannotator.usepair.data.UsePairPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsePairOpenAIService {


    private final UsePairPrompt usePairPrompt;

    private final CommonFunction commonFunction;

    @Autowired
    public UsePairOpenAIService(UsePairPrompt usePairPrompt, CommonFunction commonFunction) {
        this.usePairPrompt = usePairPrompt;
        this.commonFunction = commonFunction;
    }

    public String chat(final String apiKey, final String model, final String prompt, final String firstUsage,
                    final String secondUsage, final String lemma) {
        try {
            List<ChatMessage> messages = this.usePairPrompt.getChatMessages(prompt, firstUsage, secondUsage, lemma);

            OpenAiService service = new OpenAiService(apiKey);
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(messages)
                    .model(model)
                    .temperature(0.9)
                    .topP(0.9)
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
