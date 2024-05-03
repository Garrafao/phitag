package de.garrafao.phitag.computationalannotator.common.function;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommonFunction {

    public  int extractInteger(String input) {
        // Define a pattern to match integers in the string
        Pattern pattern = Pattern.compile("\\b\\d+\\b");

        // Create a matcher
        Matcher matcher = pattern.matcher(input);

        // Find the first match
        if (matcher.find()) {
            // Parse and return the matched integer
            return Integer.parseInt(matcher.group());
        } else {
            // Return a default value or handle the case where no integer is found
            return 0;
        }
    }

}
