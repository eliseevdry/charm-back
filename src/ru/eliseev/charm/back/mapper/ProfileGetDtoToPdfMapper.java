package ru.eliseev.charm.back.mapper;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.ProfileGetDto;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static ru.eliseev.charm.back.utils.UrlUtils.BASE_CONTENT_PATH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ProfileGetDtoToPdfMapper implements Mapper<ProfileGetDto, Document> {

    private static final ProfileGetDtoToPdfMapper INSTANCE = new ProfileGetDtoToPdfMapper();

    public static ProfileGetDtoToPdfMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Document map(ProfileGetDto dto) {
        return map(dto, new Document());
    }

    @SneakyThrows
    @Override
    public Document map(ProfileGetDto dto, Document pdf) {
        pdf.open();

        PdfPTable table = new PdfPTable(2);

        table.addCell("Email");
        table.addCell(dto.getEmail());

        table.addCell("Name");
        table.addCell(dto.getName());

        table.addCell("Surname");
        table.addCell(dto.getSurname());

        table.addCell("Age");
        if (dto.getAge() != null) {
            table.addCell(dto.getAge().toString());
        } else {
            table.addCell("");
        }

        table.addCell("About");
        table.addCell(dto.getAbout());

        table.addCell("Gender");
        if (dto.getGender() != null) {
            table.addCell(dto.getGender().toString());
        } else {
            table.addCell("");
        }

        table.addCell("Photo");
        if (dto.getPhoto() != null) {
            String decodedPath = URLDecoder.decode(dto.getPhoto(), StandardCharsets.UTF_8);
            Path contentFullPath = Path.of(BASE_CONTENT_PATH, decodedPath);
            Image img = Image.getInstance(contentFullPath.toAbsolutePath().toString());
            img.scalePercent(15f);
            table.addCell(new PdfPCell(img));
        } else {
            table.addCell("");
        }

        pdf.add(table);
        pdf.close();
        return pdf;
    }
}
