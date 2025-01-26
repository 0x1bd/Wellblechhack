import { writable } from 'svelte/store';

export class SomeEvent {
    eventType = "SomeEvent" as const;
    constructor(public message: string) {}
}

export class AnotherEvent {
    eventType = "AnotherEvent" as const;
    constructor(public value: number) {}
}

type AppEvent = SomeEvent | AnotherEvent;

export class WebSocketEventBus {
    private socket: WebSocket | null = null;
    private listeners = new Map<string, ((data: AppEvent) => void)[]>();
    public isConnected = writable(false);

    constructor(private url: string) {
        this.connect();
    }

    private connect() {
        this.socket = new WebSocket(this.url);

        this.socket.onopen = () => {
            this.isConnected.set(true);
            console.log("WebSocket connected");
        };

        this.socket.onmessage = (event) => {
            try {
                const message: AppEvent = JSON.parse(event.data);
                const handlers = this.listeners.get(message.eventType) || [];
                handlers.forEach((handler) => handler(message));
            } catch (error) {
                console.error('Error parsing message:', error);
            }
        };

        this.socket.onclose = () => {
            this.isConnected.set(false);
            console.log("WebSocket disconnected. Reconnecting...");
            setTimeout(() => this.connect(), 1000); // Reconnect after 1 second
        };

        this.socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };
    }

    sendEvent<T extends AppEvent>(event: T) {
        if (this.socket?.readyState === WebSocket.OPEN) {
            this.socket.send(JSON.stringify(event));
        } else {
            console.error("Cannot send event: WebSocket is not open");
        }
    }

    addListener<T extends AppEvent>(eventType: T['eventType'], handler: (event: T) => void) {
        const handlers = this.listeners.get(eventType) || [];
        handlers.push(handler as (event: AppEvent) => void);
        this.listeners.set(eventType, handlers);
    }

    disconnect() {
        if (this.socket) {
            this.socket.close();
            console.log("WebSocket disconnected");
        }
    }
}