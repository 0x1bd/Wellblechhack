<script lang="ts">
    import type { Module } from '../types';
    import SliderInput from './SliderInput.svelte';
    import CheckboxInput from './CheckboxInput.svelte';

    export let selectedModule: Module | null;
</script>

<style>
    .settings {
        width: 320px;
        padding: 2rem;
        background: linear-gradient(160deg, rgba(25, 25, 40, 0.9) 0%, rgba(18, 18, 30, 0.9) 100%);
    }

    .setting-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem 0;
        border-bottom: 1px solid rgba(149, 76, 233, 0.1);
    }

    .setting-label {
        font-size: 0.95rem;
        color: rgba(240, 240, 245, 0.9);
        min-width: 120px;
    }

    .empty-state {
        text-align: center;
        padding: 2rem;
        opacity: 0.6;
        color: rgba(149, 76, 233, 0.5);
        font-size: 0.9rem;
    }
</style>

<div class="settings">
    {#if selectedModule}
        <h3 class="setting-label" style="margin-top: 0; color: #954CE9">{selectedModule.name}</h3>
        {#each selectedModule.settings as setting}
            <div class="setting-item">
                {#if setting.type === 'slider'}
                    <span class="setting-label">{setting.label}</span>
                    <SliderInput {setting} />
                {:else if setting.type === 'checkbox'}
                    <CheckboxInput label={setting.label} bind:checked={setting.value} />
                {/if}
            </div>
        {/each}
    {:else}
        <div class="empty-state">
            ⚡<br>
            Select a module to begin configuration
        </div>
    {/if}
</div>