package ru.eliseev.charm.back.controller;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;
import static ru.eliseev.charm.back.utils.UrlUtils.PDF_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REGISTRATION_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.ProfileGetDtoToPdfMapper;
import ru.eliseev.charm.back.mapper.RequestToProfileUpdateDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.ProfileUpdateValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

@WebServlet(PROFILE_URL + "/*")
@MultipartConfig
@Slf4j
public class ProfileController extends HttpServlet {
	private final ProfileService service = ProfileService.getInstance();

	private final RequestToProfileUpdateDtoMapper requestToProfileUpdateDtoMapper = RequestToProfileUpdateDtoMapper.getInstance();

	private final ProfileGetDtoToPdfMapper profileGetDtoToPdfMapper = ProfileGetDtoToPdfMapper.getInstance();

	private final ProfileUpdateValidator profileUpdateValidator = ProfileUpdateValidator.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String sId = req.getParameter("id");
		Long id = sId == null ? null : Long.parseLong(sId);
		Optional<ProfileGetDto> optProfileGetDto = service.findById(id);
		if (optProfileGetDto.isPresent()) {
			ProfileGetDto profileGetDto = optProfileGetDto.get();
			if (req.getRequestURI().equals(PROFILE_URL + PDF_URL)) {
				res.setHeader("Content-Disposition", "attachment; filename=\"resume.pdf\"");
				res.setContentType("application/pdf");
				try (OutputStream outputStream = res.getOutputStream()) {
					Document pdf = new Document();
					PdfWriter.getInstance(pdf, outputStream);
					profileGetDtoToPdfMapper.map(profileGetDto, pdf);
				} catch (DocumentException e) {
					throw new IOException(e);
				}
			} else {
				req.setAttribute("profile", profileGetDto);
				req.getRequestDispatcher(getJspPath(PROFILE_URL)).forward(req, res);
			}
		} else {
			res.sendError(SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		ProfileUpdateDto dto = requestToProfileUpdateDtoMapper.map(req);
		ValidationResult validationResult = profileUpdateValidator.validate(dto);
		if (validationResult.isValid()) {
			service.update(dto);
			String referer = req.getHeader("referer");
			res.sendRedirect(referer);
		} else {
			req.setAttribute("errors", validationResult.getErrors());
			doGet(req, res);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String sId = req.getParameter("id");
		if (!isBlank(sId) && service.delete(Long.parseLong(sId))) {
			log.info("Profile with id {} has been deleted", sId);
			UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
			if (sId.equals(userDetails.getId().toString())) {
				req.getSession().invalidate();
			}
			res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			res.sendRedirect(REGISTRATION_URL);
		} else {
			res.sendError(SC_NOT_FOUND);
		}
	}
}
