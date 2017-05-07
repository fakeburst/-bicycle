package blog.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Max on 26-Apr-17.
 */
public class EditPost {
    @Size(min=2, max=30, message = "Title size should be in the range [2...30]")
    private String title;

    @NotNull
    @Size(min=1, max=500)
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
