package services;
/*
Помогает пользователю вспомнить слово,
можно также реадизовать помощь по слову
 */
public class PromptHelper {
    private int currentCharIndex;
    public String word;

    public PromptHelper(){}
    public PromptHelper(String word){
        this.word = word;
        currentCharIndex = -1;
    }

    public void updateWord(String word){
        this.word = word;
        currentCharIndex = -1;
    }
    public String getNextPrompt(){
        if (currentCharIndex+1 < word.length()) {
            currentCharIndex++;
            return String.valueOf(word.charAt(currentCharIndex));
        }
        else {
            return "";
        }
    }
}
