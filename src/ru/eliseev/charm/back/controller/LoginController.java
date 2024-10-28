package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.LoginDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.RequestToLoginDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.LoginValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import java.io.IOException;
import java.util.Optional;

import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@WebServlet(LOGIN_URL)
@Slf4j
public class LoginController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final RequestToLoginDtoMapper requestToLoginDtoMapper = RequestToLoginDtoMapper.getInstance();

    private final LoginValidator loginValidator = LoginValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPath(LOGIN_URL)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LoginDto dto = requestToLoginDtoMapper.map(req);
        ValidationResult validationResult = loginValidator.validate(dto);
        if (validationResult.isValid()) {
            Optional<UserDetails> userDetailsOpt = service.getUserDetails(dto.getEmail());
            if (userDetailsOpt.isPresent()) {
                UserDetails userDetails = userDetailsOpt.get();
                req.getSession().setAttribute("userDetails", userDetails);
                resp.sendRedirect(String.format(PROFILE_URL + "?id=%s", userDetails.getId()));
            } else {
                resp.sendRedirect(LOGIN_URL);
            }
        } else {
            req.setAttribute("errors", validationResult.getErrors());
            doGet(req, resp);
        }
    }
}
