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
    name: string;
    description: string;
    enabled: boolean;
    settings: Setting[];
}

export interface Category {
    name: string;
    modules: Module[];
}

export interface CustomEvent<T> extends Event {
    detail: T;
}