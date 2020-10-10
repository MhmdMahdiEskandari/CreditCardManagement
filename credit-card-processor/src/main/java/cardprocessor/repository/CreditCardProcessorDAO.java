/**
 *
 */
package cardprocessor.repository;

import cardprocessor.model.CreditCardData;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */


public interface CreditCardProcessorDAO {

    public List<Map<String, Object>> getAllCards() throws DataAccessException;

    public List<Map<String, Object>> getCardByNumber(String cartNumber) throws DataAccessException;

    public int addCard(CreditCardData creditCardData) throws DataAccessException;

    public int removeCard(CreditCardData creditCardData) throws DataAccessException;

    public int updateCard(CreditCardData creditCardData) throws DataAccessException;

}
