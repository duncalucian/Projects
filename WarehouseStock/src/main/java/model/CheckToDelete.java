package model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used for the fields we need to use to check the deletion condition
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToDelete{

}