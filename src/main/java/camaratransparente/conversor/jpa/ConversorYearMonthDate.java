package camaratransparente.conversor.jpa;

import java.sql.Date;
import java.time.YearMonth;
import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorYearMonthDate implements AttributeConverter<YearMonth, Date> {

	@Override
	public Date convertToDatabaseColumn(YearMonth atributoDaEntidade) {
		return Optional.ofNullable(atributoDaEntidade)
				.map(yearMonth -> yearMonth.atDay(1))
				.map(Date::valueOf)
				.orElse(null);
	}

	@Override
	public YearMonth convertToEntityAttribute(Date dataNoBancoDeDados) {
		return Optional.ofNullable(dataNoBancoDeDados)
				.map(Date::toLocalDate)
				.map(YearMonth::from)
				.orElse(null);
	}

}
