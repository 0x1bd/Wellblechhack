// src/types.ts
export interface Setting {
    type: 'slider' | 'checkbox';
    label: string;
    key: string;
    value: number | boolean;
    min?: number;
    max?: number;
    step?: number;
}

export interface Module {
    id: number;
    name: string;
    description: string;
    enabled: boolean;
    settings: Setting[];
}

export interface Category {
    id: number;
    name: string;
    modules: Module[];
}

export interface CustomEvent<T> extends Event {
    detail: T;
}