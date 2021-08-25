package uz.tune.tenge.wrongeventpublisher.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractLockRabbitMQPublisher {

    public static Logger logger = LogManager.getLogger();

    protected SERVICE_NAME service;

    public AbstractLockRabbitMQPublisher() {
    }

    public abstract void publish(
            String userId,
            String deviceId,
            String ip,
            WRONG_EVENT wrongEvent
    );

    protected SERVICE_NAME getService() {
        return service;
    }

    protected void setServiceOrUnknown(String service){
        try{
            this.service = SERVICE_NAME.valueOf(service);
        }catch (Throwable e){
            logger.error(e);
            this.service = SERVICE_NAME.UNKNOWN;
        }
    }

    protected abstract void setService(String service);

}
