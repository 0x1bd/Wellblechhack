import { writable } from 'svelte/store';
import type { Category } from '../routes/ClickGui/types';

export class SomeEvent {
    eventType = "SomeEvent" as const;
    constructor(public message: string) {}
}

export class AnotherEvent {
    eventType = "AnotherEvent" as const;
    constructor(public value: number) {}
}

export class ClickGuiInfoEvent {
    eventType = "ClickGuiInfo" as const;
    constructor(public data: Category[]) {}
}

type AppEvent = SomeEvent | AnotherEvent | ClickGuiInfoEvent;

export class WebSocketEventBus {
    private socket: WebSocket | null = null;
    private listeners = new Map<string, ((data: AppEvent) => void)[]>();
    public isConnected = writable(false);

    constructor(private url: string) {
        this.connect();
    }

    private connect() {
        try {
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

            this.socket.onclose = (event) => {
                this.isConnected.set(false);
                console.warn("WebSocket disconnected. Reconnecting in 1 second...", event.reason);
                setTimeout(() => this.connect(), 1000);
            };

            this.socket.onerror = (error) => {
                console.error('WebSocket encountered an error:', error);
                this.isConnected.set(false);
            };
        } catch (error) {
            console.error("Failed to connect WebSocket:", error);
            setTimeout(() => this.connect(), 1000); // Retry on failure
        }
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

    removeListener<T extends AppEvent>(eventType: T['eventType'], handler: (event: T) => void) {
        const handlers = this.listeners.get(eventType);
        if (handlers) {
            this.listeners.set(
                eventType,
                handlers.filter((h) => h !== handler)
            );
        }
    }

    disconnect() {
        if (this.socket) {
            this.socket.close();
            this.isConnected.set(false);
            console.log("WebSocket disconnected");
        }
    }
}

export const bus = new WebSocketEventBus('ws://localhost:8080/ws');
