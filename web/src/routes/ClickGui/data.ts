// src/data.ts
import type { Category } from './types';

export const categories: Category[] = [
    {
        name: "Combat",
        modules: [
            {
                name: "Damage Modifier",
                description: "Adjust damage multipliers",
                enabled: false,
                settings: [
                    { type: "slider", label: "Player Damage", key: "dmgMult", value: 1.0, min: 0.5, max: 3.0, step: 0.1 },
                    { type: "checkbox", label: "Friendly Fire", key: "friendlyFire", value: false }
                ]
            },
            {
                name: "God Mode",
                description: "Become invincible",
                enabled: false,
                settings: [
                    { type: "checkbox", label: "Enable Invincibility", key: "invincible", value: false }
                ]
            }
        ]
    },
    {
        name: "Movement",
        modules: [
            {
                name: "Speed Hack",
                description: "Modify movement speed",
                enabled: false,
                settings: [
                    { type: "slider", label: "Speed Multiplier", key: "speedMult", value: 1.0, min: 0.5, max: 5.0, step: 0.1 }
                ]
            }
        ]
    },
    {
        name: "Bobbers",
        modules: [
            {
                name: "A",
                description: "A",
                enabled: true,
                settings: []
            },
            {
                name: "B",
                description: "B",
                enabled: true,
                settings: []
            },
            {
                name: "AB",
                description: "AB",
                enabled: true,
                settings: []
            },
        ]
    }
];