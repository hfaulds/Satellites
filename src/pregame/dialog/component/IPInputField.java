package pregame.dialog.component;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;

@SuppressWarnings("serial")
public class IPInputField extends JFormattedTextField {
  
  private static IPFormatter ipFormatter = new IPFormatter();

  public IPInputField() {
    super(ipFormatter);
  }

  public boolean validText(String text) {
    return ipFormatter.validText(text);
  }

  public void addDocumentListener(SelectServerListener listener) {
    getDocument().addDocumentListener(listener);
  }
}

@SuppressWarnings("serial") 
class IPFormatter extends DefaultFormatter {
  
    private static final String REGEX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    
    private Pattern pattern;
    private Matcher matcher;

    public IPFormatter() throws PatternSyntaxException {
        super();
        setPattern(Pattern.compile(REGEX));
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    protected void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    protected Matcher getMatcher() {
        return matcher;
    }

    public Object stringToValue(String text) throws ParseException {
        Pattern pattern = getPattern();

        if (pattern != null) {
            Matcher matcher = pattern.matcher(text);

            if (matcher.matches()) {
                setMatcher(matcher);
                return super.stringToValue(text);
            }
            throw new ParseException("Pattern did not match", 0);
        }
        return text;
    }
    
    public boolean validText(String text) {
      Matcher matcher = pattern.matcher(text);
      return matcher.matches();
    }
}