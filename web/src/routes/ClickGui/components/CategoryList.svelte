<script lang="ts">
    import type { Category, Module } from "../types";

    export let categories: Category[];
    export let selectedCategory: Category;
    export let searchQuery: string;
    export let filteredModules: Module[];
</script>

<style>
    .categories {
        width: 220px;
        padding: 1.5rem;
        border-right: 1px solid rgba(149, 76, 233, 0.2);
        background: linear-gradient(160deg, rgba(25, 25, 40, 0.9) 0%, rgba(18, 18, 30, 0.9) 100%);
    }

    .category-btn {
        width: 100%;
        padding: 1rem;
        margin-bottom: 0.75rem;
        background: transparent;
        border: 1px solid rgba(149, 76, 233, 0.3);
        border-radius: 12px;
        color: rgba(240, 240, 245, 0.8);
        text-align: left;
        cursor: pointer;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        position: relative;
        overflow: hidden;
        backdrop-filter: blur(4px);
    }

    .category-btn:hover {
        background: rgba(149, 76, 233, 0.15);
        border-color: #954CE9;
        transform: translateX(8px);
    }

    .category-btn.active {
        background: linear-gradient(135deg, rgba(149, 76, 233, 0.3) 0%, transparent 100%);
        border-color: #954CE9;
        color: #fff;
        font-weight: 600;
        box-shadow: 0 0 15px rgba(149, 76, 233, 0.3);
    }

    .search-container {
        padding: 1rem;
        border-bottom: 1px solid rgba(149, 76, 233, 0.2);
        margin-bottom: 1rem;
    }

    .search-input {
        width: 85%;
        padding: 0.75rem 1rem;
        background: rgba(25, 25, 40, 0.8);
        border: 1px solid rgba(149, 76, 233, 0.3);
        border-radius: 35px;
        color: #fff;
        font-family: inherit;
        font-size: 0.95rem;
        transition: all 0.2s ease;
    }

    .search-input:focus {
        outline: none;
        border-color: #954CE9;
        box-shadow: 0 0 15px rgba(149, 76, 233, 0.2);
    }

    .results-count {
        font-size: 0.85rem;
        color: rgba(255, 255, 255, 0.6);
        margin-top: 0.5rem;
        text-align: center;
    }
</style>

<div class="categories">
    <div class="search-container">
        <input
            type="text"
            class="search-input"
            placeholder="Search modules..."
            bind:value={searchQuery}
        >
        {#if searchQuery}
            <div class="results-count">
                {filteredModules.length} result{#if filteredModules.length !== 1}s{/if} found
            </div>
        {/if}
    </div>
    
    {#each categories as category}
        <button
            class="category-btn {selectedCategory.name === category.name ? 'active' : ''}"
            on:click={() => {
                selectedCategory = category;
                searchQuery = '';
            }}
        >
            {category.name}
        </button>
    {/each}
</div>