<script lang="ts">
    import type { Module } from '../types';
    import ModuleCard from './ModuleCard.svelte';
    import type { CustomEvent } from '../types';

    export let modules: Module[];
    export let selectedModule: Module | null;
    export let searchQuery: string;
    
    function handleSelect(event: CustomEvent<Module>) {
        selectedModule = event.detail;
    }
</script>

<style>
    .modules {
        flex: 1;
        padding: 1.5rem;
        border-right: 1px solid rgba(149, 76, 233, 0.2);
        overflow-y: auto;
    }

    .empty-state {
        text-align: center;
        padding: 2rem;
        opacity: 0.6;
        color: rgba(149, 76, 233, 0.5);
        font-size: 0.9rem;
    }
</style>

<div class="modules">
    {#if modules.length > 0}
        {#each modules as module (module.id)}
            <ModuleCard 
                {module} 
                isSelected={selectedModule?.id === module.id}
                on:select={handleSelect}
            />
        {/each}
    {:else}
        <div class="empty-state">
            {#if searchQuery}
                🔍<br>No modules found for "{searchQuery}"
            {:else}
                ⚙️<br>No modules available
            {/if}
        </div>
    {/if}
</div>