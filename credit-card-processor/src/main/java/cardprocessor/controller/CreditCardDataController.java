/**
 *
 */
package cardprocessor.controller;

import cardprocessor.model.*;
import cardprocessor.constants.CreditCardDataConstants;
import cardprocessor.model.*;
import cardprocessor.service.CCProcessorService;
import cardprocessor.utils.CCProcessorUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */
@RestController
public class CreditCardDataController {

    @Autowired
    private CCProcessorService processorService;

    @RequestMapping(path = "/add-card", method = RequestMethod.POST)
    @ApiOperation("Add a new transfer card")
    public CCGenericResponse addCard(@RequestBody CreditCardData creditCardData) {

        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        if (CCProcessorUtils.validateCardDetails(creditCardData)) {
            ccGenericResponse = processorService.addCard(creditCardData);
        } else {
            ccGenericResponse.setCardNumber(creditCardData.getCardNumber());
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.CARD_NUMBER_ERROR);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    @ApiOperation("Transfer money from source cart to destination cart")
    public CCGenericResponse transfer(@RequestBody CCTransactionRequestModel request) {
        CreditCardData creditCardData = new CreditCardData(request);
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        if (CCProcessorUtils.validateCardDetails(creditCardData)) {
            ccGenericResponse = processorService.transfer(request);
        } else {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.CARD_NUMBER_ERROR);
            ccGenericResponse.setErrors(errors);
        }
        return ccGenericResponse;
    }

    @RequestMapping(path = "/list-cards", method = RequestMethod.GET , produces = "application/json")
    @ApiOperation("List all cards from the system")
    public CreditCardListResponse getCards() {
        return processorService.getAllCards();

    }

}
