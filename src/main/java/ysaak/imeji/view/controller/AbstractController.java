package ysaak.imeji.view.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractController {

    protected String redirect(String url) {
        return "redirect:" + url;
    }

    protected ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
