package com.yaremax.busticketbooth_tech.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public String handleDuplicateResourceException(DuplicateResourceException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:" + request.getHeader("Referer");
    }
}
