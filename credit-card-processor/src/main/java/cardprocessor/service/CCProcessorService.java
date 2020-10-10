package cardprocessor.service;

import cardprocessor.model.CCGenericResponse;
import cardprocessor.model.CCTransactionRequestModel;
import cardprocessor.model.CreditCardData;
import cardprocessor.model.CreditCardListResponse;

/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */

public interface CCProcessorService {

    public CCGenericResponse addCard(CreditCardData creditCardData);

    public String removeCard(CreditCardData creditCardData);

    public CreditCardListResponse getAllCards();

    public CCGenericResponse transfer(CCTransactionRequestModel CCTransactionRequestModel);

}
