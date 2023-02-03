package examplemod.client.ultralight.opengl.js;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates method is used by JavaScript
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.METHOD,})
public @interface JavaScriptUse {
}

