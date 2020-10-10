package cardprocessor.service;

import cardprocessor.constants.CreditCardDataConstants;
import cardprocessor.model.*;
import cardprocessor.repository.CreditCardProcessorDAO;
import cardprocessor.model.*;
import cardprocessor.utils.CCProcessorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */

@Service
public class CCProcessorServiceImpl implements CCProcessorService {
    private static final Logger LOG = LoggerFactory.getLogger(CCProcessorServiceImpl.class);


    @Autowired
    private CreditCardProcessorDAO creditCardProcessorDAO;

    @Override
    public CCGenericResponse addCard(CreditCardData creditCardData) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        ccGenericResponse.setCardNumber(creditCardData.getCardNumber());
        ccGenericResponse.setBalanceRemaining("0");

        try {
            int response = creditCardProcessorDAO.addCard(creditCardData);
        } catch (DuplicateKeyException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.CARD_ALREADY_EXIST);
            ccGenericResponse.setErrors(errors);
        } catch (DataAccessException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;

    }

    @Override
    public String removeCard(CreditCardData creditCardData) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        ccGenericResponse.setCardNumber(creditCardData.getCardNumber());

        try {
            if (null == creditCardData.getCardNumber() || creditCardData.getCardNumber().isEmpty()) {
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_NOT_FOUND);
                ccGenericResponse.setErrors(errors);
            } else {
                int response = creditCardProcessorDAO.removeCard(creditCardData);
            }
        } catch (DataAccessException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            ccGenericResponse.setErrors(errors);
        }

        return "Cart removed successfully.";
    }

    public CreditCardListResponse getAllCards() {
        CreditCardListResponse cardList = new CreditCardListResponse();
        List<CreditCardData> creditCards = new ArrayList<CreditCardData>();
        try {
            List<Map<String, Object>> cards = creditCardProcessorDAO.getAllCards();
            for (Map<String, Object> row : cards) {
                CreditCardData cardData = new CreditCardData();
                cardData.setCardNumber((String) row.get("ccnumber"));
                cardData.setFirstName((String) row.get("fname"));
                cardData.setLastName((String) row.get("lname"));
                cardData.setPin((String) row.get("pin"));
                cardData.setCvv2((String) row.get("cvv2"));
                cardData.setExpDate((String) row.get("exp_date"));
                cardData.setTotalCredit(CCProcessorUtils.formatAmount((String) row.get("credit_amount")));
                creditCards.add(cardData);
            }
        } catch (DataAccessException | NumberFormatException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            cardList.setErrors(errors);
        }

        if (!creditCards.isEmpty()) {
            cardList.setCreditCards(creditCards);
        }
        return cardList;
    }

    @Override
    public CCGenericResponse transfer(CCTransactionRequestModel CCTransactionRequestModel) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        try {
            CreditCardData sourceCardInfo = getExistingCardDetails(CCTransactionRequestModel.getSource());

            if ((null == sourceCardInfo.getCardNumber() || sourceCardInfo.getCardNumber().isEmpty()) ||
                    (null == CCTransactionRequestModel.getDest() || CCTransactionRequestModel.getDest().isEmpty()) ) {
                ccGenericResponse.setCardNumber(CCTransactionRequestModel.getSource());
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_NOT_FOUND);
                ccGenericResponse.setErrors(errors);
            } else if(!CCTransactionRequestModel.getCvv2().equals(sourceCardInfo.getCvv2())) {
                ccGenericResponse.setCardNumber(CCTransactionRequestModel.getSource());
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_CVV2_ERROR);
                ccGenericResponse.setErrors(errors);
            } else if(!CCTransactionRequestModel.getPin().equals(sourceCardInfo.getPin())) {
                ccGenericResponse.setCardNumber(CCTransactionRequestModel.getSource());
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_PIN_ERROR);
                ccGenericResponse.setErrors(errors);
            } else if(!CCTransactionRequestModel.getExpDate().equals(sourceCardInfo.getExpDate())) {
                ccGenericResponse.setCardNumber(CCTransactionRequestModel.getSource());
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_EXPDate_ERROR);
                ccGenericResponse.setErrors(errors);
            } else {
                double transactionAmount = Double.parseDouble(CCTransactionRequestModel.getTransactionAmount());
                double sourceCurrentCredit = Double.parseDouble(sourceCardInfo.getTotalCredit());

                double balance = (sourceCurrentCredit + transactionAmount);

                CreditCardData destCardInfo = getExistingCardDetails(CCTransactionRequestModel.getDest());
                double destCurrentCredit = Double.parseDouble(destCardInfo.getTotalCredit());

                if(sourceCardInfo.getCardNumber().substring(0, 4).equals("6037")) {
                    LOG.info("Payment provider 1 is called.");
                } else {
                    LOG.info("Payment provider 2 is called.");
                }

                sourceCardInfo.setTotalCredit(String.valueOf(sourceCurrentCredit - transactionAmount));
                creditCardProcessorDAO.updateCard(sourceCardInfo);


                destCardInfo.setTotalCredit(String.valueOf(destCurrentCredit + transactionAmount));
                creditCardProcessorDAO.updateCard(destCardInfo);

                ccGenericResponse.setCardNumber(destCardInfo.getCardNumber());
                ccGenericResponse.setCardNumber(destCardInfo.getCardNumber());
                ccGenericResponse.setTotalCredit(destCardInfo.getTotalCredit());
                ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(String.valueOf(balance)));

                LOG.info("Message sent to notification server.");

            }
        } catch (DataAccessException | NumberFormatException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    public CreditCardData getExistingCardDetails(String cartNumber) throws DataAccessException, NumberFormatException {

        List<Map<String, Object>> cards = creditCardProcessorDAO.getCardByNumber(cartNumber);

        if (cards.isEmpty()) {
            return new CreditCardData();
        }
        CreditCardData cardInfo = new CreditCardData();
        for (Map<String, Object> row : cards) {
            cardInfo.setCardNumber((String) row.get("ccnumber"));
            cardInfo.setPin((String) row.get("pin"));
            cardInfo.setCvv2((String) row.get("cvv2"));
            cardInfo.setExpDate((String) row.get("exp_date"));
            cardInfo.setFirstName((String) row.get("fname"));
            cardInfo.setLastName((String) row.get("lname"));
            cardInfo.setTotalCredit((String) row.get("credit_amount"));
        }

        return cardInfo;
    }
}
