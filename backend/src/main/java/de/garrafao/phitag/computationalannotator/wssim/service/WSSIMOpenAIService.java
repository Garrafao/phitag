package de.garrafao.phitag.computationalannotator.wssim.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import de.garrafao.phitag.computationalannotator.common.error.WrongApiKeyException;
import de.garrafao.phitag.computationalannotator.common.error.WrongModelException;
import de.garrafao.phitag.computationalannotator.common.function.CommonFunction;
import de.garrafao.phitag.computationalannotator.common.model.application.data.OpenAPIResponseDto;
import de.garrafao.phitag.computationalannotator.wssim.data.WSSIMPrompt;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WSSIMOpenAIService {
    private final WSSIMPrompt wssimPrompt;

    private final CommonFunction commonFunction;

    public WSSIMOpenAIService(WSSIMPrompt wssimPrompt, CommonFunction commonFunction) {
        this.wssimPrompt = wssimPrompt;
        this.commonFunction = commonFunction;
    }

    public OpenAPIResponseDto chat(final String apiKey, final String model, final String prompt, final String usage, final String lemma) {
        try {
            OpenAiService service = new OpenAiService(apiKey);
            List<ChatMessage> messages = this.wssimPrompt.getChatMessages(prompt, usage, lemma);

            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(messages)
                    .model(model)
                    .temperature(0.0)
                    .n(1)
                    .build();
            List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();
            StringBuilder returnString = new StringBuilder();
            for (ChatCompletionChoice choice : choices) {
                returnString.append("response: ").append(choice.getMessage().getContent()).append(System.lineSeparator());
            }
            int result = this.commonFunction.extractInteger(returnString.toString());

            return new OpenAPIResponseDto(String.valueOf(result));


        } catch (OpenAiHttpException e) {
            if (e.getMessage().contains("The model")) {
                throw new WrongModelException(model);
            }
            if (e.getMessage().contains("Incorrect API key provided")) {
                throw new WrongApiKeyException();
            }
        }

        return null;
    }

}
