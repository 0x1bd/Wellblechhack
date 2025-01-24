<script>
    import { createEventDispatcher } from 'svelte';
    const dispatch = createEventDispatcher();
    
    export let module;
    export let isSelected;
    import ToggleSwitch from './ToggleSwitch.svelte';
</script>

<style>
    .module-card {
        background: rgba(25, 25, 40, 0.8);
        border-radius: 12px;
        padding: 1.25rem;
        margin-bottom: 1rem;
        cursor: pointer;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border: 1px solid transparent;
        backdrop-filter: blur(4px);
    }

    .module-card:hover {
        background: rgba(149, 76, 233, 0.15);
        transform: translateY(-3px);
        border-color: rgba(149, 76, 233, 0.3);
    }

    .module-card.active {
        background: linear-gradient(135deg, rgba(149, 76, 233, 0.25) 0%, transparent 100%);
        border-color: #954CE9;
        box-shadow: 0 0 20px rgba(149, 76, 233, 0.2);
    }

    .module-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 0.5rem;
    }

    .module-title {
        font-size: 1.1rem;
        font-weight: 600;
        letter-spacing: 0.5px;
    }

    .module-description {
        font-size: 0.9rem;
        color: rgba(255, 255, 255, 0.7);
        line-height: 1.4;
    }
</style>

<div class="module-card" class:active={isSelected} on:click={() => dispatch('select', module)}>
    <div class="module-header">
        <div class="module-title">{module.name}</div>
        <ToggleSwitch bind:checked={module.enabled} />
    </div>
    <div class="module-description">
        {module.description}
    </div>
</div>