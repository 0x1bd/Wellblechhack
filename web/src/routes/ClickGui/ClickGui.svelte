<script lang="ts">
    import { categories } from './data.js';
    import CategoryList from './components/CategoryList.svelte';
    import ModuleList from './components/ModuleList.svelte';
    import SettingsPanel from './components/SettingsPanel.svelte';
    import type { Category, Module } from './types';

    let selectedCategory: Category = categories[0];
    let selectedModule: Module | null = null;
    let searchQuery: string = '';

    $: filteredModules = selectedCategory.modules.filter(module => {
        if (!searchQuery) return true;
        const query = searchQuery.toLowerCase();
        return (
            module.name.toLowerCase().includes(query) ||
            module.description.toLowerCase().includes(query) ||
            module.settings.some(setting => setting.label.toLowerCase().includes(query))
        );
    });
</script>

<style>
    :global(body) {
        margin: 0;
        padding: 0;
        font-family: 'Inter', sans-serif;
    }

    .container {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        padding: 2rem;
        background: radial-gradient(circle at 50% 50%, rgba(149, 76, 233, 0.1) 0%);
    }

    .panel {
        display: flex;
        width: 1000px;
        height: 700px;
        background: rgba(18, 18, 30, 0.95);
        border-radius: 20px;
        box-shadow: 0 0 50px rgba(149, 76, 233, 0.3);
        overflow: hidden;
        transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        backdrop-filter: blur(10px);
        border: 1px solid rgba(149, 76, 233, 0.2);
    }
</style>

<div class="container">
    <div class="panel">
        <CategoryList 
            {categories} 
            bind:selectedCategory 
            bind:searchQuery 
            {filteredModules}
        />
        
        <ModuleList 
            modules={filteredModules} 
            bind:selectedModule 
            {searchQuery}
        />
        
        <SettingsPanel {selectedModule} />
    </div>
</div>