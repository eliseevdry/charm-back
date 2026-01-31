package ru.eliseev.charm.back.controller;

import lombok.Setter;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.dto.ProfileUpdateStatusDto;
import ru.eliseev.charm.back.mapper.RequestToProfileFilterMapper;
import ru.eliseev.charm.back.mapper.RequestToProfileUpdateStatusDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.eliseev.charm.back.utils.UrlUtils.PROFILES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@Setter
public class ProfilesController extends HttpServlet {
    private ProfileService service;
    private RequestToProfileUpdateStatusDtoMapper updateMapper;
    private RequestToProfileFilterMapper filterMapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ProfileFilter filter = filterMapper.map(req);
		req.setAttribute("profiles", service.findAll(filter));
		req.setAttribute("filter", filter);
		req.getRequestDispatcher(getJspPath(PROFILES_URL)).forward(req, res);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<ProfileUpdateStatusDto> dtos = updateMapper.map(req);
		service.updateStatuses(dtos);
		String referer = req.getHeader("referer");
		res.sendRedirect(referer);
	}
}
