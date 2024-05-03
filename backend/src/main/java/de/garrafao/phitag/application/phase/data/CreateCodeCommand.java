package de.garrafao.phitag.application.phase.data;

import lombok.Getter;
import org.apache.commons.lang3.Validate;
@Getter
public class CreateCodeCommand {
   private final String code;



    public CreateCodeCommand(String code) {
        Validate.notBlank(code, "code cannot be blank");
        this.code = code;
    }
    @Override
    public String toString() {
        return "CreateCodeCommand{" +
                "code='" + code + '\'' +
                '}';
    }
}
