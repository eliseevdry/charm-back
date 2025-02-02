package ru.eliseev.charm.back.dto;

import static ru.eliseev.charm.back.utils.DateTimeUtils.getPastDate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ru.eliseev.charm.back.model.Status;

public class ProfileSelectQueryBuilder {
	//language=POSTGRES-PSQL
	public static final String SELECT_BASE = """
			SELECT id, email, password, "name", surname, birth_date, about, gender, photo, status, role
			FROM profile
			WHERE '' = ''
			""";

	private final StringBuilder sb;
	private final List<Object> args;

	public ProfileSelectQueryBuilder() {
		this.sb = new StringBuilder(SELECT_BASE);
		this.args = new ArrayList<>();
	}

	public ProfileSelectQueryBuilder addIdFilter(Long id) {
		if (id == null) {
			return this;
		}
		sb.append(" AND id = ?");
		args.add(id);
		return this;
	}

	public ProfileSelectQueryBuilder addEmailFilter(String email) {
		if (email == null) {
			return this;
		}
		sb.append(" AND email = ?");
		args.add(email);
		return this;
	}

	public ProfileSelectQueryBuilder addPasswordFilter(String password) {
		if (password == null) {
			return this;
		}
		sb.append(" AND password = ?");
		args.add(password);
		return this;
	}

	public ProfileSelectQueryBuilder addEmailStartWithFilter(String emailStartWith) {
		if (emailStartWith == null) {
			return this;
		}
		sb.append(" AND email like ?");
		args.add(emailStartWith + "%");
		return this;
	}

	public ProfileSelectQueryBuilder addNameStartWith(String nameStartWith) {
		if (nameStartWith == null) {
			return this;
		}
		sb.append(" AND name like ?");
		args.add(nameStartWith + "%");
		return this;
	}

	public ProfileSelectQueryBuilder addSurnameStartWith(String surnameStartWith) {
		if (surnameStartWith == null) {
			return this;
		}
		sb.append(" AND surname like ?");
		args.add(surnameStartWith + "%");
		return this;
	}

	public ProfileSelectQueryBuilder addLTAge(Integer ltAge) {
		if (ltAge == null) {
			return this;
		}
		Date gtDate = getPastDate(ltAge);
		sb.append(" AND birth_date > ?");
		args.add(gtDate);
		return this;
	}

	public ProfileSelectQueryBuilder addGTEAge(Integer gteAge) {
		if (gteAge == null) {
			return this;
		}
		Date lteDate = getPastDate(gteAge);
		sb.append(" AND birth_date <= ?");
		args.add(lteDate);
		return this;
	}

	public ProfileSelectQueryBuilder addStatus(Status status) {
		if (status == null) {
			return this;
		}
		sb.append(" AND status = ?");
		args.add(status.toString());
		return this;
	}

	public ProfileSelectQueryBuilder addSortedColumn(String column) {
		if (column == null) {
			column = "id";
		}
		sb.append(" ORDER BY ").append(column);
		return this;
	}

	public Query build() {
		return new Query(sb.toString(), args);
	}
}
