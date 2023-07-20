package org.verify.core.usecase;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyCommentUseCase extends UseCase<VerifyCommentUseCase.Input, VerifyCommentUseCase.Output> {
    @Override
    public Output execute(Input input) {
        String invalidWord = "Kurka";
        boolean isValid = !containsWord(input.content, invalidWord);
        return new Output(isValid);
    }

    private boolean containsWord(String inputString, String word){
        String patternString = "\\b" + word + "\\b";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);

        return matcher.find();
    }

    @Value
    public static class Input implements UseCase.Input{
        String content;
    }

    @Value
    public static class Output implements UseCase.Output{
        boolean isValid;
    }
}
