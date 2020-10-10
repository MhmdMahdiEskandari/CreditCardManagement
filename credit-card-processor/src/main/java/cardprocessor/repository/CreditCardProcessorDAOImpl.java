/**
 *
 */
package cardprocessor.repository;

import cardprocessor.model.CreditCardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */

@Repository
public class CreditCardProcessorDAOImpl implements CreditCardProcessorDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Map<String, Object>> getAllCards() throws DataAccessException {
        return jdbcTemplate.queryForList("SELECT * FROM creditcardinfo");
    }

    public int addCard(CreditCardData creditCardData) throws DataAccessException {
        return jdbcTemplate.update("insert into creditcardinfo (ccnumber,pin,cvv2,exp_date,fname,lname,credit_amount) values (?,?,?,?,?,?,?)", new Object[]{
                creditCardData.getCardNumber(), creditCardData.getPin(), creditCardData.getCvv2(), creditCardData.getExpDate(),
                creditCardData.getFirstName(), creditCardData.getLastName(), creditCardData.getTotalCredit()
        });
    }

    @Override
    public int removeCard(CreditCardData creditCardData) throws DataAccessException {
        return jdbcTemplate.update("DELETE FROM creditcardinfo WHERE ccnumber=? "
                , creditCardData.getCardNumber());
    }

    public List<Map<String, Object>> getCardByNumber(String cartNumber) throws DataAccessException {
        return jdbcTemplate.queryForList("SELECT * FROM creditcardinfo WHERE ccnumber = ?", cartNumber);
    }

    public int updateCard(CreditCardData creditCardData) throws DataAccessException {
        return jdbcTemplate.update("UPDATE creditcardinfo SET credit_amount=? WHERE ccnumber=? "
                , creditCardData.getTotalCredit(), creditCardData.getCardNumber());
    }
}
