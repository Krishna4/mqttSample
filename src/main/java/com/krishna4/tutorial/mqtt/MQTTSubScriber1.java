package com.krishna4.tutorial.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Timestamp;

public class MQTTSubScriber1 implements MqttCallback {

    public void setup() throws Exception {
        String broker = "tcp://0.0.0.0:1883";
        String clientId = "paho-java-client-first-subscriber";
        MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
        sampleClient.setCallback(new MQTTSubScriber1());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        sampleClient.connect(connOpts);
//Subscribe to topic
        sampleClient.subscribe("even");


    }

    public static void main(String[] args) throws Exception {
        new MQTTSubScriber1().setup();
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.err.println("Connection Lost" + cause.getMessage());
        cause.printStackTrace();
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
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Time:\t" + time +
                "  Topic:\t" + topic +
                "  Message:\t" + new String(message.getPayload()) +
                "  QoS:\t" + message.getQos());
    }
}
