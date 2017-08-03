package com.amazonaws.lambda.runtime.events.samples;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;

/**
 * represents how to handle an ses event in lambda
 */
public class SESHandler implements RequestHandler<SNSEvent, SESEvent> {

    /**
     * hanlde lambda request
     * @param request SNS message
     * @param context Lambda context
     * @return deserialized SESEvent
     */
    public SESEvent handleRequest(SNSEvent request, Context context) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        try {
            SESEvent sesEvent = mapper.readValue(request.getRecords().get(0).getSNS().getMessage(), SESEvent.class);
            System.out.println("Received mail from: " + sesEvent.getMail().getSource());
            return sesEvent;

        } catch (IOException e) {
            System.out.println("Unable to deserialize ses event");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
