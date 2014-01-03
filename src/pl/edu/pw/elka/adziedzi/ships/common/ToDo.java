/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.common;

import java.lang.annotation.*;
/**
 * Annotation to describe sth to do.
 * 
 * @author Adam Dziedzic
 */
@Target({ElementType.FIELD,
    ElementType.METHOD,
    ElementType.CONSTRUCTOR,
    ElementType.ANNOTATION_TYPE})
public @interface ToDo
{
    String value();
}
