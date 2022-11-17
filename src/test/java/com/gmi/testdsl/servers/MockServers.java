package com.gmi.testdsl.servers;

import org.mockserver.integration.ClientAndServer;

import static com.gmi.testdsl.fakeapplication.clients.RetrofitConfiguration.ENTITY_SERVER_PORT;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class MockServers {

    private static ClientAndServer entityServer;

    public static ClientAndServer getEntityServer() {
        if (entityServer == null) {
            entityServer = startClientAndServer(ENTITY_SERVER_PORT);
        }

        return entityServer;
    }

 }
