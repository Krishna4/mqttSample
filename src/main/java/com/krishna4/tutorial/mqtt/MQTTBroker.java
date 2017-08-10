package com.krishna4.tutorial.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTBroker {
    public static void main(String[] args) {
        String topic = "news";
        String content = "Visit www.hascode.com! :D";
        int qos = 2;
        String broker = "tcp://0.0.0.0:1883";
        String clientId = "paho-java-client-broker";

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            int count = 0;
            while (count < 100) {
                content = "Sample Test Message" + ": " + count;
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                if (count % 2 == 0)
                    topic = "even";
                else topic = "odd";
                sampleClient.publish(topic, message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }

            sampleClient.disconnect();
        } catch (MqttException me) {
            me.printStackTrace();
        }

    }
}
