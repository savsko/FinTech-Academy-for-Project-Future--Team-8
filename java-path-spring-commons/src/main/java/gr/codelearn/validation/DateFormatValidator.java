package gr.codelearn.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateFormatValidator implements ConstraintValidator<DateFormatted, String> {

    private String pattern;

    @Override
    public void initialize(DateFormatted constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(object);
            return true;
        } catch (Exception e) {
            log.info("Error occured during date validation: {}.", e.getMessage());
            return false;
        }
    }
}