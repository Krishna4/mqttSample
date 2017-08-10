package com.krishna4.tutorial.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Timestamp;

public class MQTTSubScriber2 implements MqttCallback {

    public void setup() throws Exception {
        String broker = "tcp://0.0.0.0:1883";
        String clientId = "paho-java-client-second-subscriber";
        MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
        sampleClient.setCallback(new MQTTSubScriber2());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        sampleClient.connect(connOpts);

        sampleClient.subscribe("odd");


    }

    public static void main(String[] args) throws Exception {
        new MQTTSubScriber2().setup();
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.err.println("Connection Lost" + cause.getMessage());
        cause.printStackTrace();
        //System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {

        System.out.println("Delivery Completed" + token);
    }

    /****************************************************************/
  /* End of MqttCallback methods                                  */

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        // Called when a message arrives from the server that matches any
        // subscription made by the client
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Time:\t" + time +
                "  Topic:\t" + topic +
                "  Message:\t" + new String(message.getPayload()) +
                "  QoS:\t" + message.getQos());
    }
}
